export interface IUserRole {
  id: number;
  userId?: number | null;
  roleId?: number | null;
}

export type NewUserRole = Omit<IUserRole, 'id'> & { id: null };
