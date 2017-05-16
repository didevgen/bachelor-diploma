import { Inject, Injectable, ApplicationRef } from '@angular/core';
import { Http, Headers, RequestOptions, Response, RequestOptionsArgs } from '@angular/http';
import { Observable } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/errorobservable';
import 'rxjs/add/operator/catch';
import { AuthHttp } from './http/auth.http';

declare var gApiBaseAddress: any;

export declare interface IApiClient {
  get<T>(path: string, options?: RequestOptionsArgs): Observable<T>;
  post<T>(path: string, data: any, options?: RequestOptionsArgs): Observable<T>;
  put<T>(path: string, data: any, options?: RequestOptionsArgs): Observable<T>;
  delete<T>(path: string, data?: any, options?: RequestOptionsArgs): Observable<any>;
}

export declare interface ICachingApiClient {
  get<T>(path: string): Observable<T>;
}

@Injectable()
export class ApiClient implements IApiClient {

  public _apiBaseUrl: string = null;

  constructor(private http: AuthHttp,
              private options: RequestOptions,
              private appRef: ApplicationRef) {
    const headers = new Headers({
      'Accept': 'application/json',
      'If-Modified-Since': 'Mon, 26 Jul 1997 05:00:00 GMT',
      'Cache-Control': 'no-cache',
      'Pragma': 'no-cache',
      'Content-Type': 'application/json; charset=utf-8'
    });
    this.options = new RequestOptions({headers: headers});
  }

  get apiBaseUrl(): string {
    if (this._apiBaseUrl == null) {
      this._apiBaseUrl = gApiBaseAddress;
    }
    return this._apiBaseUrl;
  }

  public get<T>(path: string, options?: RequestOptionsArgs): Observable<T> {
    const observable = this.http
      .get(this.createUrl(path), this.buildRequestOptions(options))
      .map(r => {
        return this.getResult(r);
      }).catch((error: any) => {
        return this.throwError(error);
      }).share();

    return observable;
  }

  public post<T>(path: string, data: any, options?: RequestOptionsArgs): Observable<T> {
    const observable = this.http
      .post(this.createUrl(path), data, this.buildRequestOptions(options))
      .map(r => {
        if (r.status === 204) {
          return r.ok;
        }
        return this.getResult(r);
      }).catch((error: any) => {
        return this.throwError(error);
      }).share();

    return observable;
  }

  public put<T>(path: string, data: any, options?: RequestOptionsArgs): Observable<T> {
    const observable = this.http
      .put(this.createUrl(path), data, this.buildRequestOptions(options))
      .map(r => {
        if (r.status === 204) {
          return r.ok;
        }
        return this.getResult(r);
      }).catch((error: any) => {
        return this.throwError(error);
      }).share();

    return observable;
  }

  public delete(path: string, data?: any, options?: RequestOptionsArgs): Observable<any> {
    const observable = this.http
      .delete(this.createUrl(path), data, this.buildRequestOptions(options))
      .map(r => {
        if (r.status === 204) {
          return r.ok;
        }
        return this.getResult(r);
      }).catch((error: any) => {
        return this.throwError(error);
      }).share();

    return observable;
  }

  private createUrl = (path: string): string => `${this.apiBaseUrl}${path}`;
  private getErrorMessage = (error: any): string => error.message || 'Server error';
  private regexIso8601: RegExp = /(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+)|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d)|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d)/;

  private buildRequestOptions = (options?: RequestOptionsArgs): RequestOptions => this.options.merge(options);

  private getResult = (response: Response) => {
    return response.json ? response.json() : response;
  }

  private throwError = (error: any): ErrorObservable => {
    if (error.json) {
      return Observable.throw(error);
    } else {
      const errorMessage = this.getErrorMessage(error);
      return Observable.throw(errorMessage);
    }
  }
}

