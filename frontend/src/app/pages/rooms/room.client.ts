import { Injectable } from '@angular/core';
import { AuthHttp } from '../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { Room } from '../../models/rooms/room.models';

@Injectable()
export class RoomClient {
  constructor(private http: AuthHttp) {

  }

  public getRooms(pageInfo: PageData, searchString: string = ''): Observable<ListResult<Room>> {
    let url = `/api/v1/rooms?`;
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
