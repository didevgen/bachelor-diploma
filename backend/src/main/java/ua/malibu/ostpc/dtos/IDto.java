package ua.malibu.ostpc.dtos;

import ua.malibu.ostpc.models.base.UUIDEntity;

public interface IDto<T> {

    IDto<T> convert(T object);

}
