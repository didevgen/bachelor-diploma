package ua.nure.providence.stub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
public class Faculty {
    private int id;

    private String short_name;

    private String full_name;

    private List<Department> departments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
