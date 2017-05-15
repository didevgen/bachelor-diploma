import { AfterViewInit, Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { LoginClient } from "./login.client";
import { AuthService } from "../../services/authentication/auth.service";
import { LoginResponse } from "../../models/auth/auth.models";

declare const gapi: any;
@Component({
  selector: 'login',
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login implements OnInit, AfterViewInit {

  public auth2: any;
  public form: FormGroup;
  public email: AbstractControl;
  public password: AbstractControl;
  public submitted: boolean = false;
  public imageURL: string;
  public gmail: string;
  public name: string;
  public token: string;

  constructor(private fb: FormBuilder,
              private loginClient: LoginClient,
              private authService: AuthService) {
  }

  public ngOnInit(): void {
    this.form = this.fb.group({
      'email': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(3)])]
    });

    this.email = this.form.controls['email'];
    this.password = this.form.controls['password'];
  }

  public ngAfterViewInit() {
    this.googleInit();
  }

  public googleInit() {
    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '428802221440-p89kcv72hbi32j69a63j9p5pl4eufk74.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin',
        scope: 'profile email'
      });
      this.attachSignin(document.getElementById('google-login-button'));
    });
  }

  public attachSignin(element) {
    this.auth2.attachClickHandler(element, {},
      (googleUser) => {

        let profile = googleUser.getBasicProfile();
        localStorage.setItem('token', googleUser.getAuthResponse().id_token);
        localStorage.setItem('google_image', profile.getImageUrl());
        localStorage.setItem('google_name', profile.getName());
        localStorage.setItem('google_email', profile.getEmail());
        localStorage.setItem('google_id', profile.getId());
        this.loginClient.oauthLogin(googleUser.getAuthResponse().id_token).subscribe((result: any) => {
        })
      });
  }

  public getData(): void {
    this.token = localStorage.getItem('token');
    this.imageURL = localStorage.getItem('google_image');
    this.name = localStorage.getItem('google_name');
    this.gmail = localStorage.getItem('google_email');
  }

  public onSubmit(values: { email: string, password: string }): void {
    this.submitted = true;
    if (this.form.valid) {
      this.loginClient.login(values.email, values.password).subscribe((result: any) => {
        localStorage.setItem('token', result.headers.get('x-auth-token'));
      })
    }
  }
}
