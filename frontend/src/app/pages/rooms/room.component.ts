import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { NamedCardHolder } from '../../models/cardh-holders/holders.model';
import { RoomClient } from './room.client';
import { Room } from '../../models/rooms/room.models';

@Component({
  selector: 'rooms',
  styleUrls: ['./room.scss'],
  templateUrl: './room.component.html'
})
export class RoomComponent extends UnsubscribableComponent implements OnInit, IBaseList<NamedCardHolder> {

  public rows: Room[];
  public count: number;
  public offset: number;
  public limit: number;
  public nameFilter: string;
  public userTyping: boolean = false;

  constructor(private currentRoute: ActivatedRoute,
              private roomClient: RoomClient) {
    super();
  }

  public ngOnInit(): void {
    this.getRooms();
  }

  public nameChanged(filter: string) {
    if (!this.userTyping) {
      this.userTyping = true;
      setTimeout(() => {
        this.userTyping = false;
        this.getRooms();
      }, 500);
    }
  }

  public setPage(pageInfo: PageData): void {
    pageInfo.offset = pageInfo.limit * pageInfo.offset;
    this.loading = true;
    this.getRooms(pageInfo);
  }

  private getRooms(pageInfo: PageData = <PageData>{limit: 10, offset: 0}) {
    this.subscribers.push(
      this.roomClient.getRooms(pageInfo, this.nameFilter)
      .finally(() => this.loading = false)
      .subscribe((rooms: ListResult<Room>) => {
        this.handleListResult(rooms);
      }));
  }

  private handleListResult(rooms: ListResult<Room>): void {
    this.rows = rooms.data;
    this.limit = rooms.limit;
    this.offset = Math.floor(rooms.offset / rooms.limit);
    this.count = rooms.count;
  }

}
