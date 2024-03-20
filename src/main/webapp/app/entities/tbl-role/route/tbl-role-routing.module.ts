import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TblRoleComponent } from '../list/tbl-role.component';
import { TblRoleDetailComponent } from '../detail/tbl-role-detail.component';
import { TblRoleUpdateComponent } from '../update/tbl-role-update.component';
import { TblRoleRoutingResolveService } from './tbl-role-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tblRoleRoute: Routes = [
  {
    path: '',
    component: TblRoleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TblRoleDetailComponent,
    resolve: {
      tblRole: TblRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TblRoleUpdateComponent,
    resolve: {
      tblRole: TblRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TblRoleUpdateComponent,
    resolve: {
      tblRole: TblRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tblRoleRoute)],
  exports: [RouterModule],
})
export class TblRoleRoutingModule {}
