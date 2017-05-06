package ua.nure.providence.dtos.history;

import org.joda.time.DateTime;
import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.business.cardholder.NamedHolderDTO;
import ua.nure.providence.dtos.business.room.RoomDTO;
import ua.nure.providence.models.history.History;
import ua.nure.providence.models.zk.internal.EventType;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class HistoryDTO extends BaseUuidDTO<History> {

    private DateTime timestamp;

    private NamedHolderDTO cardHolder;

    private RoomDTO room;

    private EventType event;

    @Override
    public HistoryDTO convert(History object) {
        super.convert(object);
        setTimestamp(object.getTimeStamp());
        setCardHolder(new NamedHolderDTO().convert(object.getCardHolder()));
        setEvent(object.getEventType());
        setRoom(new RoomDTO().convert(object.getRoom()));
        return this;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public NamedHolderDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(NamedHolderDTO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }
}
