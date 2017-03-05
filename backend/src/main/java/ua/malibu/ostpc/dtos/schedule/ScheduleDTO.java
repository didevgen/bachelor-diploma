package ua.malibu.ostpc.dtos.schedule;

import ua.malibu.ostpc.dtos.BaseUuidDTO;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.models.Schedule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Игорь on 09.02.2017.
 */
public class ScheduleDTO extends BaseUuidDTO {
    private String startDate;
    private String endDate;
    private ScheduleStatus status;
    private List<String> workingDays;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public ScheduleDTO convert(Schedule object) {
        this.setStartDate(object.getStartDate().toLocalDate().toString());
        this.setEndDate(object.getEndDate().toLocalDate().toString());
        this.setStatus(object.getStatus());
        this.setWorkingDays(object.getWorkingDays().stream().map(schedule -> schedule.getUuid()).collect(Collectors.toList()));
        return this;
    }
}
