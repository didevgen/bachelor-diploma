package ua.nure.providence.dtos.business.room;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.business.Room;

/**
 * Created by Providence Team on 02.05.2017.
 */
public class RoomDTO extends BaseUuidDTO<Room> {

    private String name;

    private String building;

    private Integer online;

    private int floor;

    @Override
    public RoomDTO convert(Room object) {
        super.convert(object);
        name = object.getName();
        building = object.getBuilding();
        floor = object.getFloor();
        return this;
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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }
}
