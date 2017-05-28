import { RouterModule, Routes } from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { HistoryComponent } from './history.component';
import { HolderHistoryComponent } from './holder/holder-history.component';

export const routes: Routes = [
  {
    path: '',
    component: HistoryComponent,
    children: [],
    pathMatch: 'full'
  },
  {
    path: 'holder/:uuid/sessions',
    component: HolderHistoryComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
