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

  public getCategory(uuid: string): Observable<DetailCategory> {
    return this.http.get(`/api/v1/categories/${uuid}`);
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
