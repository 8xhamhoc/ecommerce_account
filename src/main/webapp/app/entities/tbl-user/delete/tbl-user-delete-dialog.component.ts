import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITblUser } from '../tbl-user.model';
import { TblUserService } from '../service/tbl-user.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tbl-user-delete-dialog.component.html',
})
export class TblUserDeleteDialogComponent {
  tblUser?: ITblUser;

  constructor(protected tblUserService: TblUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tblUserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
