package ua.nure.providence.dtos.notifications;

import org.joda.time.format.DateTimeFormat;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.history.History;

/**
 * Created by User on 03.06.2017.
 */
public class OneSignalContentDTO {
    private String en;

    public OneSignalContentDTO(History history, CardHolder holder) {
        StringBuilder sb = new StringBuilder();
        sb.append(holder.getFullName()).append(" ").append("has ");
        if (history.getInOutState() == 0) {
            sb.append("left from ");
        } else {
            sb.append("arrived to ");
        }
        sb.append(history.getRoom().getName()).append(" ")
                .append("at ").append(history.getTimeStamp()
                .toString(DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss")));
        this.en = sb.toString();
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
