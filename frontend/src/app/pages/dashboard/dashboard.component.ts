import { Component, OnInit } from '@angular/core';
import { DashboardClient } from './client/dashboard.client';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { Page } from '../../models/datatable/page';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';

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

  public setPage(pageInfo: PageData) {
    this.subscribers.push(this.dashboardClient.getSubscriptions(pageInfo).subscribe((data: ListResult<any>) => {
      this.rows = data.data;
      this.limit = data.limit;
      this.offset = data.offset / data.limit;
      this.count = data.count;
    }));
  }
}
