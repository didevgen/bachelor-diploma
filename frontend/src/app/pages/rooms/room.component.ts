import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { PageData } from '../../models/datatable/list.data';
import { IBaseList } from '../components/base/base-list.component';
import { NamedCardHolder } from '../../models/cardh-holders/holders.model';

@Component({
  selector: 'categories',
  styleUrls: ['./categories.scss'],
  templateUrl: './categories.html'
})
export class RoomComponent extends UnsubscribableComponent implements OnInit, IBaseList<NamedCardHolder> {

  public rows: any[];
  public count: number;
  public offset: number;
  public limit: number;

  constructor(private currentRoute: ActivatedRoute) {
    super();
  }

  public ngOnInit(): void {
    this.subscribers.push(this.currentRoute.params.subscribe(params => {
    }));
  }

  public setPage(pageInfo: PageData): void {
    pageInfo.offset = pageInfo.limit * pageInfo.offset;
    this.loading = true;
  }

  private handleListResult(cardHolders: any): void {
    this.rows = cardHolders.data;
    this.limit = cardHolders.limit;
    this.offset = Math.floor(cardHolders.offset / cardHolders.limit);
    this.count = cardHolders.count;
  }

}
