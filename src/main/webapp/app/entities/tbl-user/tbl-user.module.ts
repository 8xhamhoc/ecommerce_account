import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TblUserComponent } from './list/tbl-user.component';
import { TblUserDetailComponent } from './detail/tbl-user-detail.component';
import { TblUserUpdateComponent } from './update/tbl-user-update.component';
import { TblUserDeleteDialogComponent } from './delete/tbl-user-delete-dialog.component';
import { TblUserRoutingModule } from './route/tbl-user-routing.module';

@NgModule({
  imports: [SharedModule, TblUserRoutingModule],
  declarations: [TblUserComponent, TblUserDetailComponent, TblUserUpdateComponent, TblUserDeleteDialogComponent],
})
export class TblUserModule {}
