package ua.nure.providence.dtos.xml;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.business.DoorConfiguration;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Providence Team on 07.05.2017.
 */
public class XmlDoorConfiguration extends BaseUuidDTO<DoorConfiguration> {

    private String protocol;

    private String port;

    private String deviceId;

    private String baudrate;

    private String ipAddress;

    private String timeout;

    private String passwd;

    @Override
    public XmlDoorConfiguration convert(DoorConfiguration object) {
        super.convert(object);
        setBaudrate(object.getBaudrate());
        setDeviceId(object.getDeviceId());
        setIpAddress(object.getIpAddress());
        setPasswd(object.getPasswd());
        setPort(object.getPort());
        setTimeout(object.getTimeout());
        setProtocol(object.getProtocol());
        return this;
    }

    @XmlElement
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @XmlElement
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @XmlElement
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @XmlElement
    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    @XmlElement
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @XmlElement
    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    @XmlElement
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
