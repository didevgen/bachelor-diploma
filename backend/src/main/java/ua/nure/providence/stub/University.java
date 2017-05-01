package ua.nure.providence.stub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
public class University {

    private String short_name;

    private String full_name;

    private List<Faculty> faculties = new ArrayList<>();

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}
