import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgaModule } from '../../theme/nga.module';
import { NguiAutoCompleteModule } from '@ngui/auto-complete';

import { routing } from './categories.routing';

import { CategoriesComponent } from './categories.component';
import { CategoryClient } from './categories.client';
import { CategoryComponent } from './category-grid/category-component';
import { SpinnerModule } from '../ui/spinner/spinner.module';
import { DataTableModule } from 'angular2-datatable';
import { CategoryFormComponent } from './category-form/category-form.component';
import { ToasterModule } from 'angular2-toaster';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AppTranslationModule,
    NgaModule,
    NguiAutoCompleteModule,
    NgbModule.forRoot(),
    NgxDatatableModule,
    DataTableModule,
    SpinnerModule,
    ToasterModule,
    routing
  ],
  declarations: [
    CategoriesComponent,
    CategoryComponent,
    CategoryFormComponent
  ],
  providers: [
    CategoryClient
  ]
})
export class CategoriesModule {
}
