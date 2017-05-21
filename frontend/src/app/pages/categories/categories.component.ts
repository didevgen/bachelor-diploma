import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../theme/unsubscribable.component';
import { CategoryClient } from './categories.client';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { Category, DetailCategory } from '../../models/category/category.models';
import { Observable } from 'rxjs';
import { IBaseList } from '../components/base/base-list.component';
import { NamedCardHolder } from '../../models/cardh-holders/holders.model';

@Component({
  selector: 'categories',
  styleUrls: ['./categories.scss'],
  templateUrl: './categories.html'
})
export class CategoriesComponent extends UnsubscribableComponent implements OnInit,
  IBaseList<NamedCardHolder> {

  public parentCategories: Category[] = [];
  public currentCategory: DetailCategory;
  public rows: any[] = [];
  public count: number;
  public offset: number;
  public limit: number;

  public get isParentCategory(): boolean {
    return this.currentRoute.snapshot.params['uuid'] === undefined;
  }

  private uuid: string;

  public columns = [
    {prop: 'name'},
  ];

  constructor(private client: CategoryClient,
              private currentRoute: ActivatedRoute) {
    super();
  }


  public ngOnInit(): void {
    this.subscribers.push(this.currentRoute.params.subscribe(params => {
      this.uuid = params['uuid'];
      this.loading = true;
      if (!this.isParentCategory) {
        this.handleCategory();
      } else {
        this.handleRootCategory();
      }
    }));
  }

  public setPage(pageInfo: PageData): void {
  }

  private handleCategory() {
    this.subscribers.push(
      Observable.forkJoin(
        this.client.getCategory(this.uuid),
        this.client.getCategoryHolders(this.uuid, <PageData>{limit: 10, offset: 0})
      ).finally(() => this.loading = false)
        .subscribe(([categories, cardHolders]) => {
          this.currentCategory = categories;
          this.rows = cardHolders.data.map(item => {
            return {name: item.name};
          });
          this.limit = cardHolders.limit;
          this.offset = cardHolders.offset;
          this.count = cardHolders.count;
        }));
  }

  private handleRootCategory() {
    this.subscribers.push(this.client.getAllParentCategories()
      .finally(() => this.loading = false)
      .subscribe((data: ListResult<Category>) => {
        this.parentCategories = data.data;
      }));
  }

}


