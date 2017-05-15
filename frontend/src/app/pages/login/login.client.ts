import { Injectable } from "@angular/core";
import { Http } from "@angular/http";

@Injectable()
export class LoginClient {
  constructor(private http: Http) {}

  public login(email: string, password: string) {
      return this.http.post('/api/v1/login', {email, password});
  }

  public oauthLogin(token: string) {
    return this.http.post(`/api/v1/verifyToken`, {token});
  }
}
