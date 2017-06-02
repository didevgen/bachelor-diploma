import { Component } from '@angular/core';
import { Routes } from '@angular/router';

import { BaMenuService } from '../theme';
import { PAGES_MENU } from './pages.menu';
import { OneSignalService } from '../services/global/one-signal.service';
import { GlobalHttp } from '../services/global/global.http';

@Component({
  selector: 'pages',
  template: `
    <ba-sidebar></ba-sidebar>
    <ba-page-top></ba-page-top>
    <div class="al-main">
      <div class="al-content">
        <ba-content-top></ba-content-top>
        <router-outlet></router-outlet>
      </div>
    </div>
    <footer class="al-footer clearfix">
    </footer>
    <ba-back-top position="200"></ba-back-top>
    `
})
export class Pages {

  constructor(private _menuService: BaMenuService,
              private globalHttp: GlobalHttp,
              private oneSignal: OneSignalService) {
  }

  ngOnInit() {
    this._menuService.updateMenuByRoutes(<Routes>PAGES_MENU);

    this.oneSignal.init();
    this.oneSignal.sendRequestWithSubscriptionId(this.globalHttp.registerApplication.bind(this.globalHttp), () => {
    });

  }
}
