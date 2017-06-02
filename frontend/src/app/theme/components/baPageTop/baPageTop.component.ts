import { Component } from '@angular/core';

import { GlobalState } from '../../../global.state';
import { LoginClient } from '../../../pages/login/login.client';
import { OneSignalService } from '../../../services/global/one-signal.service';

@Component({
  selector: 'ba-page-top',
  templateUrl: './baPageTop.html',
  styleUrls: ['./baPageTop.scss']
})
export class BaPageTop {
  public avatar: string = localStorage.getItem('google_image');
  public isScrolled: boolean = false;
  public isMenuCollapsed: boolean = false;

  constructor(private _state: GlobalState,
              private oneSignalService: OneSignalService,
              private loginClient: LoginClient) {
    this._state.subscribe('menu.isCollapsed', (isCollapsed) => {
      this.isMenuCollapsed = isCollapsed;
    });
  }

  public toggleMenu() {
    this.isMenuCollapsed = !this.isMenuCollapsed;
    this._state.notifyDataChanged('menu.isCollapsed', this.isMenuCollapsed);
    return false;
  }

  public logout(): void {
    this.oneSignalService.sendRequestWithSubscriptionId(this.loginClient.logout.bind(this.loginClient), () => {
      window.location.href = '/login';
    });
  }

  public scrolledChanged(isScrolled) {
    this.isScrolled = isScrolled;
  }
}
