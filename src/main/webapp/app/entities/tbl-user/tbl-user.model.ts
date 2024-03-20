import { ITblRole } from 'app/entities/tbl-role/tbl-role.model';

export interface ITblUser {
  id: number;
  username?: string | null;
  email?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  roles?: Pick<ITblRole, 'id'>[] | null;
}

export type NewTblUser = Omit<ITblUser, 'id'> & { id: null };
