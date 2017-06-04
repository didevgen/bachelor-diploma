import { Injectable } from '@angular/core';
import { AuthHttp } from '../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { CardHolder } from '../../models/cardh-holders/holders.model';
import { DashboardClient } from '../dashboard/client/dashboard.client';

@Injectable()
export class HolderClient extends DashboardClient {
  constructor(private http: AuthHttp) {
    super(http);
  }

  public getHolders(pageInfo: PageData, searchString: string = ''): Observable<ListResult<CardHolder>> {
    let url = `/api/v1/holders?`;
    if (pageInfo.limit !== undefined) {
      url += `&limit=${pageInfo.limit}`;
    }
    if (pageInfo.offset !== undefined) {
      url += `&offset=${pageInfo.offset}`;
    }
    if (searchString) {
      url += `&name=${searchString}`;
    }
    return this.http.get(url);
  }

  public getInvalidHolders(pageInfo: PageData, searchString: string = ''): Observable<ListResult<CardHolder>> {
    let url = `/api/v1/holders/invalid/all?`;
    if (pageInfo.limit !== undefined) {
      url += `&limit=${pageInfo.limit}`;
    }
    if (pageInfo.offset !== undefined) {
      url += `&offset=${pageInfo.offset}`;
    }
    if (searchString) {
      url += `&cardNumber=${searchString}`;
    }
    return this.http.get(url);
  }

  public getRoomHolders(roomId: string, pageInfo: PageData): Observable<ListResult<CardHolder>> {
    let url = `/api/v1/history/room/${roomId}/holders/online?`;
    if (pageInfo.limit !== undefined) {
      url += `&limit=${pageInfo.limit}`;
    }
    if (pageInfo.offset !== undefined) {
      url += `&offset=${pageInfo.offset}`;
    }
    return this.http.get(url);
  }

}
