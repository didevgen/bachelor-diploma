import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgaModule } from '../../theme/nga.module';

import { routing } from './holder.routing';
import { SpinnerModule } from '../ui/spinner/spinner.module';
import { DataTableModule } from 'angular2-datatable';
import { HolderComponent } from './holder.component';
import { HolderClient } from './holder.client';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    NgbModule.forRoot(),
    NgxDatatableModule,
    DataTableModule,
    SpinnerModule,
    routing
  ],
  declarations: [
    HolderComponent
  ],
  providers: [
    HolderClient
  ]
})
export class HolderModule {
}