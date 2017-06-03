package ua.nure.providence.services.notifications;

import ua.nure.providence.enums.DeviceType;
import ua.nure.providence.services.notifications.empty.EmptyNotification;
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
                return new EmptyNotification();
            }
            default: {
                return new EmptyNotification();
            }
        }
    }
}
