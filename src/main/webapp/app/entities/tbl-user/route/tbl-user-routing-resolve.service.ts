import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITblUser } from '../tbl-user.model';
import { TblUserService } from '../service/tbl-user.service';

@Injectable({ providedIn: 'root' })
export class TblUserRoutingResolveService implements Resolve<ITblUser | null> {
  constructor(protected service: TblUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITblUser | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tblUser: HttpResponse<ITblUser>) => {
          if (tblUser.body) {
            return of(tblUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
