package ua.nure.providence.models.business;

import ua.nure.providence.daos.DoorDAO;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Entity
@Table(name = "door")
public class DoorLocker extends BaseEntity {


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Room room;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "locker")
    private List<DoorConfiguration> configuration = new ArrayList<>();

    public DoorLocker() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<DoorConfiguration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<DoorConfiguration> configuration) {
        this.configuration = configuration;
    }

    @PreRemove
    private void preRemove() {
        DoorDAO dao = new DoorDAO();
        dao.deleteDoorConfigurations(this);
    }
}
