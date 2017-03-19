package ua.nure.providence.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.nure.providence.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clubs")
public class Club extends BaseEntity {
    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<WorkDay> workingDays = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = {CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Holiday> holidays = new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY, mappedBy="club")
    private ClubPreference preference;

    public Club() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public ClubPreference getPreference() {
        return preference;
    }

    public void setPreference(ClubPreference preference) {
        this.preference = preference;
    }
}
