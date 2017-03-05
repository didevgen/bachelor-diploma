package ua.malibu.ostpc.controllers.workDay;

import com.querydsl.jpa.impl.JPAQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.daos.WorkDayDAO;
import ua.malibu.ostpc.dtos.workDay.WorkDayDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;

/**
 * Created by Игорь on 04.02.2017.
 */
public class WorkDayController extends BaseController {
    @Autowired
    private WorkDayDAO workDayDAO;

    @RequestMapping(value = "/workday/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<WorkDayDTO> getWorkDay (@PathVariable (name = "uuid", required = true) String uuid){
        WorkDay workDay = workDayDAO.get(uuid);
        if(workDay != null){
            return new ResponseEntity<>(new WorkDayDTO().convert(workDay), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/workday/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteWorkDay (@PathVariable (name = "uuid") String uuid){
        WorkDay workDay = workDayDAO.get(uuid);
        if (workDay != null){
            workDayDAO.delete(workDay);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/workday/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<WorkDayDTO> updateWorkDay (@PathVariable (name = "uuid") String uuid,
                                                     @RequestBody WorkDayDTO workDayDTO) {
        WorkDay workDay = workDayDAO.get(uuid);
        if (workDay != null){
            workDay.setClub(new JPAQuery<Club>(entityManager)
                    .from(QClub.club)
                    .where(QClub.club.uuid.eq(workDayDTO.getClub())).fetchOne());
            workDay.setMaxEmployeesCount(workDayDTO.getMaxEmployeesCount());
            workDay.setDate(DateTime.parse(workDayDTO.getDate()));
            workDay.setSchedule(new JPAQuery<Schedule>(entityManager)
                    .from(QSchedule.schedule)
                    .where(QSchedule.schedule.uuid.eq(workDayDTO.getSchedule())).fetchOne());
            workDay.setShifts(new JPAQuery<Shift>(entityManager)
                    .from(QShift.shift)
                    .where(QShift.shift.uuid.in(workDayDTO.getShifts())).fetch());
            workDayDAO.update(workDay);
            return new ResponseEntity<WorkDayDTO>(new WorkDayDTO().convert(workDay), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/workday", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<WorkDayDTO> createWorkDay (@RequestBody WorkDayDTO workDayDTO){
        WorkDay workDay = new WorkDay();
        workDay.setClub(new JPAQuery<Club>(entityManager)
                .from(QClub.club)
                .where(QClub.club.uuid.eq(workDayDTO.getClub())).fetchOne());
        workDay.setMaxEmployeesCount(workDayDTO.getMaxEmployeesCount());
        workDay.setDate(DateTime.parse(workDayDTO.getDate()));
        workDay.setSchedule(new JPAQuery<Schedule>(entityManager)
                .from(QSchedule.schedule)
                .where(QSchedule.schedule.uuid.eq(workDayDTO.getSchedule())).fetchOne());
        workDay.setShifts(new JPAQuery<Shift>(entityManager)
                .from(QShift.shift)
                .where(QShift.shift.uuid.in(workDayDTO.getShifts())).fetch());
        workDayDAO.insert(workDay);
        return new ResponseEntity<WorkDayDTO>(new WorkDayDTO().convert(workDay), HttpStatus.OK);
    }
}
