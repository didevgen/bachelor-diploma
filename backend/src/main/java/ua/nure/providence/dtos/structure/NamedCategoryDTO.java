package ua.nure.providence.dtos.structure;

import ua.nure.providence.dtos.BaseUuidDTO;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class NamedCategoryDTO {

    private String uuid;

    private String name;

    public NamedCategoryDTO(String uuid, String name) {
        setName(name);
        setUuid(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
