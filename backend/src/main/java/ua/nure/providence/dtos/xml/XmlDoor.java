package ua.nure.providence.dtos.xml;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.business.DoorLocker;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class XmlDoor extends BaseUuidDTO<DoorLocker>{

    private String roomUuid;

    private List<XmlDoorConfiguration> doorConfigurations = new ArrayList<>();

    @Override
    public XmlDoor convert(DoorLocker object) {
        super.convert(object);
        setDoorConfigurations(object.getConfiguration().stream()
                .map(doorConfiguration -> new XmlDoorConfiguration().convert(doorConfiguration))
                .collect(Collectors.toList()));
        return this;
    }

    @XmlElement(name = "roomId")
    public String getRoomUuid() {
        return roomUuid;
    }

    public void setRoomUuid(String roomUuid) {
        this.roomUuid = roomUuid;
    }

    @XmlElement(name = "roomConfiguration")
    public List<XmlDoorConfiguration> getDoorConfigurations() {
        return doorConfigurations;
    }

    public void setDoorConfigurations(List<XmlDoorConfiguration> doorConfigurations) {
        this.doorConfigurations = doorConfigurations;
    }
}
