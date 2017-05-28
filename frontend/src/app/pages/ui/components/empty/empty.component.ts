import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'empty',
  templateUrl: './empty.html',
})
export class EmptyComponent {

  @Input() public text: string = 'Oooups no data';
  @Input() public title: string = 'No data';
  @Input() public link: string;

  constructor(private router: Router) {
  }

  public redirect(): void {
    if (this.link) {
      this.router.navigateByUrl(this.link);
    }
  }
}
