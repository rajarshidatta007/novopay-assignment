export interface IWallet {
  id?: number;
  balance?: number;
  owenerId?: number;
  passbookId?: number;
}

export class Wallet implements IWallet {
  constructor(public id?: number, public balance?: number, public owenerId?: number, public passbookId?: number) {}
}
