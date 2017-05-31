import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { CardHolder } from '../../models/cardh-holders/holders.model';
import { HolderClient } from './holder.client';
declare const OneSignal: any;

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

  constructor(private currentRoute: ActivatedRoute,
              private ngZone: NgZone,
              private holderClient: HolderClient) {
    super();
  }

  public ngOnInit(): void {
    this.getHolders();
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
    OneSignal.push(() => {
      OneSignal.isPushNotificationsEnabled().then((isEnabled) => {
        if (isEnabled) {
          OneSignal.getUserId().then(userId => {
          });
        } else {
          this.ngZone.run(() => {
            row.subscribed = !value;
            if (row.subscribed === true) {
              this.subscribers.push(this.holderClient.subscribeToHolder(row.uuid).subscribe(() => {
              }));
            } else {
              this.subscribers.push(this.holderClient.unsubscribeFromHolder(row.uuid).subscribe(() => {
              }));
            }
          });
        }
      });
    });

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

  private handleListResult(holders: ListResult<CardHolder>): void {
    this.rows = holders.data;
    this.limit = holders.limit;
    this.offset = Math.floor(holders.offset / holders.limit);
    this.count = holders.count;
  }

}
