import { Component, Input, Output, EventEmitter } from '@angular/core';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { Category, DetailCategory } from '../../../models/category/category.models';
import { CategoryClient } from '../categories.client';

@Component({
  selector: 'category',
  templateUrl: './category.html',
  styleUrls: ['./category.scss']
})
export class CategoryComponent extends UnsubscribableComponent {

  @Input() public data: DetailCategory | Category;

  @Output() public onCategoryDeleted: EventEmitter<string> = new EventEmitter<string>();

  constructor(private categoryClient: CategoryClient) {
    super();
  }

  public deleteCategory(uuid: string): void {
    this.categoryClient.deleteCategory(uuid).subscribe(() => {
      this.onCategoryDeleted.emit(uuid);
    });
  }

}
