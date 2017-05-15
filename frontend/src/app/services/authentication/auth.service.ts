import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Headers, Http, RequestOptionsArgs } from "@angular/http";
import { User } from "../../models/auth/auth.models";

@Injectable()
export class AuthService {

  constructor(private http: Http) {
  }

  private _user: User;

  public set user(value: User) {
    this._user = value;
  }

  public get user(): User {
    return this._user;
  }

  public get isAuthenticated(): boolean {
    return this._user !== null;
  };

  public getToken(): string {
    return localStorage.getItem('token');
  }

  public makeAuthenticatedCall(url: string, options: RequestOptionsArgs) {

    if (url) {
      let x = 0;
      if (this.isAuthenticated) {
        if (options.headers == null) {
          options.headers = new Headers();
        }
        options.headers.set('x-auth-token', this.getToken());
        return this.http.request(url, options).catch(this.handleError);
      }
      else {
        return Observable.throw(new Error("User Not Authenticated."));
      }
    }
    else {
      return this.http.request(url, options).catch(this.handleError);
    }
  }

  private handleError(error: any): Observable<any> {
    return Observable.throw(error);
  }


}
