import { OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

export abstract class UnsubscribableComponent implements OnDestroy {

  public set loading(value: boolean) {
    this._loading = value;
  }

  public get loading(): boolean {
    return this._loading;
  }

  protected subscribers: Array<Subscription> = [];

  private _loading: boolean = false;

  public ngOnDestroy(): void {
    for (const subscription of this.subscribers) {
      if (!subscription.closed) {
        subscription.unsubscribe();
      }
    }
  }
}
