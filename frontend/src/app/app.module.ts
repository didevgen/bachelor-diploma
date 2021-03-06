import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
/*
 * Platform and Environment providers/directives/pipes
 */
import { routing } from './app.routing';
// App is our top level component
import { App } from './app.component';
import { AppState, InternalStateType } from './app.service';
import { GlobalState } from './global.state';
import { NgaModule } from './theme/nga.module';
import { PagesModule } from './pages/pages.module';
import { AuthService } from './services/authentication/auth.service';
import { AuthHttp } from './services/http/auth.http';
import { ToasterModule } from 'angular2-toaster';
import { AuthenticationResolve } from './resolvers/authoriation.resolve';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SpinnerComponent } from './pages/ui/spinner/spinner.component';
import { SpinnerModule } from './pages/ui/spinner/spinner.module';

// Application wide providers
const APP_PROVIDERS = [
  AppState,
  GlobalState,
  AuthService,
  AuthHttp,
  AuthenticationResolve
];

export type StoreType = {
  state: InternalStateType,
  restoreInputValues: () => void,
  disposeOldHosts: () => void
};

/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
  bootstrap: [App],
  declarations: [
    App
  ],
  imports: [ // import Angular's modules
    BrowserModule,
    HttpModule,
    RouterModule,
    FormsModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgaModule.forRoot(),
    NgbModule.forRoot(),
    PagesModule,
    ToasterModule,
    SpinnerModule,
    routing
  ],
  providers: [ // expose our Services and Providers into Angular's dependency injection
    APP_PROVIDERS
  ],

})

export class AppModule {

  constructor(public appState: AppState) {
  }
}
