package ua.malibu.ostpc.dtos.user;

import org.joda.time.DateTime;
import ua.malibu.ostpc.dtos.BaseUuidDTO;
import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.Shift;
import ua.malibu.ostpc.models.User;

import java.util.List;

public class FullUserDTO extends BaseUuidDTO<User> {

    private String name;
    private String surname;
    private String middleName;
    private DateTime birthday;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private List<Shift> shifts;

    public FullUserDTO() {
    }

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

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public DateTime getBirthday() {
        return birthday;
    }
    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public FullUserDTO convert(User object){
        this.setUuid(object.getUuid());
        this.setName(object.getName());
        this.setSurname(object.getSurname());
        this.setMiddleName(object.getMiddleName());
        this.setBirthday(object.getBirthday());
        this.setAddress(object.getAddress());
        this.setEmail(object.getEmail());
        this.setPhoneNumber(object.getPhoneNumber());
        this.setRole(object.getRole());
        this.setShifts(object.getShifts());
        this.setPassword(object.getPassword());
        return this;
    }

}
