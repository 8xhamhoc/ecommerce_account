import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITblRole } from '../tbl-role.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tbl-role.test-samples';

import { TblRoleService } from './tbl-role.service';

const requireRestSample: ITblRole = {
  ...sampleWithRequiredData,
};

describe('TblRole Service', () => {
  let service: TblRoleService;
  let httpMock: HttpTestingController;
  let expectedResult: ITblRole | ITblRole[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TblRoleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TblRole', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tblRole = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tblRole).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TblRole', () => {
      const tblRole = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tblRole).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TblRole', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TblRole', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TblRole', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTblRoleToCollectionIfMissing', () => {
      it('should add a TblRole to an empty array', () => {
        const tblRole: ITblRole = sampleWithRequiredData;
        expectedResult = service.addTblRoleToCollectionIfMissing([], tblRole);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tblRole);
      });

      it('should not add a TblRole to an array that contains it', () => {
        const tblRole: ITblRole = sampleWithRequiredData;
        const tblRoleCollection: ITblRole[] = [
          {
            ...tblRole,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTblRoleToCollectionIfMissing(tblRoleCollection, tblRole);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TblRole to an array that doesn't contain it", () => {
        const tblRole: ITblRole = sampleWithRequiredData;
        const tblRoleCollection: ITblRole[] = [sampleWithPartialData];
        expectedResult = service.addTblRoleToCollectionIfMissing(tblRoleCollection, tblRole);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tblRole);
      });

      it('should add only unique TblRole to an array', () => {
        const tblRoleArray: ITblRole[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tblRoleCollection: ITblRole[] = [sampleWithRequiredData];
        expectedResult = service.addTblRoleToCollectionIfMissing(tblRoleCollection, ...tblRoleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tblRole: ITblRole = sampleWithRequiredData;
        const tblRole2: ITblRole = sampleWithPartialData;
        expectedResult = service.addTblRoleToCollectionIfMissing([], tblRole, tblRole2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tblRole);
        expect(expectedResult).toContain(tblRole2);
      });

      it('should accept null and undefined values', () => {
        const tblRole: ITblRole = sampleWithRequiredData;
        expectedResult = service.addTblRoleToCollectionIfMissing([], null, tblRole, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tblRole);
      });

      it('should return initial array if no TblRole is added', () => {
        const tblRoleCollection: ITblRole[] = [sampleWithRequiredData];
        expectedResult = service.addTblRoleToCollectionIfMissing(tblRoleCollection, undefined, null);
        expect(expectedResult).toEqual(tblRoleCollection);
      });
    });

    describe('compareTblRole', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTblRole(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTblRole(entity1, entity2);
        const compareResult2 = service.compareTblRole(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTblRole(entity1, entity2);
        const compareResult2 = service.compareTblRole(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTblRole(entity1, entity2);
        const compareResult2 = service.compareTblRole(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
