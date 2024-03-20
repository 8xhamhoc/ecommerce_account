import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TblUserFormService } from './tbl-user-form.service';
import { TblUserService } from '../service/tbl-user.service';
import { ITblUser } from '../tbl-user.model';
import { ITblRole } from 'app/entities/tbl-role/tbl-role.model';
import { TblRoleService } from 'app/entities/tbl-role/service/tbl-role.service';

import { TblUserUpdateComponent } from './tbl-user-update.component';

describe('TblUser Management Update Component', () => {
  let comp: TblUserUpdateComponent;
  let fixture: ComponentFixture<TblUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tblUserFormService: TblUserFormService;
  let tblUserService: TblUserService;
  let tblRoleService: TblRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TblUserUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TblUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TblUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tblUserFormService = TestBed.inject(TblUserFormService);
    tblUserService = TestBed.inject(TblUserService);
    tblRoleService = TestBed.inject(TblRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TblRole query and add missing value', () => {
      const tblUser: ITblUser = { id: 456 };
      const roles: ITblRole[] = [{ id: 49573 }];
      tblUser.roles = roles;

      const tblRoleCollection: ITblRole[] = [{ id: 26787 }];
      jest.spyOn(tblRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: tblRoleCollection })));
      const additionalTblRoles = [...roles];
      const expectedCollection: ITblRole[] = [...additionalTblRoles, ...tblRoleCollection];
      jest.spyOn(tblRoleService, 'addTblRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tblUser });
      comp.ngOnInit();

      expect(tblRoleService.query).toHaveBeenCalled();
      expect(tblRoleService.addTblRoleToCollectionIfMissing).toHaveBeenCalledWith(
        tblRoleCollection,
        ...additionalTblRoles.map(expect.objectContaining)
      );
      expect(comp.tblRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tblUser: ITblUser = { id: 456 };
      const role: ITblRole = { id: 90638 };
      tblUser.roles = [role];

      activatedRoute.data = of({ tblUser });
      comp.ngOnInit();

      expect(comp.tblRolesSharedCollection).toContain(role);
      expect(comp.tblUser).toEqual(tblUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblUser>>();
      const tblUser = { id: 123 };
      jest.spyOn(tblUserFormService, 'getTblUser').mockReturnValue(tblUser);
      jest.spyOn(tblUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tblUser }));
      saveSubject.complete();

      // THEN
      expect(tblUserFormService.getTblUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tblUserService.update).toHaveBeenCalledWith(expect.objectContaining(tblUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblUser>>();
      const tblUser = { id: 123 };
      jest.spyOn(tblUserFormService, 'getTblUser').mockReturnValue({ id: null });
      jest.spyOn(tblUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tblUser }));
      saveSubject.complete();

      // THEN
      expect(tblUserFormService.getTblUser).toHaveBeenCalled();
      expect(tblUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblUser>>();
      const tblUser = { id: 123 };
      jest.spyOn(tblUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tblUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTblRole', () => {
      it('Should forward to tblRoleService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tblRoleService, 'compareTblRole');
        comp.compareTblRole(entity, entity2);
        expect(tblRoleService.compareTblRole).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
