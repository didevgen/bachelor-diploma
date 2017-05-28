import { Component, OnInit } from '@angular/core';
import { DashboardClient } from './client/dashboard.client';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { Page } from '../../models/datatable/page';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { CardHolder } from '../../models/cardh-holders/holders.model';

@Component({
  selector: 'dashboard',
  styleUrls: ['./dashboard.scss'],
  templateUrl: './dashboard.html'
})
export class Dashboard extends UnsubscribableComponent implements OnInit, IBaseList<any> {

  public rows: any[] = [];
  public count: number;
  public offset: number;
  public limit: number;

  public get showTable(): boolean {
    return this.rows && this.rows.length > 0;
  }

  private page: Page = new Page();

  constructor(private dashboardClient: DashboardClient) {
    super();
  }

  public ngOnInit(): void {
    this.setPage(<PageData>{offset: 0, limit: 10});
  }

  public unsubscribe(uuid: string, row: any): void {
    this.dashboardClient.unsubscribeFromHolder(uuid).subscribe(data => {
      row.unsubscribed = true;
    }, error => {
    });
  }

  public subscribe(uuid: string, row: any): void {
    this.dashboardClient.subscribeToHolder(uuid).subscribe(data => {
      row.unsubscribed = false;
    }, error => {
    });
  }

  public setPage(pageInfo: PageData) {
    this.subscribers.push(this.dashboardClient.getSubscriptions(pageInfo).subscribe((data: ListResult<CardHolder>) => {
      this.rows = data.data;
      this.limit = data.limit;
      this.offset = Math.floor(data.offset / data.limit) * this.limit;
      this.count = data.count;
    }));
  }
}
