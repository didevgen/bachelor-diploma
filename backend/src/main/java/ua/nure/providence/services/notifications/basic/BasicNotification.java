package ua.nure.providence.services.notifications.basic;

import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.history.History;
import ua.nure.providence.services.notifications.INotification;

import java.util.List;

/**
 * Created by User on 08.06.2017.
 */
public class BasicNotification implements INotification {
    @Override
    public void sendNotification(History history, List<String> subscribers, CardHolder holder) {

    }
}
