export interface IBeer {
  id?: number;
  name?: string;
  brewery?: string;
  price?: number;
}

export class Beer implements IBeer {
  constructor(public id?: number, public name?: string, public brewery?: string, public price?: number) {}
}
