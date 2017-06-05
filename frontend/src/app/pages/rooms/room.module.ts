import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgaModule } from '../../theme/nga.module';

import { routing } from './room.routing';
import { SpinnerModule } from '../ui/spinner/spinner.module';
import { DataTableModule } from 'angular2-datatable';
import { RoomComponent } from './room.component';
import { RoomClient } from './room.client';
import { RoomFormComponent } from './room-form/room-form.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    NgbModule.forRoot(),
    NgxDatatableModule,
    ReactiveFormsModule,
    DataTableModule,
    SpinnerModule,
    routing
  ],
  declarations: [
    RoomComponent,
    RoomFormComponent
  ],
  entryComponents: [
    RoomFormComponent
  ],
  providers: [
    RoomClient
  ]
})
export class RoomModule {
}
