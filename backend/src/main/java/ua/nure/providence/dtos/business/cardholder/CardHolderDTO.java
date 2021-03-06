package ua.nure.providence.dtos.business.cardholder;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.business.structure.SimpleCategoryDTO;
import ua.nure.providence.models.base.UUIDEntity;
import ua.nure.providence.models.business.Card;
import ua.nure.providence.models.business.CardHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 02.05.2017.
 */
public class CardHolderDTO extends BaseUuidDTO<CardHolder> {

    private String fullName;

    private List<String> cardNumbers = new ArrayList<>();

    private List<SimpleCategoryDTO> categories = new ArrayList<>();

    @Override
    public CardHolderDTO convert(CardHolder object) {
        super.convert(object);
        this.fullName = object.getFullName();
        setCardNumbers(object.getCards().stream().map(Card::getCardNumber).collect(Collectors.toList()));
        setCategories(object.getCategories().stream().map(item -> new SimpleCategoryDTO().convert(item)).collect(Collectors.toList()));
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<String> cardNumber) {
        this.cardNumbers = cardNumber;
    }

    public List<SimpleCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<SimpleCategoryDTO> categories) {
        this.categories = categories;
    }
}
