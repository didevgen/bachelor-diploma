import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { HistoryClient } from './history.client';
import { AccountHistoryItem } from '../../models/history/history.models';

@Component({
  selector: 'history',
  styleUrls: ['./history.scss'],
  templateUrl: './history.component.html'
})
export class HistoryComponent extends UnsubscribableComponent implements OnInit, IBaseList<AccountHistoryItem> {

  public rows: AccountHistoryItem[];
  public count: number;
  public offset: number;
  public limit: number;
  public nameFilter: string;
  public userTyping: boolean = false;

  constructor(private currentRoute: ActivatedRoute,
              private historyClient: HistoryClient) {
    super();
  }

  public ngOnInit(): void {
    this.getHistory();
  }

  public nameChanged(filter: string) {
    if (!this.userTyping) {
      this.userTyping = true;
      setTimeout(() => {
        this.userTyping = false;
        this.getHistory();
      }, 500);
    }
  }

  public setPage(pageInfo: PageData): void {
    pageInfo.offset = pageInfo.limit * pageInfo.offset;
    this.loading = true;
    this.getHistory(pageInfo);
  }

  private getHistory(pageInfo: PageData = <PageData>{limit: 10, offset: 0}) {
    this.loading = true;
    this.subscribers.push(
      this.historyClient.getHistory(pageInfo, this.nameFilter)
        .finally(() => this.loading = false)
        .subscribe((history: ListResult<AccountHistoryItem>) => {
          this.handleListResult(history);
        }));
  }

  private handleListResult(history: ListResult<AccountHistoryItem>): void {
    this.rows = history.data;
    this.limit = history.limit;
    this.offset = Math.floor(history.offset / history.limit);
    this.count = history.count;
  }

}
