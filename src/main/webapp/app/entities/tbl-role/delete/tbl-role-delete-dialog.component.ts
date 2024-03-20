import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITblRole } from '../tbl-role.model';
import { TblRoleService } from '../service/tbl-role.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tbl-role-delete-dialog.component.html',
})
export class TblRoleDeleteDialogComponent {
  tblRole?: ITblRole;

  constructor(protected tblRoleService: TblRoleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tblRoleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
