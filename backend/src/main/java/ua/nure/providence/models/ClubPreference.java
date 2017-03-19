package ua.nure.providence.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "club_prefs")
public class ClubPreference extends BaseEntity{

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Club club;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "club_preferenced_users",
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    private List<User> users = new ArrayList<User>();

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
