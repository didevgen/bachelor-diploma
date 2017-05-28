package ua.nure.providence.models.subscription;

import ua.nure.providence.enums.SubscriptionType;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.base.BaseEntity;
import ua.nure.providence.models.business.CardHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 28.05.2017.
 */
@Entity
public class Subscription extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    private String subscriptionKey;

    @ManyToMany
    @JoinTable(
            name="holder_subscriptions",
            joinColumns = {
                    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "cardholder_id", referencedColumnName = "id")
            })
    private List<CardHolder> subsribedHolders = new ArrayList<>();

    public Subscription() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public List<CardHolder> getSubsribedHolders() {
        return subsribedHolders;
    }

    public void setSubsribedHolders(List<CardHolder> subsribedHolders) {
        this.subsribedHolders = subsribedHolders;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }
}
