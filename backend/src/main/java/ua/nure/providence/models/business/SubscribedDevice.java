package ua.nure.providence.models.business;

import ua.nure.providence.enums.DeviceType;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "device")
public class SubscribedDevice extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column
    private String subscriptionKey;

    public SubscribedDevice() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }
}
