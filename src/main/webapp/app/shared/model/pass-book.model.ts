import { ITransaction } from 'app/shared/model/transaction.model';

export interface IPassBook {
  id?: number;
  transactions?: ITransaction[];
}

export class PassBook implements IPassBook {
  constructor(public id?: number, public transactions?: ITransaction[]) {}
}
