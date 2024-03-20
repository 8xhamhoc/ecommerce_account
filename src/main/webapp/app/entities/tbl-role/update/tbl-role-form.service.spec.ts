import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tbl-role.test-samples';

import { TblRoleFormService } from './tbl-role-form.service';

describe('TblRole Form Service', () => {
  let service: TblRoleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TblRoleFormService);
  });

  describe('Service methods', () => {
    describe('createTblRoleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTblRoleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });

      it('passing ITblRole should create a new form with FormGroup', () => {
        const formGroup = service.createTblRoleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });
    });

    describe('getTblRole', () => {
      it('should return NewTblRole for default TblRole initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTblRoleFormGroup(sampleWithNewData);

        const tblRole = service.getTblRole(formGroup) as any;

        expect(tblRole).toMatchObject(sampleWithNewData);
      });

      it('should return NewTblRole for empty TblRole initial value', () => {
        const formGroup = service.createTblRoleFormGroup();

        const tblRole = service.getTblRole(formGroup) as any;

        expect(tblRole).toMatchObject({});
      });

      it('should return ITblRole', () => {
        const formGroup = service.createTblRoleFormGroup(sampleWithRequiredData);

        const tblRole = service.getTblRole(formGroup) as any;

        expect(tblRole).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITblRole should not enable id FormControl', () => {
        const formGroup = service.createTblRoleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTblRole should disable id FormControl', () => {
        const formGroup = service.createTblRoleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
