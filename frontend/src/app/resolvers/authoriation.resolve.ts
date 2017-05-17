import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/authentication/auth.service';

@Injectable()
export class AuthenticationResolve implements Resolve<boolean> {

  constructor(private authService: AuthService, private router: Router) {

  }

  public resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    if (this.authService.isAuthenticated) {
      return Observable.of(true);
    } else {
      this.router.navigateByUrl('/login');
      return Observable.of(false);
    }
  }
}
