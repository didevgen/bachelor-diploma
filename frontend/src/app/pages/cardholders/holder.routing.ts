import { Routes, RouterModule }  from '@angular/router';

import { ModuleWithProviders } from '@angular/core';
import { HolderComponent } from './holder.component';
import { InvalidHolderComponent } from './invalid/invalid-holder.component';
import { Holders } from './base/holders.component';
import { HolderFormComponent } from './form/holder-form.component';

export const routes: Routes = [
  {
    path: '',
    component: Holders,
    children: [
      { path: 'all', component: HolderComponent },
      { path: 'invalid', component: InvalidHolderComponent }
    ],
  }, {
    path: 'room/:roomId/online',
    component: HolderComponent,
    children: [],
    pathMatch: 'full'
  },{
    path: ':uuid/edit',
    component: HolderFormComponent,
    children: [],
    pathMatch: 'full'
  },{
    path: 'new',
    component: HolderFormComponent,
    children: [],
    pathMatch: 'full'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
