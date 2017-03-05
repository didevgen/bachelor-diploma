package ua.malibu.ostpc.models;

import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shifts")
public class Shift extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private WorkDay workingDay;

    @Column(name = "shift_ordinal")
    private Integer shiftOrdinal;

    @ManyToMany(mappedBy="shifts")
    private List<User> workersOnShift = new ArrayList<>();

    public WorkDay getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(WorkDay workingDay) {
        this.workingDay = workingDay;
    }

    public Integer getShiftOrdinal() {
        return shiftOrdinal;
    }

    public void setShiftOrdinal(Integer shiftOrdinal) {
        this.shiftOrdinal = shiftOrdinal;
    }

    public List<User> getWorkersOnShift() {
        return workersOnShift;
    }

    public void setWorkersOnShift(List<User> workersOnShift) {
        this.workersOnShift = workersOnShift;
    }
}
