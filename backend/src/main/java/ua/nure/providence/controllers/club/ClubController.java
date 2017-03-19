package ua.nure.providence.controllers.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.ClubDAO;
import ua.nure.providence.dtos.club.SimpleClubDTO;
import ua.nure.providence.dtos.club.FullClubDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.Club;


@RestController
public class ClubController extends BaseController {

    @Autowired
    private ClubDAO clubDAO;

    @RequestMapping(value = "/club/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SimpleClubDTO> readClub(@PathVariable(name = "uuid", required = true) String uuid) {
        return new ResponseEntity<>(new SimpleClubDTO().convert(clubDAO.get(uuid)), HttpStatus.OK);
    }

    @RequestMapping(value = "/Club/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteClub(@PathVariable(name = "uuid", required = true) String uuid) {
        Club club = clubDAO.get(uuid);
        if (club != null) {
            new ClubDAO().delete(club);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/Club/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullClubDTO> updateClub(@PathVariable(name = "uuid", required = true) String uuid,
                                                  @RequestBody FullClubDTO fullClub) {
        Club club = clubDAO.get(uuid);
        if (club != null) {
            club.setTitle(fullClub.getTitle());
            club.setWorkingDays(fullClub.getWorkingDays());
            club.setHolidays(fullClub.getHolidays());
            club.setPreference(fullClub.getPreference());

            clubDAO.update(club);

            return new ResponseEntity<>(new FullClubDTO().convert(club), HttpStatus.OK);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/Club", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FullClubDTO> createClub(@RequestBody FullClubDTO fullClub) {
        Club club = new Club();

        club.setTitle(fullClub.getTitle());
        club.setWorkingDays(fullClub.getWorkingDays());
        club.setHolidays(fullClub.getHolidays());
        club.setPreference(fullClub.getPreference());

        clubDAO.update(club);

        return new ResponseEntity<>(new FullClubDTO().convert(club), HttpStatus.OK);
    }
}

