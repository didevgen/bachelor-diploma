package ua.nure.providence.dtos.doors;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.business.room.RoomDTO;
import ua.nure.providence.models.business.DoorLocker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class DoorDTO extends BaseUuidDTO<DoorLocker> {

    private RoomDTO room;

    private List<DoorConfigurationDTO> configurations = new ArrayList<>();

    @Override
    public DoorDTO convert(DoorLocker object) {
        super.convert(object);
        setRoom(new RoomDTO().convert(object.getRoom()));
        setConfigurations(object.getConfiguration().stream()
                .map(doorConfiguration -> new DoorConfigurationDTO().convert(doorConfiguration))
                .collect(Collectors.toList()));
        return this;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public List<DoorConfigurationDTO> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<DoorConfigurationDTO> configurations) {
        this.configurations = configurations;
    }
}
