package ua.malibu.ostpc.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_day")
public class WorkDay extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Schedule schedule;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @OneToMany(mappedBy = "workingDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Shift> shifts = new ArrayList<>();

    @Column(name = "max_employees")
    private Integer maxEmployeesCount;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Integer getMaxEmployeesCount() {
        return maxEmployeesCount;
    }

    public void setMaxEmployeesCount(Integer maxEmployeesCount) {
        this.maxEmployeesCount = maxEmployeesCount;
    }
}
