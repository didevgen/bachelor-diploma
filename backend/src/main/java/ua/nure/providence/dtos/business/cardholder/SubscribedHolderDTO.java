package ua.nure.providence.dtos.business.cardholder;

import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;

/**
 * Created by Providence Team on 23.05.2017.
 */
public class SubscribedHolderDTO extends NamedHolderDTO {

    private boolean isSubscribed;

    public SubscribedHolderDTO convert(CardHolder object, User user) {
        super.convert(object);
        setSubscribed(object.getSubscriptions().stream().anyMatch(item -> item.getUser().getUuid()
                .equals(user.getUuid())));
        return this;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
