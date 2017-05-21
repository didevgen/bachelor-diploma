import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgaModule } from '../../theme/nga.module';

import { routing } from './categories.routing';

import { CategoriesComponent } from './categories.component';
import { CategoryClient } from './categories.client';
import { CategoryComponent } from './category-grid/category-component';
import { SpinnerModule } from '../ui/spinner/spinner.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    NgbModule.forRoot(),
    NgxDatatableModule,
    SpinnerModule,
    routing
  ],
  declarations: [
    CategoriesComponent,
    CategoryComponent,
  ],
  providers: [
    CategoryClient
  ]
})
export class CategoriesModule {
}
