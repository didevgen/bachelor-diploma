package ua.nure.providence.dtos.business.room;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.dtos.IPostDTO;
import ua.nure.providence.models.business.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 02.05.2017.
 */
public class RoomUpdateDTO extends BaseUuidDTO<Room> implements IPostDTO<Room> {

    private String name;

    private String building;

    private int floor;

    private List<String> lockers = new ArrayList<>();

    @Override
    public RoomUpdateDTO convert(Room object) {
        super.convert(object);
        return this;
    }

    @Override
    public void fromDTO(Room room) {
        room.setFloor(this.floor);
        room.setName(this.name);
        room.setBuilding(this.building);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getLockers() {
        return lockers;
    }

    public void setLockers(List<String> lockers) {
        this.lockers = lockers;
    }
}
