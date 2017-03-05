package ua.malibu.ostpc.controllers.clubPreference;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.daos.ClubPreferenceDAO;
import ua.malibu.ostpc.dtos.clubPreferenceDTO.ClubPreferenceDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;

/**
 * Created by Игорь on 09.02.2017.
 */
public class ClubPreferenceController extends BaseController {
    @Autowired
    private ClubPreferenceDAO clubPreferenceDAO;

    @RequestMapping(value = "/clubpreference/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ClubPreferenceDTO> getClubPreference (@PathVariable (name = "uuid") String uuid) {
        ClubPreference clubPreference = clubPreferenceDAO.get(uuid);
        if (clubPreference != null){
            return new ResponseEntity<ClubPreferenceDTO>(new ClubPreferenceDTO().convert(clubPreference), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/clubpreference/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteClubPreference (@PathVariable(name = "uuid") String uuid) {
        ClubPreference clubPreference = clubPreferenceDAO.get(uuid);
        if (clubPreference != null){
            clubPreferenceDAO.delete(clubPreference);
            return new ResponseEntity<Boolean>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/clubpreference/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ClubPreferenceDTO> updateClubPreference (@PathVariable(name = "uuid") String uuid,
                                                                   @RequestBody ClubPreferenceDTO clubPreferenceDTO) {
        ClubPreference clubPreference = clubPreferenceDAO.get(uuid);
        if (clubPreference != null){
            clubPreference.setClub(new JPAQuery<Club>(entityManager)
                    .from(QClub.club)
                    .where(QClub.club.uuid.eq(uuid)).fetchOne());
            clubPreference.setUsers(new JPAQuery<User>(entityManager)
                    .from(QUser.user)
                    .where(QUser.user.uuid.in(clubPreferenceDTO.getUsers())).fetch());
            clubPreferenceDAO.update(clubPreference);
            return new ResponseEntity<ClubPreferenceDTO>(new ClubPreferenceDTO().convert(clubPreference), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/clubpreference/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ClubPreferenceDTO> createClubPreference (@RequestBody ClubPreferenceDTO clubPreferenceDTO){
        ClubPreference clubPreference = new ClubPreference();
        clubPreference.setClub(new JPAQuery<Club>(entityManager)
                .from(QClub.club)
                .where(QClub.club.uuid.eq(clubPreferenceDTO.getUuid())).fetchOne());
        clubPreference.setUsers(new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.uuid.in(clubPreferenceDTO.getUsers())).fetch());
        clubPreferenceDAO.insert(clubPreference);
        return new ResponseEntity<ClubPreferenceDTO>(new ClubPreferenceDTO().convert(clubPreference), HttpStatus.OK);
    }
}
