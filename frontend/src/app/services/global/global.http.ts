import { Injectable } from '@angular/core';
import { AuthHttp } from '../http/auth.http';
import { Observable } from 'rxjs';
@Injectable()
export class GlobalHttp {
  constructor(private authHttp: AuthHttp) {
  }

  public registerApplication(subscriptionKey: string): Observable<any> {
    return this.authHttp.post(`/api/v1/subscriptions/device/register`, {
      subscriptionKey,
      deviceType: 'ONE_SIGNAL'
    });
  }
}
