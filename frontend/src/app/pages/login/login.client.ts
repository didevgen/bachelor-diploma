import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { LoginResponse, User } from '../../models/auth/auth.models';
import { Observable } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/errorobservable';

@Injectable()
export class LoginClient {
  constructor(private http: Http) {
  }

  public login(email: string, password: string) {
    return this.http.post('/api/v1/login', {email, password}).map((item: Response) => {
      return new LoginResponse(<User>this.getResult(item), item.headers);
    }).catch((error: any) => {
      return this.throwError(error);
    }).share();
  }

  public oauthLogin(token: string) {
    return this.http.post(`/api/v1/verifyToken`, {token}).map((item: Response) => {
      return new LoginResponse(<User>this.getResult(item), item.headers);
    }).catch((error: any) => {
      return this.throwError(error);
    }).share();
  }

  private getResult = (response: Response) => {
    return response.json ? response.json() : response;
  };

  private throwError(error: any): ErrorObservable<any> {
    if (error.json) {
      return Observable.throw(error.json());
    } else {
      const errorMessage = this.getErrorMessage(error);
      return Observable.throw(errorMessage);
    }
  }

  private getErrorMessage = (error: any): string => error.message || 'Server error';


}
