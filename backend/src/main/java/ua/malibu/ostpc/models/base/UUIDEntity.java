package ua.malibu.ostpc.models.base;

import ua.malibu.ostpc.models.annotations.CloneIgnoreField;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDEntity  {

    @CloneIgnoreField
    private static final long serialVersionUID = -8466770986304062624L;

    @Column(nullable = false, unique = true)
    @CloneIgnoreField
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
