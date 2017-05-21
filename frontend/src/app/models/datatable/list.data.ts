export class ListResult<T> {
  public data: T[];
  public limit: number;
  public offset: number;
  public count: number;
}

export class PageData {
  public count?: number;
  public limit?: number;
  public offset?: number;
  public pageSize?: number;
}
