package ua.nure.providence.dtos.user;


import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.enums.UserRole;
import ua.nure.providence.models.User;

public class SimpleUserDTO extends BaseUuidDTO<User> {

    public SimpleUserDTO() {}

    private String name;

    private String surname;

    private UserRole role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public SimpleUserDTO convert(User object){
        this.setUuid(object.getUuid());
        this.setName(object.getName());
        this.setSurname(object.getSurname());
        this.setRole(object.getRole());
        return this;
    }

}
