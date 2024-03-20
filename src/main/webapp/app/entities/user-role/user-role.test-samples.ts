import { IUserRole, NewUserRole } from './user-role.model';

export const sampleWithRequiredData: IUserRole = {
  id: 43156,
  userId: 63793,
  roleId: 37235,
};

export const sampleWithPartialData: IUserRole = {
  id: 13386,
  userId: 41293,
  roleId: 63115,
};

export const sampleWithFullData: IUserRole = {
  id: 91395,
  userId: 88036,
  roleId: 95729,
};

export const sampleWithNewData: NewUserRole = {
  userId: 14730,
  roleId: 30276,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
