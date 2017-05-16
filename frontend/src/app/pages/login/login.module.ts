import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { AppTranslationModule } from '../../app.translation.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgaModule } from '../../theme/nga.module';
import { Login } from './login.component';
import { routing }       from './login.routing';
import { LoginClient } from './login.client';
import { ToasterModule } from 'angular2-toaster';


@NgModule({
  imports: [
    CommonModule,
    AppTranslationModule,
    ReactiveFormsModule,
    FormsModule,
    NgaModule,
    ToasterModule,
    routing
  ],
  declarations: [
    Login
  ],
  providers: [LoginClient]
})
export class LoginModule {
}
