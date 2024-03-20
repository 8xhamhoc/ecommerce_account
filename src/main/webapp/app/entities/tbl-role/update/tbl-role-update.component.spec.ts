import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TblRoleFormService } from './tbl-role-form.service';
import { TblRoleService } from '../service/tbl-role.service';
import { ITblRole } from '../tbl-role.model';

import { TblRoleUpdateComponent } from './tbl-role-update.component';

describe('TblRole Management Update Component', () => {
  let comp: TblRoleUpdateComponent;
  let fixture: ComponentFixture<TblRoleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tblRoleFormService: TblRoleFormService;
  let tblRoleService: TblRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TblRoleUpdateComponent],
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
      .overrideTemplate(TblRoleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TblRoleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tblRoleFormService = TestBed.inject(TblRoleFormService);
    tblRoleService = TestBed.inject(TblRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tblRole: ITblRole = { id: 456 };

      activatedRoute.data = of({ tblRole });
      comp.ngOnInit();

      expect(comp.tblRole).toEqual(tblRole);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblRole>>();
      const tblRole = { id: 123 };
      jest.spyOn(tblRoleFormService, 'getTblRole').mockReturnValue(tblRole);
      jest.spyOn(tblRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tblRole }));
      saveSubject.complete();

      // THEN
      expect(tblRoleFormService.getTblRole).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tblRoleService.update).toHaveBeenCalledWith(expect.objectContaining(tblRole));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblRole>>();
      const tblRole = { id: 123 };
      jest.spyOn(tblRoleFormService, 'getTblRole').mockReturnValue({ id: null });
      jest.spyOn(tblRoleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblRole: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tblRole }));
      saveSubject.complete();

      // THEN
      expect(tblRoleFormService.getTblRole).toHaveBeenCalled();
      expect(tblRoleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITblRole>>();
      const tblRole = { id: 123 };
      jest.spyOn(tblRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tblRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tblRoleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
