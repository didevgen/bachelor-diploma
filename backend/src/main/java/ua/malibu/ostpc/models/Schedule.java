package ua.malibu.ostpc.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="schedules")
public class Schedule extends BaseEntity {

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<WorkDay> workingDays = new ArrayList<>();

    public Schedule() {
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public List<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }
}
