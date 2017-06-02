import { Injectable } from '@angular/core';
import { AuthHttp } from '../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { AccountHistoryItem, SessionDetail } from '../../models/history/history.models';
import { Room } from '../../models/rooms/room.models';

@Injectable()
export class HistoryClient {
  constructor(private http: AuthHttp) {
  }

  public getSessions(uuid: string): Observable<SessionDetail> {
    const url = `/api/v1/history/cardHolder/${uuid}/sessions`;
    return this.http.get(url);
  }

  public findHolder(uuid: string): Observable<Room> {
    const url = `/api/v1/history/cardHolder/${uuid}/find`;
    return this.http.get(url);
  }

  public getHistory(pageInfo: PageData, searchString: string = ''): Observable<ListResult<AccountHistoryItem>> {
    let url = `/api/v1/history?`;
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

}
