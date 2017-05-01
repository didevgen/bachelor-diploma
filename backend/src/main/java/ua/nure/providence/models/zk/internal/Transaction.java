package ua.nure.providence.models.zk.internal;

/**
 * Created by Providence Team on 01.05.2017.
 */
public class Transaction {
    private long cardId;

    private int pin;

    private int verified;

    private String doorId;

    private int eventType;

    private int inOutState;

    private String timeString;

    public Transaction() {
        super();
    }

    public Transaction(long cardId, int pin, int verified,
                       String doorId, int eventType, int inOutState, String time) {
        super();
        this.cardId = cardId;
        this.pin = pin;
        this.verified = verified;
        this.doorId = doorId;
        this.eventType = eventType;
        this.inOutState = inOutState;
        this.timeString = time;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getInOutState() {
        return inOutState;
    }

    public void setInOutState(int inOutState) {
        this.inOutState = inOutState;
    }

    public String getTime() {
        return timeString;
    }

    public void setTime(String time) {
        this.timeString = time;
    }

    @Override
    public String toString() {
        return "Transaction [cardId=" + cardId + ", pin=" + pin + ", verified=" + verified + ", doorId=" + doorId
                + ", eventType=" + eventType + ", inOutState=" + inOutState + ", time=" + timeString + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (cardId ^ (cardId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        return cardId == other.cardId && timeString == other.timeString;
    }
}
