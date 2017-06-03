package ua.nure.providence.dtos.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03.06.2017.
 */
public class AccountEventDTO {

    private String accountId;

    private List<EventDTO> events = new ArrayList<>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
