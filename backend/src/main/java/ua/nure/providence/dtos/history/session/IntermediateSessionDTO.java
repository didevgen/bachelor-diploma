package ua.nure.providence.dtos.history.session;

/**
 * Created by Providence Team on 09.05.2017.
 */
public class IntermediateSessionDTO {

    private String timestamp;

    private int inOutState;

    public IntermediateSessionDTO(String timestamp, int inOutState) {
        this.timestamp = timestamp;
        this.inOutState = inOutState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getInOutState() {
        return inOutState;
    }

    public void setInOutState(int inOutState) {
        this.inOutState = inOutState;
    }
}
