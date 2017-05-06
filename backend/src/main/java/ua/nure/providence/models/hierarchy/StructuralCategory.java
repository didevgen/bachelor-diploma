package ua.nure.providence.models.hierarchy;

import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.base.BaseEntity;
import ua.nure.providence.models.business.CardHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "categories")
public class StructuralCategory extends BaseEntity {

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="category_cardholder",
            joinColumns=@JoinColumn(name="category_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="cardholder_id", referencedColumnName="id"))
    private List<CardHolder> cardHolders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private StructuralCategory parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<StructuralCategory> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public StructuralCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CardHolder> getCardHolders() {
        return cardHolders;
    }

    public void setCardHolders(List<CardHolder> cardHolders) {
        this.cardHolders = cardHolders;
    }

    public StructuralCategory getParent() {
        return parent;
    }

    public void setParent(StructuralCategory parent) {
        this.parent = parent;
    }

    public List<StructuralCategory> getChildren() {
        return children;
    }

    public void setChildren(List<StructuralCategory> children) {
        this.children = children;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @PreRemove
    private void preRemove() {
        this.getCardHolders().forEach(cardHolder -> cardHolder.setCategories(cardHolder
                .getCategories().stream().filter(category -> !this.getUuid().equals(category.getUuid()))
                .collect(Collectors.toList())));
        this.getChildren().forEach(child -> child.setParent(this.getParent()));
    }
}
