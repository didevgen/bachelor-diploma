package ua.nure.providence.controllers.zk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.DoorDAO;
import ua.nure.providence.daos.RoomDAO;
import ua.nure.providence.dtos.doors.DoorDTO;
import ua.nure.providence.dtos.doors.DoorUpdateDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.business.DoorConfiguration;
import ua.nure.providence.models.business.DoorLocker;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.stream.Collectors;

/**
 * Created by Providence Team on 07.05.2017.
 */
@RestController
@Transactional
@RequestMapping("doors")
public class ZKController extends BaseController {

    @Autowired
    private DoorDAO dao;

    @Autowired
    private RoomDAO roomDAO;

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DoorDTO> getDoorLocker(@PathVariable(name = "uuid") String uuid) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DoorDTO> updateDoorLocker(@PathVariable(name = "uuid") String uuid,
                                                    @RequestBody() DoorUpdateDTO dto) {
        if (dto.getConfigurations() == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, 400011, "The configuration is not specified");
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        dao.deleteDoorConfigurations(locker);
        locker.setConfiguration(dto.getConfigurations().stream().map(doorConfigurationDTO -> {
            DoorConfiguration configuration = new DoorConfiguration();
            doorConfigurationDTO.fromDTO(configuration);
            return configuration;
        }).collect(Collectors.toList()));

        if (dto.getRoomUuid() != null) {
            locker.setRoom(roomDAO.get(dto.getRoomUuid()));
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, 400008, "The room is not specified");
        }

        dao.update(locker);
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DoorDTO> updateDoorLocker(@RequestBody() DoorUpdateDTO dto) {
        DoorLocker locker = new DoorLocker();

        if (dto.getConfigurations() == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, 400011, "The configuration is not specified");
        }

        locker.setConfiguration(dto.getConfigurations().stream().map(doorConfigurationDTO -> {
            DoorConfiguration configuration = new DoorConfiguration();
            doorConfigurationDTO.fromDTO(configuration);
            return configuration;
        }).collect(Collectors.toList()));

        if (dto.getRoomUuid() != null) {
            locker.setRoom(roomDAO.get(dto.getRoomUuid()));
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, 400008, "The room is not specified");
        }

        dao.insert(locker);
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteDoorLocker(@PathVariable(name = "uuid") String uuid) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        dao.delete(locker);
        return new ResponseEntity(HttpStatus.OK);
    }
}
