package ua.malibu.ostpc.dtos;

import ua.malibu.ostpc.models.base.UUIDEntity;

public class BaseUuidDTO<T extends UUIDEntity> implements IDto<T>{

    private String uuid;

    @Override
    public BaseUuidDTO convert(T object) {
        this.setUuid(object.getUuid());
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
