package ua.nure.providence.dtos.business.cardholder;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.business.Card;
import ua.nure.providence.models.business.CardHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class SimpleCardHolderDTO extends BaseUuidDTO<CardHolder> {

    private String name;

    private List<String> cardNumbers = new ArrayList<>();

    @Override
    public SimpleCardHolderDTO convert(CardHolder object) {
        super.convert(object);
        setName(object.getFullName());
        setCardNumbers(object.getCards().stream().map(Card::getCardNumber).collect(Collectors.toList()));
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<String> cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
}
