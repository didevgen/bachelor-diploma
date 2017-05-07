package ua.nure.providence.dtos.doors;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.IPostDTO;
import ua.nure.providence.models.business.DoorConfiguration;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class DoorConfigurationDTO extends BaseUuidDTO<DoorConfiguration> implements IPostDTO<DoorConfiguration> {

    private String protocol;

    private String port;

    private String deviceId;

    private String baudrate;

    private String ipAddress;

    private String timeout;

    private String passwd;

    @Override
    public void fromDTO(DoorConfiguration object) {
        object.setBaudrate(this.getBaudrate());
        object.setDeviceId(this.getDeviceId());
        object.setIpAddress(this.getIpAddress());
        object.setPasswd(this.getPasswd());
        object.setPort(this.getPort());
        object.setProtocol(this.getProtocol());
        object.setTimeout(this.getTimeout());
    }

    @Override
    public DoorConfigurationDTO convert(DoorConfiguration object) {
        super.convert(object);
        setBaudrate(object.getBaudrate());
        setDeviceId(object.getDeviceId());
        setIpAddress(object.getIpAddress());
        setPasswd(object.getPasswd());
        setPort(object.getPort());
        setProtocol(object.getProtocol());
        setTimeout(object.getTimeout());
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
