import { ITblUser } from 'app/entities/tbl-user/tbl-user.model';

export interface ITblRole {
  id: number;
  name?: string | null;
  users?: Pick<ITblUser, 'id'>[] | null;
}

export type NewTblRole = Omit<ITblRole, 'id'> & { id: null };
