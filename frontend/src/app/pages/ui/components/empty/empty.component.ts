import {Component, Input} from '@angular/core';

@Component({
  selector: 'empty',
  templateUrl: './empty.html',
})
export class EmptyComponent {

  @Input() public text: string = 'Oooups no data';
  @Input() public title: string = 'No data';

  constructor() {
  }
}
