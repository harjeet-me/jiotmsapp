import { Moment } from 'moment';

export interface IAccounts {
  id?: number;
  balance?: number;
  over30?: number;
  over60?: number;
  over90?: number;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
}

export class Accounts implements IAccounts {
  constructor(
    public id?: number,
    public balance?: number,
    public over30?: number,
    public over60?: number,
    public over90?: number,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string
  ) {}
}
