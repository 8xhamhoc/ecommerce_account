import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tbl-user',
        data: { pageTitle: 'accountApp.tblUser.home.title' },
        loadChildren: () => import('./tbl-user/tbl-user.module').then(m => m.TblUserModule),
      },
      {
        path: 'tbl-role',
        data: { pageTitle: 'accountApp.tblRole.home.title' },
        loadChildren: () => import('./tbl-role/tbl-role.module').then(m => m.TblRoleModule),
      },
      {
        path: 'user-role',
        data: { pageTitle: 'accountApp.userRole.home.title' },
        loadChildren: () => import('./user-role/user-role.module').then(m => m.UserRoleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
