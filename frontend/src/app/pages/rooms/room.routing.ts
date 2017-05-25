import { Routes, RouterModule }  from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { RoomComponent } from './room.component';

export const routes: Routes = [
  {
    path: '',
    component: RoomComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
