import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITblUser, NewTblUser } from '../tbl-user.model';

export type PartialUpdateTblUser = Partial<ITblUser> & Pick<ITblUser, 'id'>;

export type EntityResponseType = HttpResponse<ITblUser>;
export type EntityArrayResponseType = HttpResponse<ITblUser[]>;

@Injectable({ providedIn: 'root' })
export class TblUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbl-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tblUser: NewTblUser): Observable<EntityResponseType> {
    return this.http.post<ITblUser>(this.resourceUrl, tblUser, { observe: 'response' });
  }

  update(tblUser: ITblUser): Observable<EntityResponseType> {
    return this.http.put<ITblUser>(`${this.resourceUrl}/${this.getTblUserIdentifier(tblUser)}`, tblUser, { observe: 'response' });
  }

  partialUpdate(tblUser: PartialUpdateTblUser): Observable<EntityResponseType> {
    return this.http.patch<ITblUser>(`${this.resourceUrl}/${this.getTblUserIdentifier(tblUser)}`, tblUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITblUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITblUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTblUserIdentifier(tblUser: Pick<ITblUser, 'id'>): number {
    return tblUser.id;
  }

  compareTblUser(o1: Pick<ITblUser, 'id'> | null, o2: Pick<ITblUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getTblUserIdentifier(o1) === this.getTblUserIdentifier(o2) : o1 === o2;
  }

  addTblUserToCollectionIfMissing<Type extends Pick<ITblUser, 'id'>>(
    tblUserCollection: Type[],
    ...tblUsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tblUsers: Type[] = tblUsersToCheck.filter(isPresent);
    if (tblUsers.length > 0) {
      const tblUserCollectionIdentifiers = tblUserCollection.map(tblUserItem => this.getTblUserIdentifier(tblUserItem)!);
      const tblUsersToAdd = tblUsers.filter(tblUserItem => {
        const tblUserIdentifier = this.getTblUserIdentifier(tblUserItem);
        if (tblUserCollectionIdentifiers.includes(tblUserIdentifier)) {
          return false;
        }
        tblUserCollectionIdentifiers.push(tblUserIdentifier);
        return true;
      });
      return [...tblUsersToAdd, ...tblUserCollection];
    }
    return tblUserCollection;
  }
}
