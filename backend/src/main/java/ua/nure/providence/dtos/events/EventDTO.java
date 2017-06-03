package ua.nure.providence.dtos.events;

/**
 * Created by User on 03.06.2017.
 */
public class EventDTO {

    private String cardNumber;

    private String roomId;

    private String timeStamp;

    private String eventTypeId;

    private int inOutState;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public int getInOutState() {
        return inOutState;
    }

    public void setInOutState(int inOutState) {
        this.inOutState = inOutState;
    }
}
