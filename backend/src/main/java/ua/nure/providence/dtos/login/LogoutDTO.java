package ua.nure.providence.dtos.login;

import ua.nure.providence.enums.DeviceType;

/**
 * Created by User on 02.06.2017.
 */
public class LogoutDTO {

    private String subscriptionKey;

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }
}
