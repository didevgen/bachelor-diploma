package ua.nure.providence.dtos.structure;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.hierarchy.StructuralCategory;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class SimpleCategoryDTO extends BaseUuidDTO<StructuralCategory> {

    private String name;

    @Override
    public SimpleCategoryDTO convert(StructuralCategory object) {
        super.convert(object);
        setName(object.getName());
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
