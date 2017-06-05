import { Injectable } from '@angular/core';
import { AuthHttp } from '../../services/http/auth.http';
import { Observable } from 'rxjs';
import { ListResult, PageData } from '../../models/datatable/list.data';
import { Category, DetailCategory } from '../../models/category/category.models';
import { NamedCardHolder } from '../../models/cardh-holders/holders.model';

@Injectable()
export class CategoryClient {
  constructor(private http: AuthHttp) {

  }

  public createCategory(data: any): Observable<Category> {
    return this.http.post(`/api/v1/categories/`, data);
  }

  public deleteCategory(uuid: string): Observable<any> {
    return this.http.delete(`/api/v1/categories/${uuid}`);
  }

  public updateCategory(uuid: string, data: any): Observable<any> {
    return this.http.put(`/api/v1/categories/${uuid}`, data);
  }

  public getCategory(uuid: string): Observable<DetailCategory> {
    return this.http.get(`/api/v1/categories/${uuid}`);
  }

  public subscribeToHolder(uuid: string): Observable<any> {
    return this.http.post(`/api/v1/subscriptions/subscribe`, {data: uuid});
  }

  public unsubscribeFromHolder(uuid: string): Observable<any> {
    return this.http.post(`/api/v1/subscriptions/unsubscribe`, {data: uuid});
  }

  public getCategoryHolders(uuid: string, pageInfo: PageData): Observable<ListResult<NamedCardHolder>> {
    let url = `/api/v1/categories/${uuid}/holders?`;
    if (pageInfo.limit !== undefined) {
      url += `&limit=${pageInfo.limit}`;
    }
    if (pageInfo.offset !== undefined) {
      url += `&offset=${pageInfo.offset}`;
    }
    return this.http.get(url);
  }

  public getAllParentCategories(): Observable<ListResult<Category>> {
    return this.http.get('/api/v1/categories/parentCategories/all');
  }
}
