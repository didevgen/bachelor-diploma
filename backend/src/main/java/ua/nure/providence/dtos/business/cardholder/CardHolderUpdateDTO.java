package ua.nure.providence.dtos.business.cardholder;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.IPostDTO;
import ua.nure.providence.models.business.CardHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 02.05.2017.
 */
public class CardHolderUpdateDTO extends BaseUuidDTO<CardHolder> implements IPostDTO<CardHolder>{

    private String fullName;

    private List<SimpleCardDTO> cards = new ArrayList<>();

    private List<String> categories = new ArrayList<>();

    @Override
    public void fromDTO(CardHolder object) {
        object.setFullName(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<SimpleCardDTO> getCards() {
        return cards;
    }

    public void setCards(List<SimpleCardDTO> cards) {
        this.cards = cards;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
