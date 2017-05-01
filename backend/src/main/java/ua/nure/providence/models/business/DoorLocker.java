package ua.nure.providence.models.business;

import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "door")
public class DoorLocker extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Room room;

    @OneToOne(fetch = FetchType.EAGER)
    private DoorConfiguration configuration;

    public DoorLocker() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public DoorConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(DoorConfiguration configuration) {
        this.configuration = configuration;
    }
}
