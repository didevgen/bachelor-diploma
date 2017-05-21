import { Component, Input } from '@angular/core';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { Category, DetailCategory } from '../../../models/category/category.models';

@Component({
  selector: 'category',
  templateUrl: './category.html',
  styleUrls: ['./category.scss']
})
export class CategoryComponent extends UnsubscribableComponent {

  @Input() public data: DetailCategory | Category;

  constructor() {
    super();
  }

}
