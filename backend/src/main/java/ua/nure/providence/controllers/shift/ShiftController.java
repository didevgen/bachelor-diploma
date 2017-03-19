package ua.nure.providence.controllers.shift;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.ShiftDAO;
import ua.nure.providence.dtos.shift.ShiftDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.Shift;
import ua.nure.providence.models.User;
import ua.nure.providence.models.WorkDay;

/**
 * Created by Игорь on 31.01.2017.
 */
@RestController
public class ShiftController extends BaseController
{
    @Autowired
    private ShiftDAO shiftDAO;

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ShiftDTO> getShift(@PathVariable (name = "uuid") String uuid)
    {
        Shift shift = shiftDAO.get(uuid);
        if(shift != null){
            return new ResponseEntity<>(new ShiftDTO().convert(shift), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteShift(@PathVariable (name = "uuid", required = true) String uuid)
    {
        Shift shift = shiftDAO.get(uuid);
        if(shift != null){
            shiftDAO.delete(shift);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ShiftDTO> updateShift (@PathVariable (name = "uuid") String uuid,
                                       @RequestBody ShiftDTO shiftDTO)
    {
        Shift shift = shiftDAO.get(uuid);
        if(shift != null) {
            shift.setShiftOrdinal(shiftDTO.getShiftOrdinal());
            shift.setWorkersOnShift(new JPAQuery<User>(entityManager)
                    .from(QUser.user)
                    .where(QUser.user.uuid.in(shiftDTO.getWorkersOnShift())).fetch());
            shift.setWorkingDay(new JPAQuery<WorkDay>(entityManager)
                    .from(QWorkDay.workDay)
                    .where(QWorkDay.workDay.uuid.eq(shiftDTO.getWorkingDay())).fetchOne());
            shiftDAO.update(shift);
            return new ResponseEntity<>(new ShiftDTO().convert(shift), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }

    }

    @RequestMapping(value = "/shift", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ShiftDTO> createShift (@RequestBody ShiftDTO shiftDTO)
    {
            Shift shift = new Shift();
            shift.setShiftOrdinal(shiftDTO.getShiftOrdinal());
            shift.setWorkersOnShift(new JPAQuery<User>(entityManager)
                    .from(QUser.user)
                    .where(QUser.user.uuid.in(shiftDTO.getWorkersOnShift())).fetch());
            shift.setWorkingDay(new JPAQuery<WorkDay>(entityManager)
                    .from(QWorkDay.workDay)
                    .where(QWorkDay.workDay.uuid.eq(shiftDTO.getWorkingDay())).fetchOne());
            shiftDAO.insert(shift);
            return new ResponseEntity<>(shiftDTO.convert(shift), HttpStatus.OK);
    }
}
