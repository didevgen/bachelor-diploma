import { Routes, RouterModule }  from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { CategoriesComponent } from './categories.component';

export const routes: Routes = [
  {
    path: '',
    component: CategoriesComponent,
    children: [],
    pathMatch: 'full'
  }, {
    path: ':uuid',
    component: CategoriesComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
