package ua.nure.providence.dtos.structure;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.business.cardholder.NamedHolderDTO;
import ua.nure.providence.dtos.business.cardholder.SimpleCardHolderDTO;
import ua.nure.providence.models.base.UUIDEntity;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class DetailCategoryDTO extends BaseUuidDTO<StructuralCategory> {

    private String name;

    private SimpleCategoryDTO parent;

    private List<SimpleCategoryDTO> children = new ArrayList<>();

    private List<NamedHolderDTO> cardHolders = new ArrayList<NamedHolderDTO>();

    @Override
    public DetailCategoryDTO convert(StructuralCategory object) {
        super.convert(object);
        setName(object.getName());

        if (object.getParent() != null) {
            setParent(new SimpleCategoryDTO().convert(object.getParent()));
        }

        setChildren(object.getChildren().stream()
                .map(child -> new SimpleCategoryDTO().convert(child)).collect(Collectors.toList()));

        setCardHolders(object.getCardHolders().stream()
                .map(cardHolder -> new NamedHolderDTO().convert(cardHolder))
                .collect(Collectors.toList()));
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleCategoryDTO getParent() {
        return parent;
    }

    public void setParent(SimpleCategoryDTO parent) {
        this.parent = parent;
    }

    public List<SimpleCategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<SimpleCategoryDTO> children) {
        this.children = children;
    }

    public List<NamedHolderDTO> getCardHolders() {
        return cardHolders;
    }

    public void setCardHolders(List<NamedHolderDTO> cardHolders) {
        this.cardHolders = cardHolders;
    }
}
