package ua.nure.providence.dtos;

import ua.nure.providence.models.base.UUIDEntity;

import javax.xml.bind.annotation.XmlElement;

public class BaseUuidDTO<T extends UUIDEntity> implements IDto<T>{

    private String uuid;

    @Override
    public BaseUuidDTO convert(T object) {
        this.setUuid(object.getUuid());
        return this;
    }

    @XmlElement
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
