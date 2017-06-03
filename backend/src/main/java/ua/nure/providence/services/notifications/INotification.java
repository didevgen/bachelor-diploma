package ua.nure.providence.services.notifications;

import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.history.History;

import java.util.List;

/**
 * Created by User on 03.06.2017.
 */
public interface INotification {

    void sendNotification(History history, List<String> keys, CardHolder holder);
}
