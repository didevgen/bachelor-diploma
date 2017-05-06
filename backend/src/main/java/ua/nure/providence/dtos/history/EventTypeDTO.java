package ua.nure.providence.dtos.history;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.zk.internal.EventType;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class EventTypeDTO extends BaseUuidDTO<EventType> {

    private int code;

    private String eventName;

    @Override
    public EventTypeDTO convert(EventType object) {
        super.convert(object);
        setCode(object.getCode());
        setEventName(object.getEventName());
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
