import { Component, OnInit } from '@angular/core';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { IBaseList } from '../../components/base/base-list.component';
import { CardHolder } from '../../../models/cardh-holders/holders.model';
import { HolderClient } from '../holder.client';
import { ListResult, PageData } from '../../../models/datatable/list.data';

@Component({
  selector: 'invalid-holders',
  templateUrl: './invalid-holder.component.html'
})
export class InvalidHolderComponent extends UnsubscribableComponent implements OnInit, IBaseList<CardHolder> {

  public rows: CardHolder[];
  public count: number;
  public offset: number;
  public limit: number;
  public nameFilter: string;
  public userTyping: boolean = false;
  public roomId: string = null;

  constructor(private holderClient: HolderClient) {
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

  private getHolders(pageInfo: PageData = <PageData>{limit: 10, offset: 0}) {
    this.loading = true;
    this.subscribers.push(
      this.holderClient.getInvalidHolders(pageInfo, this.nameFilter)
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
