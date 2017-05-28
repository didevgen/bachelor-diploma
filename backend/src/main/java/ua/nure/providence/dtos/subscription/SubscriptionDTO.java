package ua.nure.providence.dtos.subscription;

/**
 * Created by Providence Team on 28.05.2017.
 */
public class SubscriptionDTO {

    private String holderUuid;

    private String oneSignalId;

    public String getHolderUuid() {
        return holderUuid;
    }

    public void setHolderUuid(String holderUuid) {
        this.holderUuid = holderUuid;
    }

    public String getOneSignalId() {
        return oneSignalId;
    }

    public void setOneSignalId(String oneSignalId) {
        this.oneSignalId = oneSignalId;
    }
}
