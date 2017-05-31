import { Injectable } from '@angular/core';
import { AuthHttp } from '../../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../../models/datatable/list.data';

@Injectable()
export class DashboardClient {
  constructor(private authService: AuthHttp) {

  }

  public subscribeToHolder(uuid: string): Observable<any> {
    return this.authService.post(`/api/v1/subscriptions/subscribe`, {data: uuid});
  }

  public oneSignalSubscribeToHolder(uuid: string, signalId: string): Observable<any> {
    return this.authService.post(`/api/v1/subscriptions/subscribe/signal`, {data: uuid, oneSignalId: signalId});
  }

  public oneSignalUnsubscribeFromHolder(uuid: string, signalId: string): Observable<any> {
    return this.authService.post(`/api/v1/subscriptions/unsubscribe/signal`, {data: uuid, oneSignalId: signalId});
  }

  public unsubscribeFromHolder(uuid: string): Observable<any> {
    return this.authService.post(`/api/v1/subscriptions/unsubscribe`, {data: uuid});
  }

  public getSubscriptions(pageData: PageData): Observable<ListResult<any>> {
    let baseUrl = '/api/v1/subscriptions/all?';

    if (pageData.limit !== undefined) {
      baseUrl += `&limit=${pageData.limit}`;
    }
    if (pageData.offset !== undefined) {
      baseUrl += `&offset=${pageData.offset}`;
    }
    return this.authService.get(baseUrl);
  }
}
