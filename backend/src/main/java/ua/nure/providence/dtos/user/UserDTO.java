package ua.nure.providence.dtos.user;

import org.joda.time.DateTime;
import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.authentication.User;

/**
 * Created by Providence Team on 19.03.2017.
 */
public class UserDTO extends BaseUuidDTO<User>{

    private String name;

    private String surname;

    private String middleName;

    private DateTime birthday;

    private String address;

    private String email;

    private String phoneNumber;

    @Override
    public UserDTO convert(User object) {
        super.convert(object);
        this.name = object.getName();
        this.surname = object.getSurname();
        this.middleName = object.getMiddleName();
        this.birthday = object.getBirthday();
        this.address = object.getAddress();
        this.email = object.getEmail();
        this.phoneNumber = object.getPhoneNumber();
        return this;
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
}
