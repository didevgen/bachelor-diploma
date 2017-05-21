export class Category {
  public name: string;
  public uuid: string;
}

export class DetailCategory extends Category {
  public children: Category[] = [];
  public parent: Category;
}
