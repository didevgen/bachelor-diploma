package ua.nure.providence.dtos.doors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class DoorUpdateDTO {

    private String roomUuid;

    private List<DoorConfigurationDTO> configurations = new ArrayList<>();

    public String getRoomUuid() {
        return roomUuid;
    }

    public void setRoomUuid(String roomUuid) {
        this.roomUuid = roomUuid;
    }

    public List<DoorConfigurationDTO> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<DoorConfigurationDTO> configurations) {
        this.configurations = configurations;
    }
}
