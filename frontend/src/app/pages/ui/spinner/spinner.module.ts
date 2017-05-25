import { NgModule } from '@angular/core';
import { SpinnerComponent } from './spinner.component';
import { CommonModule } from '@angular/common';
import { EmptyComponent } from '../components/empty/empty.component';
import { BaCard } from '../../../theme/components/baCard/baCard.component';

@NgModule({
  imports: [CommonModule],
  declarations: [
    SpinnerComponent,
  ],
  providers: [],
  exports: [
    SpinnerComponent]
})
export class SpinnerModule {
}
