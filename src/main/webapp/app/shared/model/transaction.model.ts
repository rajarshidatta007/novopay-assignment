export interface ITransaction {
  id?: number;
  amount?: number;
  charges?: number;
  receiverId?: number;
  senderId?: number;
  passbookId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public amount?: number,
    public charges?: number,
    public receiverId?: number,
    public senderId?: number,
    public passbookId?: number
  ) {}
}
