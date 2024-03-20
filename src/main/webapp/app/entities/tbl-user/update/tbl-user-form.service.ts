import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITblUser, NewTblUser } from '../tbl-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITblUser for edit and NewTblUserFormGroupInput for create.
 */
type TblUserFormGroupInput = ITblUser | PartialWithRequiredKeyOf<NewTblUser>;

type TblUserFormDefaults = Pick<NewTblUser, 'id' | 'roles'>;

type TblUserFormGroupContent = {
  id: FormControl<ITblUser['id'] | NewTblUser['id']>;
  username: FormControl<ITblUser['username']>;
  email: FormControl<ITblUser['email']>;
  firstName: FormControl<ITblUser['firstName']>;
  lastName: FormControl<ITblUser['lastName']>;
  roles: FormControl<ITblUser['roles']>;
};

export type TblUserFormGroup = FormGroup<TblUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TblUserFormService {
  createTblUserFormGroup(tblUser: TblUserFormGroupInput = { id: null }): TblUserFormGroup {
    const tblUserRawValue = {
      ...this.getFormDefaults(),
      ...tblUser,
    };
    return new FormGroup<TblUserFormGroupContent>({
      id: new FormControl(
        { value: tblUserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      username: new FormControl(tblUserRawValue.username, {
        validators: [Validators.required],
      }),
      email: new FormControl(tblUserRawValue.email, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(tblUserRawValue.firstName),
      lastName: new FormControl(tblUserRawValue.lastName),
      roles: new FormControl(tblUserRawValue.roles ?? []),
    });
  }

  getTblUser(form: TblUserFormGroup): ITblUser | NewTblUser {
    return form.getRawValue() as ITblUser | NewTblUser;
  }

  resetForm(form: TblUserFormGroup, tblUser: TblUserFormGroupInput): void {
    const tblUserRawValue = { ...this.getFormDefaults(), ...tblUser };
    form.reset(
      {
        ...tblUserRawValue,
        id: { value: tblUserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TblUserFormDefaults {
    return {
      id: null,
      roles: [],
    };
  }
}
