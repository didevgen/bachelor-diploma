import { Routes, RouterModule }  from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { HolderComponent } from './holder.component';

export const routes: Routes = [
  {
    path: '',
    component: HolderComponent,
    children: [],
    pathMatch: 'full'
  }, {
    path: 'room/:roomId/online',
    component: HolderComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
