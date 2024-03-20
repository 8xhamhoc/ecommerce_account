import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TblRoleComponent } from './list/tbl-role.component';
import { TblRoleDetailComponent } from './detail/tbl-role-detail.component';
import { TblRoleUpdateComponent } from './update/tbl-role-update.component';
import { TblRoleDeleteDialogComponent } from './delete/tbl-role-delete-dialog.component';
import { TblRoleRoutingModule } from './route/tbl-role-routing.module';

@NgModule({
  imports: [SharedModule, TblRoleRoutingModule],
  declarations: [TblRoleComponent, TblRoleDetailComponent, TblRoleUpdateComponent, TblRoleDeleteDialogComponent],
})
export class TblRoleModule {}
