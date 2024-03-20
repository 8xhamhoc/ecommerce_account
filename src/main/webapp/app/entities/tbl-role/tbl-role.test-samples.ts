import { ITblRole, NewTblRole } from './tbl-role.model';

export const sampleWithRequiredData: ITblRole = {
  id: 2679,
  name: 'heuristic Ukraine Borders',
};

export const sampleWithPartialData: ITblRole = {
  id: 11914,
  name: 'Ergonomic Greece',
};

export const sampleWithFullData: ITblRole = {
  id: 49079,
  name: 'Table indexing cultivate',
};

export const sampleWithNewData: NewTblRole = {
  name: 'Frozen',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
