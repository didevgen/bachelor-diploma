import { Routes, RouterModule }  from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { RoomComponent } from './room.component';
import { RoomFormComponent } from './room-form/room-form.component';

export const routes: Routes = [
  {
    path: '',
    component: RoomComponent,
    children: [],
    pathMatch: 'full'
  },{
    path: 'new',
    component: RoomFormComponent,
    children: [],
    pathMatch: 'full'
  },{
    path: ':uuid/edit',
    component: RoomFormComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
