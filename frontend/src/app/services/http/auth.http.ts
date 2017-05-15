import { Injectable } from "@angular/core";
import { Headers, RequestMethod, RequestOptions, RequestOptionsArgs, URLSearchParams } from "@angular/http";
import { Observable } from "rxjs";
import { AuthService } from "../authentication/auth.service";

@Injectable()
export class AuthHttp {
  constructor(
    private authenticationService: AuthService
  ) {
  }

  public get(url: string, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Get });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  public post(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Post, body: body });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  public delete(url: string, body?: any, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Delete, body: body });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  public patch(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Patch, body: body });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  public put(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Put, body: body });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  public head(url: string, options?: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions({ method: RequestMethod.Put });
    newOptions = newOptions.merge(options);
    return this.sendRequest(url, newOptions);
  }

  private sendRequest(url: string, options: RequestOptionsArgs): Observable<any> {
    let newOptions = new RequestOptions();
    newOptions.method = options.method;

    if (options.search != null) {
      newOptions.search = new URLSearchParams(options.search.toString()).clone();
    }

    if (options.headers != null) {
      newOptions.headers = new Headers(options.headers.toJSON());
    }

    if (options.body != null) {
      newOptions.body = options.body;
    }

    return this.authenticationService.makeAuthenticatedCall(url, newOptions);
  }
}
