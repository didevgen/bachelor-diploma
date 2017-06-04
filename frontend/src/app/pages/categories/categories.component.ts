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

  public parentCategories: Category[];
  public currentCategory: DetailCategory;
  public rows: any[];
  public count: number;
  public offset: number;
  public limit: number;

  public get isParentCategory(): boolean {
    return this.currentRoute.snapshot.params['uuid'] === undefined;
  }

  private uuid: string;

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

  public onCategoryDeleted(uuid: string) {
    if (!this.isParentCategory) {
      this.currentCategory.children = this.currentCategory.children.filter(category => category.uuid !== uuid);
    } else {
      this.parentCategories = this.parentCategories.filter(category => category.uuid !== uuid);
    }
  }

  public setPage(pageInfo: PageData): void {
    pageInfo.offset = pageInfo.limit * pageInfo.offset;
    this.loading = true;
    this.client.getCategoryHolders(this.uuid, pageInfo)
      .finally(() => this.loading = false)
      .subscribe((cardHolders) => {
        this.handleListResult(cardHolders);
      });
  }

  public subscribeToCardHolder(value: boolean, row: any) {
    row.subscribed = !value;
    if (row.subscribed === true) {
      this.subscribers.push(this.client.subscribeToHolder(row.uuid).subscribe(() => {
      }));
    } else {
      this.subscribers.push(this.client.unsubscribeFromHolder(row.uuid).subscribe(() => {
      }));
    }
  }

  private handleCategory() {
    this.subscribers.push(
      Observable.forkJoin(
        this.client.getCategory(this.uuid),
        this.client.getCategoryHolders(this.uuid, <PageData>{limit: 10, offset: 0})
      ).finally(() => this.loading = false)
        .subscribe(([categories, cardHolders]) => {
          this.currentCategory = categories;
          this.handleListResult(cardHolders);
        }));
  }

  private handleRootCategory() {
    this.subscribers.push(this.client.getAllParentCategories()
      .finally(() => this.loading = false)
      .subscribe((data: ListResult<Category>) => {
        this.parentCategories = data.data;
      }));
  }

  private handleListResult(cardHolders: any): void {
    this.rows = cardHolders.data;
    this.limit = cardHolders.limit;
    this.offset = Math.floor(cardHolders.offset / cardHolders.limit);
    this.count = cardHolders.count;
  }

}


