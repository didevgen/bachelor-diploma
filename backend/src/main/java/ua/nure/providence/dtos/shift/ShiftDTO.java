package ua.nure.providence.dtos.shift;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.Shift;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Игорь on 01.02.2017.
 */

public class ShiftDTO extends BaseUuidDTO {
    private String workingDay;
    private Integer shiftOrdinal;
    private List<String> workersOnShift;

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public Integer getShiftOrdinal() {
        return shiftOrdinal;
    }

    public void setShiftOrdinal(Integer shiftOrdinal) {
        this.shiftOrdinal = shiftOrdinal;
    }

    public List<String> getWorkersOnShift() {
        return workersOnShift;
    }

    public void setWorkersOnShift(List<String> workersOnShift) {
        this.workersOnShift = workersOnShift;
    }

    public ShiftDTO convert(Shift object) {
        this.setWorkingDay(object.getWorkingDay().getUuid());
        this.setShiftOrdinal(object.getShiftOrdinal());
        this.setWorkersOnShift(object.getWorkersOnShift()
                .stream().map(worker -> worker.getUuid()).collect(Collectors.toList()));
        return this;
    }
}
