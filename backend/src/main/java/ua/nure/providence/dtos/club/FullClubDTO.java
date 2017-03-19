package ua.nure.providence.dtos.club;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.Club;
import ua.nure.providence.models.ClubPreference;
import ua.nure.providence.models.Holiday;
import ua.nure.providence.models.WorkDay;

import java.util.List;

/**
 * Created by simar on 2/9/2017.
 */
public class FullClubDTO extends BaseUuidDTO<Club> {

    public FullClubDTO() {
    }

    private String title;
    private List<WorkDay> workingDays;
    private List<Holiday> holidays;
    private ClubPreference preference;

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

    @Override
    public FullClubDTO convert(Club object) {
        this.setUuid(object.getUuid());
        this.setTitle(object.getTitle());
        this.setWorkingDays(object.getWorkingDays());
        this.setHolidays(object.getHolidays());
        this.setPreference(object.getPreference());
        return this;
    }
}

