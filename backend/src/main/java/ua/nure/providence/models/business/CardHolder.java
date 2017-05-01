package ua.nure.providence.models.business;

import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.base.BaseEntity;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "card_holders")
public class CardHolder extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "holder")
    private List<Card> cards = new ArrayList<>();

    @ManyToMany(mappedBy = "cardHolders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StructuralCategory> categories = new ArrayList<>();


    public CardHolder() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<StructuralCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<StructuralCategory> categories) {
        this.categories = categories;
    }
}
