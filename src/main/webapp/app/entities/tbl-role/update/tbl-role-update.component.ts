import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TblRoleFormService, TblRoleFormGroup } from './tbl-role-form.service';
import { ITblRole } from '../tbl-role.model';
import { TblRoleService } from '../service/tbl-role.service';

@Component({
  selector: 'jhi-tbl-role-update',
  templateUrl: './tbl-role-update.component.html',
})
export class TblRoleUpdateComponent implements OnInit {
  isSaving = false;
  tblRole: ITblRole | null = null;

  editForm: TblRoleFormGroup = this.tblRoleFormService.createTblRoleFormGroup();

  constructor(
    protected tblRoleService: TblRoleService,
    protected tblRoleFormService: TblRoleFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tblRole }) => {
      this.tblRole = tblRole;
      if (tblRole) {
        this.updateForm(tblRole);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tblRole = this.tblRoleFormService.getTblRole(this.editForm);
    if (tblRole.id !== null) {
      this.subscribeToSaveResponse(this.tblRoleService.update(tblRole));
    } else {
      this.subscribeToSaveResponse(this.tblRoleService.create(tblRole));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITblRole>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tblRole: ITblRole): void {
    this.tblRole = tblRole;
    this.tblRoleFormService.resetForm(this.editForm, tblRole);
  }
}
