package ua.nure.providence.models.history;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.nure.providence.models.base.BaseEntity;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.models.listeners.HistoryListener;
import ua.nure.providence.models.zk.internal.EventType;

import javax.persistence.*;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@EntityListeners(HistoryListener.class)
@Table(name = "history")
public class History extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CardHolder cardHolder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EventType eventType;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timeStamp;

    @Column
    private int inOutState;

    public History() {
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getInOutState() {
        return inOutState;
    }

    public void setInOutState(int inOutState) {
        this.inOutState = inOutState;
    }

}
