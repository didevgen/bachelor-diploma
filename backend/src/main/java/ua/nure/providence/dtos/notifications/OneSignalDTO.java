package ua.nure.providence.dtos.notifications;

import ua.nure.providence.models.history.History;

import java.util.List;

/**
 * Created by User on 03.06.2017.
 */
public class OneSignalDTO {
    public String app_id;

    public List<String> include_player_ids;

    public OneSignalContentDTO contents;

    public OneSignalDTO(String appId, History history, List<String> keys) {
        this.app_id = appId;
        this.contents = new OneSignalContentDTO(history, history.getCardHolder());
        this.include_player_ids = keys;
    }
}
