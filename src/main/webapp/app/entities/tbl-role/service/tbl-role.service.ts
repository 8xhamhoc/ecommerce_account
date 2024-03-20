import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITblRole, NewTblRole } from '../tbl-role.model';

export type PartialUpdateTblRole = Partial<ITblRole> & Pick<ITblRole, 'id'>;

export type EntityResponseType = HttpResponse<ITblRole>;
export type EntityArrayResponseType = HttpResponse<ITblRole[]>;

@Injectable({ providedIn: 'root' })
export class TblRoleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbl-roles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tblRole: NewTblRole): Observable<EntityResponseType> {
    return this.http.post<ITblRole>(this.resourceUrl, tblRole, { observe: 'response' });
  }

  update(tblRole: ITblRole): Observable<EntityResponseType> {
    return this.http.put<ITblRole>(`${this.resourceUrl}/${this.getTblRoleIdentifier(tblRole)}`, tblRole, { observe: 'response' });
  }

  partialUpdate(tblRole: PartialUpdateTblRole): Observable<EntityResponseType> {
    return this.http.patch<ITblRole>(`${this.resourceUrl}/${this.getTblRoleIdentifier(tblRole)}`, tblRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITblRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITblRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTblRoleIdentifier(tblRole: Pick<ITblRole, 'id'>): number {
    return tblRole.id;
  }

  compareTblRole(o1: Pick<ITblRole, 'id'> | null, o2: Pick<ITblRole, 'id'> | null): boolean {
    return o1 && o2 ? this.getTblRoleIdentifier(o1) === this.getTblRoleIdentifier(o2) : o1 === o2;
  }

  addTblRoleToCollectionIfMissing<Type extends Pick<ITblRole, 'id'>>(
    tblRoleCollection: Type[],
    ...tblRolesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tblRoles: Type[] = tblRolesToCheck.filter(isPresent);
    if (tblRoles.length > 0) {
      const tblRoleCollectionIdentifiers = tblRoleCollection.map(tblRoleItem => this.getTblRoleIdentifier(tblRoleItem)!);
      const tblRolesToAdd = tblRoles.filter(tblRoleItem => {
        const tblRoleIdentifier = this.getTblRoleIdentifier(tblRoleItem);
        if (tblRoleCollectionIdentifiers.includes(tblRoleIdentifier)) {
          return false;
        }
        tblRoleCollectionIdentifiers.push(tblRoleIdentifier);
        return true;
      });
      return [...tblRolesToAdd, ...tblRoleCollection];
    }
    return tblRoleCollection;
  }
}
