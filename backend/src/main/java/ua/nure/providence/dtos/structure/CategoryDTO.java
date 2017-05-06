package ua.nure.providence.dtos.structure;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class CategoryDTO extends BaseUuidDTO<StructuralCategory> {

    private String name;

    private NamedCategoryDTO parent;

    private List<NamedCategoryDTO> children = new ArrayList<>();

    @Override
    public CategoryDTO convert(StructuralCategory object) {
        super.convert(object);
        setName(object.getName());
        if (object.getParent() != null) {
            setParent(new NamedCategoryDTO(object.getParent().getUuid(), object.getParent().getName()));
        }
        setChildren(object.getChildren().stream().map(child ->
                new NamedCategoryDTO(child.getUuid(), child.getName()))
                .collect(Collectors.toList()));
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NamedCategoryDTO getParent() {
        return parent;
    }

    public void setParent(NamedCategoryDTO parent) {
        this.parent = parent;
    }

    public List<NamedCategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<NamedCategoryDTO> children) {
        this.children = children;
    }
}
