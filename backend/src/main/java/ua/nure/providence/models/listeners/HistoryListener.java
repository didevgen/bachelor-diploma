package ua.nure.providence.models.listeners;

import ua.nure.providence.enums.DeviceType;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.SubscribedDevice;
import ua.nure.providence.models.history.History;
import ua.nure.providence.services.notifications.NotificationFactory;

import javax.persistence.PostPersist;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by User on 03.06.2017.
 */
public class HistoryListener {

    @PostPersist
    public void onPostPersist(History history) {
        CardHolder holder = history.getCardHolder();
        holder.getSubscribers()
                .stream().map(User::getDevices)
                .map(subscribedDevices -> subscribedDevices.stream()
                        .collect(Collectors.groupingBy(SubscribedDevice::getType)))
                .forEach(subscriber -> subscriber.forEach((key, value) -> NotificationFactory.get(key)
                        .sendNotification(
                                history,
                                value
                                        .stream().map(SubscribedDevice::getSubscriptionKey)
                                        .collect(Collectors.toList()),
                                holder
                        )));
    }
}
