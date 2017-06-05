import { Injectable } from '@angular/core';
import { AuthHttp } from '../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { Room } from '../../models/rooms/room.models';

@Injectable()
export class RoomClient {
  constructor(private http: AuthHttp) {

  }

  public createRoom(data: any): Observable<Room> {
    return this.http.post('/api/v1/rooms', data);
  }

  public updateRoom(uuid: string, data: any): Observable<Room> {
    return this.http.put(`/api/v1/rooms/${uuid}`, data);
  }

  public deleteRoom(uuid: string): Observable<any> {
    return this.http.delete(`/api/v1/rooms/${uuid}`);
  }

  public getRoom(uuid: string): Observable<Room> {
    return this.http.get(`/api/v1/rooms/${uuid}`);
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
