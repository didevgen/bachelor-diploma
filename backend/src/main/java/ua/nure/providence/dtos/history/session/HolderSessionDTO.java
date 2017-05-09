package ua.nure.providence.dtos.history.session;

import org.joda.time.DateTime;
import ua.nure.providence.dtos.business.room.RoomDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Providence Team on 09.05.2017.
 */
public class HolderSessionDTO {

    private String sessionStart;

    private String sessionEnd;

    private List<IntermediateSessionDTO> subSessions;

    private RoomDTO room;

    public HolderSessionDTO(String sessionStart, String sessionEnd, List<IntermediateSessionDTO> subSessions, RoomDTO room) {
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.subSessions = subSessions;
        this.room = room;
    }

    public String getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(String sessionStart) {
        this.sessionStart = sessionStart;
    }

    public String getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(String sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public List<IntermediateSessionDTO> getSubSessions() {
        return subSessions;
    }

    public void setSubSessions(List<IntermediateSessionDTO> subSessions) {
        this.subSessions = subSessions;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }
}
