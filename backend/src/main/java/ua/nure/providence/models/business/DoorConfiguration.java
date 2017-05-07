package ua.nure.providence.models.business;

import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "door_config")
public class DoorConfiguration extends BaseEntity{

    @Column
    private String protocol;

    @Column
    private String port;

    @Column
    private String deviceId;

    @Column
    private String baudrate;

    @Column
    private String ipAddress;

    @Column
    private String timeout;

    @Column
    private String passwd;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DoorLocker locker;

    public DoorConfiguration() {
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

    @XmlTransient
    public DoorLocker getLocker() {
        return locker;
    }

    public void setLocker(DoorLocker locker) {
        this.locker = locker;
    }
}
