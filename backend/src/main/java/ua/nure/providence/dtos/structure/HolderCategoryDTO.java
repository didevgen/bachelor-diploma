package ua.nure.providence.dtos.structure;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.business.cardholder.NamedHolderDTO;
import ua.nure.providence.dtos.business.cardholder.SimpleCardHolderDTO;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class HolderCategoryDTO extends BaseUuidDTO<StructuralCategory> {

    private String name;

    private List<NamedHolderDTO> cardHolders = new ArrayList<NamedHolderDTO>();

    @Override
    public HolderCategoryDTO convert(StructuralCategory object) {
        super.convert(object);
        setName(object.getName());
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

    public List<NamedHolderDTO> getCardHolders() {
        return cardHolders;
    }

    public void setCardHolders(List<NamedHolderDTO> cardHolders) {
        this.cardHolders = cardHolders;
    }
}
