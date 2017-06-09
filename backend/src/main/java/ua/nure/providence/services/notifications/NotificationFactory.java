package ua.nure.providence.services.notifications;

import ua.nure.providence.enums.DeviceType;
import ua.nure.providence.services.notifications.basic.BasicNotification;
import ua.nure.providence.services.notifications.empty.DefaultNotification;
import ua.nure.providence.services.notifications.onesignal.OneSignalNotification;

/**
 * Created by User on 03.06.2017.
 */
public class NotificationFactory {

    public static INotification get(DeviceType deviceType) {
        switch (deviceType) {
            case ONE_SIGNAL: {
                return new OneSignalNotification();
            }
            case BASIC: {
                return new BasicNotification();
            }
            default: {
                return new DefaultNotification();
            }
        }
    }
}
