import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { LoginResponse, User } from '../../models/auth/auth.models';

@Injectable()
export class LoginClient {
  constructor(private http: Http) {}

  public login(email: string, password: string) {
      return this.http.post('/api/v1/login', {email, password}).map((item: Response) => {
        return new LoginResponse(<User>this.getResult(item), item.headers);
      }).share();
  }

  public oauthLogin(token: string) {
    return this.http.post(`/api/v1/verifyToken`, {token}).map((item: Response) => {
      return new LoginResponse(<User>this.getResult(item), item.headers);
    }).share();
  }

  private getResult = (response: Response) => {
    return response.json ? response.json() : response;
  };
}
