import { Injectable } from '@angular/core';
import { AuthHttp } from '../../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../../models/datatable/list.data';

@Injectable()
export class DashboardClient {
  constructor(private authService: AuthHttp) {

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
