export class User {
  public address: string;
  public birthday: string;
  public email: string;
  public middleName: string;
  public name: string;
  public phoneNumber: string;
  public surname: string;
  public uuid: string;
}

export class LoginResponse {
  constructor(public user: User,
              public headers: Headers) {

  }
}
