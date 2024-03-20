import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TblRoleDetailComponent } from './tbl-role-detail.component';

describe('TblRole Management Detail Component', () => {
  let comp: TblRoleDetailComponent;
  let fixture: ComponentFixture<TblRoleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TblRoleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tblRole: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TblRoleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TblRoleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tblRole on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tblRole).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
