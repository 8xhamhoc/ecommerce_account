import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITblRole } from '../tbl-role.model';
import { TblRoleService } from '../service/tbl-role.service';

@Injectable({ providedIn: 'root' })
export class TblRoleRoutingResolveService implements Resolve<ITblRole | null> {
  constructor(protected service: TblRoleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITblRole | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tblRole: HttpResponse<ITblRole>) => {
          if (tblRole.body) {
            return of(tblRole.body);
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
