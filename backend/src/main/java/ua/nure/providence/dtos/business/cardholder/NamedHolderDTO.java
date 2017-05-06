package ua.nure.providence.dtos.business.cardholder;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.business.CardHolder;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class NamedHolderDTO extends BaseUuidDTO<CardHolder> {

    private String name;

    @Override
    public NamedHolderDTO convert(CardHolder object) {
        super.convert(object);
        setName(object.getFullName());
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
