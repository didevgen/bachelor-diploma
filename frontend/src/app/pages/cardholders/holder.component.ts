import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { CardHolder } from '../../models/cardh-holders/holders.model';
import { HolderClient } from './holder.client';

@Component({
  selector: 'holders',
  styleUrls: ['./holder.scss'],
  templateUrl: './holder.component.html'
})
export class HolderComponent extends UnsubscribableComponent implements OnInit, IBaseList<CardHolder> {

  public rows: CardHolder[];
  public count: number;
  public offset: number;
  public limit: number;
  public nameFilter: string;
  public userTyping: boolean = false;
  public roomId: string = null;

  constructor(private currentRoute: ActivatedRoute,
              private ngZone: NgZone,
              private holderClient: HolderClient) {
    super();
  }

  public ngOnInit(): void {
    this.roomId = this.currentRoute.snapshot.params['roomId'];
    if (this.roomId) {
      this.getRoomHolders(this.currentRoute.snapshot.params['roomId']);
    } else {
      this.getHolders();
    }
  }

  public nameChanged(filter: string) {
    if (!this.userTyping) {
      this.userTyping = true;
      setTimeout(() => {
        this.userTyping = false;
        this.getHolders();
      }, 500);
    }
  }

  public setPage(pageInfo: PageData): void {
    pageInfo.offset = pageInfo.limit * pageInfo.offset;
    this.loading = true;
    this.getHolders(pageInfo);
  }

  public subscribeToCardHolder(value: boolean, row: any) {
    row.subscribed = !value;
    if (row.subscribed === true) {
      this.subscribers.push(this.holderClient.subscribeToHolder(row.uuid).subscribe(() => {
      }));
    } else {
      this.subscribers.push(this.holderClient.unsubscribeFromHolder(row.uuid).subscribe(() => {
      }));
    }
  }

  private getHolders(pageInfo: PageData = <PageData>{limit: 10, offset: 0}) {
    this.loading = true;
    this.subscribers.push(
      this.holderClient.getHolders(pageInfo, this.nameFilter)
        .finally(() => this.loading = false)
        .subscribe((rooms: ListResult<CardHolder>) => {
          this.handleListResult(rooms);
        }));
  }

  private getRoomHolders(roomId: string, pageInfo: PageData = <PageData>{limit: 10, offset: 0}) {
    this.loading = true;
    this.subscribers.push(
      this.holderClient.getRoomHolders(roomId, pageInfo)
        .finally(() => this.loading = false)
        .subscribe((rooms: ListResult<CardHolder>) => {
          this.handleListResult(rooms);
        }));
  }

  private handleListResult(holders: ListResult<CardHolder>): void {
    this.rows = holders.data;
    this.limit = holders.limit;
    this.offset = Math.floor(holders.offset / holders.limit);
    this.count = holders.count;
  }

}
