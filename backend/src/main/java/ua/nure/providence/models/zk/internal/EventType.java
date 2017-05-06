package ua.nure.providence.models.zk.internal;

import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "event_types")
public class EventType extends BaseEntity {

    @Column
    private int code;

    @Column
    private String eventName;

    @Column(length=10485760)
    private String description;

    public EventType() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
