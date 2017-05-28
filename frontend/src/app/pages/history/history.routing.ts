import { RouterModule, Routes } from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { HistoryComponent } from './history.component';

export const routes: Routes = [
  {
    path: '',
    component: HistoryComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
