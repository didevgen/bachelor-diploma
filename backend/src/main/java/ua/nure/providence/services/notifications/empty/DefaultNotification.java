package ua.nure.providence.services.notifications.empty;

import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.history.History;
import ua.nure.providence.services.notifications.INotification;

import java.util.List;

/**
 * Created by User on 03.06.2017.
 */
public class DefaultNotification implements INotification {
    @Override
    public void sendNotification(History history, List<String> subscribers, CardHolder holder) {

    }
}
