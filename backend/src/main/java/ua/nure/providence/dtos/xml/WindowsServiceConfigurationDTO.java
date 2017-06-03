package ua.nure.providence.dtos.xml;

import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.business.DoorLocker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 07.05.2017.
 */
@XmlRootElement(name = "configuration")
public class WindowsServiceConfigurationDTO  {

    private String accountUuid;

    private String publicApiKey;

    private List<XmlDoor> doors = new ArrayList<>();

    public WindowsServiceConfigurationDTO convert(Account object, List<DoorLocker> doorLockers) {
        setAccountUuid(object.getUuid());
        setDoors(doorLockers.stream().map(doorLocker -> new XmlDoor().convert(doorLocker))
                .collect(Collectors.toList()));
        return this;
    }

    @XmlElement(name = "account")
    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }

    @XmlElement(name = "doors")
    public List<XmlDoor> getDoors() {
        return doors;
    }

    public void setDoors(List<XmlDoor> doors) {
        this.doors = doors;
    }
}
