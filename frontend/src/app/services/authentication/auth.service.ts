import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Headers, Http, RequestOptionsArgs, Response } from '@angular/http';
import { User } from '../../models/auth/auth.models';

@Injectable()
export class AuthService {

  private readonly TOKEN_NAME = 'token';
  private readonly TOKEN_EXPIRATION = 'token_expire';

  private _user: User;

  constructor(private http: Http, private router: Router) {
  }

  public set user(value: User) {
    this._user = value;
  }

  public get user(): User {
    return this._user;
  }

  public get isAuthenticated(): boolean {
    return !!this.getToken() && !this.isTokenExpired();
  };

  public getToken(): string {
    return localStorage.getItem(this.TOKEN_NAME);
  }

  public isTokenExpired(): boolean {
    return +localStorage.getItem(this.TOKEN_EXPIRATION) <= new Date().getTime();
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
      return this.http.request(url, options)
        .map((response: Response) => {
          return response.json();
        })
        .catch(this.handleError);
    } else {
      this.router.navigate(['/login']);
      return Observable.throw(new Error('User Not Authenticated.'));
    }
  }

  private handleError(error: any): Observable<any> {
    if (error.code === 401 || error.code === 403) {
      this.router.navigate(['/login']);
    }
    return Observable.throw(error);
  }


}
