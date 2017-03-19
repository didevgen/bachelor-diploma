package ua.nure.providence.dtos.workDay;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.WorkDay;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Игорь on 04.02.2017.
 */
public class WorkDayDTO extends BaseUuidDTO {
    private String club;
    private String schedule;
    private String date;
    private List<String> shifts;
    private Integer maxEmployeesCount;


    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getShifts() {
        return shifts;
    }

    public void setShifts(List<String> shifts) {
        this.shifts = shifts;
    }

    public Integer getMaxEmployeesCount() {
        return maxEmployeesCount;
    }

    public void setMaxEmployeesCount(Integer maxEmployeesCount) {
        this.maxEmployeesCount = maxEmployeesCount;
    }

    public WorkDayDTO convert(WorkDay object) {
        this.setClub(object.getClub().getUuid());
        this.setDate(object.getDate().toLocalDate().toString());
        this.setMaxEmployeesCount(object.getMaxEmployeesCount());
        this.setSchedule(object.getSchedule().getUuid());
        this.setShifts(object.getShifts()
                .stream().map(shift -> shift.getUuid()).collect(Collectors.toList()));
        return this;
    }
}
