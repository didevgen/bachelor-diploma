import { PageData } from '../../../models/datatable/list.data';
export interface IBaseList<T> {
  rows: T[];
  count: number;
  offset: number;
  limit: number;
  setPage(pageInfo: PageData): void;
}
