package ua.nure.providence.models.business;

import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
public class Card extends BaseEntity {

    @Column(name = "number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CardHolder holder;

    public Card() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardHolder getHolder() {
        return holder;
    }

    public void setHolder(CardHolder holder) {
        this.holder = holder;
    }
}
