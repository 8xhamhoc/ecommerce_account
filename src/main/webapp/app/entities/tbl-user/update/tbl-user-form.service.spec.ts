import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tbl-user.test-samples';

import { TblUserFormService } from './tbl-user-form.service';

describe('TblUser Form Service', () => {
  let service: TblUserFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TblUserFormService);
  });

  describe('Service methods', () => {
    describe('createTblUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTblUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            email: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            roles: expect.any(Object),
          })
        );
      });

      it('passing ITblUser should create a new form with FormGroup', () => {
        const formGroup = service.createTblUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            email: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            roles: expect.any(Object),
          })
        );
      });
    });

    describe('getTblUser', () => {
      it('should return NewTblUser for default TblUser initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTblUserFormGroup(sampleWithNewData);

        const tblUser = service.getTblUser(formGroup) as any;

        expect(tblUser).toMatchObject(sampleWithNewData);
      });

      it('should return NewTblUser for empty TblUser initial value', () => {
        const formGroup = service.createTblUserFormGroup();

        const tblUser = service.getTblUser(formGroup) as any;

        expect(tblUser).toMatchObject({});
      });

      it('should return ITblUser', () => {
        const formGroup = service.createTblUserFormGroup(sampleWithRequiredData);

        const tblUser = service.getTblUser(formGroup) as any;

        expect(tblUser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITblUser should not enable id FormControl', () => {
        const formGroup = service.createTblUserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTblUser should disable id FormControl', () => {
        const formGroup = service.createTblUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
