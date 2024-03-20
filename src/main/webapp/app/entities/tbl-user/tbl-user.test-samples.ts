import { ITblUser, NewTblUser } from './tbl-user.model';

export const sampleWithRequiredData: ITblUser = {
  id: 28195,
  username: 'International Jewelery',
  email: 'Hailey_Bergnaum@yahoo.com',
};

export const sampleWithPartialData: ITblUser = {
  id: 43304,
  username: 'Checking',
  email: 'Richie6@gmail.com',
  firstName: 'Johnpaul',
  lastName: 'Crooks',
};

export const sampleWithFullData: ITblUser = {
  id: 82553,
  username: 'Movies Steel',
  email: 'Deven_Wolff@gmail.com',
  firstName: 'Delores',
  lastName: 'Glover',
};

export const sampleWithNewData: NewTblUser = {
  username: 'grow Loan generating',
  email: 'Jeanette.Torphy@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
