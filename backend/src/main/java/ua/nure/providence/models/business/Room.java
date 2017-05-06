package ua.nure.providence.models.business;

import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

    @Column
    private String name;

    @Column
    private String building;

    @Column
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private List<DoorLocker> doors = new ArrayList<>();

    public Room() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<DoorLocker> getDoors() {
        return doors;
    }

    public void setDoors(List<DoorLocker> doors) {
        this.doors = doors;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @PreRemove
    private void preRemove() {
        this.getDoors().forEach(doorLocker -> doorLocker.setRoom(null));
    }
}
