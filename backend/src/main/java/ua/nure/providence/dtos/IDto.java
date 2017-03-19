package ua.nure.providence.dtos;

public interface IDto<T> {

    IDto<T> convert(T object);

}
