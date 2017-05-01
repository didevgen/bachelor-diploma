package ua.nure.providence.models.authentication;

import ua.nure.providence.enums.AccountType;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    private List<User> users = new ArrayList<>();

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
