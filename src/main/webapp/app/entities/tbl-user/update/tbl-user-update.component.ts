import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TblUserFormService, TblUserFormGroup } from './tbl-user-form.service';
import { ITblUser } from '../tbl-user.model';
import { TblUserService } from '../service/tbl-user.service';
import { ITblRole } from 'app/entities/tbl-role/tbl-role.model';
import { TblRoleService } from 'app/entities/tbl-role/service/tbl-role.service';

@Component({
  selector: 'jhi-tbl-user-update',
  templateUrl: './tbl-user-update.component.html',
})
export class TblUserUpdateComponent implements OnInit {
  isSaving = false;
  tblUser: ITblUser | null = null;

  tblRolesSharedCollection: ITblRole[] = [];

  editForm: TblUserFormGroup = this.tblUserFormService.createTblUserFormGroup();

  constructor(
    protected tblUserService: TblUserService,
    protected tblUserFormService: TblUserFormService,
    protected tblRoleService: TblRoleService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTblRole = (o1: ITblRole | null, o2: ITblRole | null): boolean => this.tblRoleService.compareTblRole(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tblUser }) => {
      this.tblUser = tblUser;
      if (tblUser) {
        this.updateForm(tblUser);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tblUser = this.tblUserFormService.getTblUser(this.editForm);
    if (tblUser.id !== null) {
      this.subscribeToSaveResponse(this.tblUserService.update(tblUser));
    } else {
      this.subscribeToSaveResponse(this.tblUserService.create(tblUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITblUser>>): void {
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

  protected updateForm(tblUser: ITblUser): void {
    this.tblUser = tblUser;
    this.tblUserFormService.resetForm(this.editForm, tblUser);

    this.tblRolesSharedCollection = this.tblRoleService.addTblRoleToCollectionIfMissing<ITblRole>(
      this.tblRolesSharedCollection,
      ...(tblUser.roles ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tblRoleService
      .query()
      .pipe(map((res: HttpResponse<ITblRole[]>) => res.body ?? []))
      .pipe(
        map((tblRoles: ITblRole[]) =>
          this.tblRoleService.addTblRoleToCollectionIfMissing<ITblRole>(tblRoles, ...(this.tblUser?.roles ?? []))
        )
      )
      .subscribe((tblRoles: ITblRole[]) => (this.tblRolesSharedCollection = tblRoles));
  }
}
