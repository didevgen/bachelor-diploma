package ua.nure.providence.dtos.business.structure;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.base.UUIDEntity;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 02.05.2017.
 */
public class CategoryDTO extends BaseUuidDTO<StructuralCategory> {

    private String parent;

    private List<String> children = new ArrayList<>();

    private String name;

    @Override
    public CategoryDTO convert(StructuralCategory object) {
        super.convert(object);
        this.parent = object.getParent().getUuid();
        this.children = object.getChildren().stream().map(UUIDEntity::getUuid).collect(Collectors.toList());
        this.name = object.getName();
        return this;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
