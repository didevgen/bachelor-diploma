import { Category } from '../category/category.models';

export class NamedCardHolder {
  public name: string;
  public uuid: string;
}

export class CardHolder {
  public cardNumbers: string[] = [];
  public categories: Category[] = [];
  public fullName: string;
  public uuid: string;
}
