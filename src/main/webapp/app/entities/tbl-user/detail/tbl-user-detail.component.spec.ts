import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TblUserDetailComponent } from './tbl-user-detail.component';

describe('TblUser Management Detail Component', () => {
  let comp: TblUserDetailComponent;
  let fixture: ComponentFixture<TblUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TblUserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tblUser: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TblUserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TblUserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tblUser on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tblUser).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
