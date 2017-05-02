package ua.nure.providence.dtos;

import ua.nure.providence.models.base.UUIDEntity;

/**
 * Created by Providence Team on 02.05.2017.
 */
public interface IPostDTO<T extends UUIDEntity> {

    void fromDTO(T object);
}
