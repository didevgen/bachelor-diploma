import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';

declare const OneSignal: any;

@Injectable()
export class OneSignalService {
  constructor(private ngZone: NgZone) {
  }

  public init(): void {
    OneSignal.push(['init', {
      appId: '276dc1e0-08e7-41c3-8db9-d8a5e4186676',
      subdomainName: 'providence-eye',
      httpPermissionRequest: {
        enable: true
      },
      persistNotification: false,
      promptOptions: {
        siteName: 'Providence Appl',
        actionMessage: 'If you want to receive notifications for this browser, please subscribe',
        exampleNotificationTitle: 'Example notification',
        exampleNotificationMessage: 'This is an example notification',
        exampleNotificationCaption: 'You can unsubscribe anytime',
        acceptButtonText: 'ALLOW',
        cancelButtonText: 'NO THANKS'
      }
    }]);
  }

  public sendRequestWithSubscriptionId(request: (userId: string) => Observable<any>,
                                subscription: (result: any) => void) {
    OneSignal.push(() => {
      OneSignal.isPushNotificationsEnabled().then((isEnabled) => {
        if (!isEnabled) {
          OneSignal.registerForPushNotifications();
        } else {
          OneSignal.getUserId().then((userId) => {
            this.ngZone.run(() => {
              request(userId).subscribe(subscription);
            });
          });
        }
      });
    });
  };
}
