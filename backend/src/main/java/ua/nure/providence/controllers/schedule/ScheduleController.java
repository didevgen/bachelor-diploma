package ua.nure.providence.controllers.schedule;

import com.querydsl.jpa.impl.JPAQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.ScheduleDAO;
import ua.nure.providence.dtos.schedule.ScheduleDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.malibu.ostpc.models.QWorkDay;
import ua.nure.providence.models.Schedule;
import ua.nure.providence.models.WorkDay;

/**
 * Created by Игорь on 09.02.2017.
 */
public class ScheduleController extends BaseController {
    @Autowired
    private ScheduleDAO scheduleDAO;

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> getSchedule (@PathVariable (name = "uuid") String uuid){
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            return new ResponseEntity<ScheduleDTO>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteSchedule (@PathVariable (name = "uuid") String uuid){
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            scheduleDAO.delete(schedule);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> updateSchedule (@PathVariable (name = "uuid") String uuid,
                                                       @RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            schedule.setStartDate(DateTime.parse(scheduleDTO.getStartDate()));
            schedule.setEndDate(DateTime.parse(scheduleDTO.getEndDate()));
            schedule.setStatus(scheduleDTO.getStatus());
            schedule.setWorkingDays(new JPAQuery<WorkDay>(entityManager)
                    .from(QWorkDay.workDay)
                    .where(QWorkDay.workDay.uuid.in(scheduleDTO.getWorkingDays())).fetch());
            scheduleDAO.update(schedule);
            return new ResponseEntity<ScheduleDTO>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/schedule/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> createSchedule (@RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setStartDate(DateTime.parse(scheduleDTO.getStartDate()));
        schedule.setEndDate(DateTime.parse(scheduleDTO.getEndDate()));
        schedule.setStatus(scheduleDTO.getStatus());
        schedule.setWorkingDays(new JPAQuery<WorkDay>(entityManager)
                .from(QWorkDay.workDay)
                .where(QWorkDay.workDay.uuid.in(scheduleDTO.getWorkingDays())).fetch());
        scheduleDAO.insert(schedule);
        return new ResponseEntity<ScheduleDTO>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
    }
}
