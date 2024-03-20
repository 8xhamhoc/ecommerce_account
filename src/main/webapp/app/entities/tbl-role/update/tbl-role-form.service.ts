import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITblRole, NewTblRole } from '../tbl-role.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITblRole for edit and NewTblRoleFormGroupInput for create.
 */
type TblRoleFormGroupInput = ITblRole | PartialWithRequiredKeyOf<NewTblRole>;

type TblRoleFormDefaults = Pick<NewTblRole, 'id' | 'users'>;

type TblRoleFormGroupContent = {
  id: FormControl<ITblRole['id'] | NewTblRole['id']>;
  name: FormControl<ITblRole['name']>;
  users: FormControl<ITblRole['users']>;
};

export type TblRoleFormGroup = FormGroup<TblRoleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TblRoleFormService {
  createTblRoleFormGroup(tblRole: TblRoleFormGroupInput = { id: null }): TblRoleFormGroup {
    const tblRoleRawValue = {
      ...this.getFormDefaults(),
      ...tblRole,
    };
    return new FormGroup<TblRoleFormGroupContent>({
      id: new FormControl(
        { value: tblRoleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(tblRoleRawValue.name, {
        validators: [Validators.required],
      }),
      users: new FormControl(tblRoleRawValue.users ?? []),
    });
  }

  getTblRole(form: TblRoleFormGroup): ITblRole | NewTblRole {
    return form.getRawValue() as ITblRole | NewTblRole;
  }

  resetForm(form: TblRoleFormGroup, tblRole: TblRoleFormGroupInput): void {
    const tblRoleRawValue = { ...this.getFormDefaults(), ...tblRole };
    form.reset(
      {
        ...tblRoleRawValue,
        id: { value: tblRoleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TblRoleFormDefaults {
    return {
      id: null,
      users: [],
    };
  }
}
