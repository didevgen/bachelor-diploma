import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Headers, Http, RequestOptionsArgs } from '@angular/http';
import { User } from '../../models/auth/auth.models';

@Injectable()
export class AuthService {

  private readonly TOKEN_NAME = 'token';

  private _user: User;

  constructor(private http: Http) {
  }

  public set user(value: User) {
    this._user = value;
  }

  public get user(): User {
    return this._user;
  }

  public get isAuthenticated(): boolean {
    return this._user !== null && !!localStorage.getItem(this.TOKEN_NAME);
  };

  public getToken(): string {
    return localStorage.getItem(this.TOKEN_NAME);
  }

  public makeAuthenticatedCall(url: string, options: RequestOptionsArgs) {

    if (!url) {
      return Observable.throw(new Error('Invalid url was provided'));
    }

    if (this.isAuthenticated) {

      if (options.headers == null) {
        options.headers = new Headers();
      }

      options.headers.set('x-auth-token', this.getToken());
      return this.http.request(url, options).catch(this.handleError);
    } else {
      return Observable.throw(new Error('User Not Authenticated.'));
    }
  }

  private handleError(error: any): Observable<any> {
    return Observable.throw(error);
  }


}
