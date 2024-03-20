import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TblUserComponent } from '../list/tbl-user.component';
import { TblUserDetailComponent } from '../detail/tbl-user-detail.component';
import { TblUserUpdateComponent } from '../update/tbl-user-update.component';
import { TblUserRoutingResolveService } from './tbl-user-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tblUserRoute: Routes = [
  {
    path: '',
    component: TblUserComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TblUserDetailComponent,
    resolve: {
      tblUser: TblUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TblUserUpdateComponent,
    resolve: {
      tblUser: TblUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TblUserUpdateComponent,
    resolve: {
      tblUser: TblUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tblUserRoute)],
  exports: [RouterModule],
})
export class TblUserRoutingModule {}
