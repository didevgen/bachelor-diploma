package ua.nure.providence.dtos.history.session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 02.06.2017.
 */
public class SessionDetailDTO {

    private String fullName;

    private List<HolderSessionDTO> sessions = new ArrayList<>();

    public SessionDetailDTO(String fullName, List<HolderSessionDTO> sessions) {
        this.fullName = fullName;
        this.sessions = sessions;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<HolderSessionDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<HolderSessionDTO> sessions) {
        this.sessions = sessions;
    }
}
