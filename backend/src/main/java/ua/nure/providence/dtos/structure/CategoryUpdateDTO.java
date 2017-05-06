package ua.nure.providence.dtos.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 06.05.2017.
 */
public class CategoryUpdateDTO  {

    private String name;

    private String parent;

    private List<String> children;

    private List<String> cardHolders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<String> getCardHolders() {
        return cardHolders;
    }

    public void setCardHolders(List<String> cardHolders) {
        this.cardHolders = cardHolders;
    }
}
