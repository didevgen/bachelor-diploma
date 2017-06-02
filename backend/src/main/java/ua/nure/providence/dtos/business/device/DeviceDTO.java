package ua.nure.providence.dtos.business.device;

import ua.nure.providence.enums.DeviceType;

/**
 * Created by User on 02.06.2017.
 */
public class DeviceDTO {

    private String subscriptionKey;

    private DeviceType deviceType;

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
