import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITblUser } from '../tbl-user.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tbl-user.test-samples';

import { TblUserService } from './tbl-user.service';

const requireRestSample: ITblUser = {
  ...sampleWithRequiredData,
};

describe('TblUser Service', () => {
  let service: TblUserService;
  let httpMock: HttpTestingController;
  let expectedResult: ITblUser | ITblUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TblUserService);
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

    it('should create a TblUser', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tblUser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tblUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TblUser', () => {
      const tblUser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tblUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TblUser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TblUser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TblUser', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTblUserToCollectionIfMissing', () => {
      it('should add a TblUser to an empty array', () => {
        const tblUser: ITblUser = sampleWithRequiredData;
        expectedResult = service.addTblUserToCollectionIfMissing([], tblUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tblUser);
      });

      it('should not add a TblUser to an array that contains it', () => {
        const tblUser: ITblUser = sampleWithRequiredData;
        const tblUserCollection: ITblUser[] = [
          {
            ...tblUser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTblUserToCollectionIfMissing(tblUserCollection, tblUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TblUser to an array that doesn't contain it", () => {
        const tblUser: ITblUser = sampleWithRequiredData;
        const tblUserCollection: ITblUser[] = [sampleWithPartialData];
        expectedResult = service.addTblUserToCollectionIfMissing(tblUserCollection, tblUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tblUser);
      });

      it('should add only unique TblUser to an array', () => {
        const tblUserArray: ITblUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tblUserCollection: ITblUser[] = [sampleWithRequiredData];
        expectedResult = service.addTblUserToCollectionIfMissing(tblUserCollection, ...tblUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tblUser: ITblUser = sampleWithRequiredData;
        const tblUser2: ITblUser = sampleWithPartialData;
        expectedResult = service.addTblUserToCollectionIfMissing([], tblUser, tblUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tblUser);
        expect(expectedResult).toContain(tblUser2);
      });

      it('should accept null and undefined values', () => {
        const tblUser: ITblUser = sampleWithRequiredData;
        expectedResult = service.addTblUserToCollectionIfMissing([], null, tblUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tblUser);
      });

      it('should return initial array if no TblUser is added', () => {
        const tblUserCollection: ITblUser[] = [sampleWithRequiredData];
        expectedResult = service.addTblUserToCollectionIfMissing(tblUserCollection, undefined, null);
        expect(expectedResult).toEqual(tblUserCollection);
      });
    });

    describe('compareTblUser', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTblUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTblUser(entity1, entity2);
        const compareResult2 = service.compareTblUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTblUser(entity1, entity2);
        const compareResult2 = service.compareTblUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTblUser(entity1, entity2);
        const compareResult2 = service.compareTblUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
