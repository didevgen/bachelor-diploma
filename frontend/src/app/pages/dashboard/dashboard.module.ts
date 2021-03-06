import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgaModule } from '../../theme/nga.module';

import { Dashboard } from './dashboard.component';
import { routing } from './dashboard.routing';
import { DashboardClient } from './client/dashboard.client';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SpinnerModule } from '../ui/spinner/spinner.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    NgxDatatableModule,
    SpinnerModule,
    routing
  ], declarations: [
    Dashboard
  ],
  providers: [
    DashboardClient,
  ]
})
export class DashboardModule {
}
