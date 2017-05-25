package ua.nure.providence.controllers.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.HistoryDAO;
import ua.nure.providence.daos.LockerDAO;
import ua.nure.providence.daos.RoomDAO;
import ua.nure.providence.dtos.BaseListDTO;
import ua.nure.providence.dtos.business.room.RoomDTO;
import ua.nure.providence.dtos.business.room.RoomUpdateDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 02.05.2017.
 */
@Transactional
@RestController
@RequestMapping("rooms")
public class RoomController extends BaseController {

    @Autowired
    private RoomDAO dao;

    @Autowired
    private HistoryDAO historyDAO;

    @Autowired
    private LockerDAO lockerDAO;

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<RoomDTO> getRoom(@PathVariable(name = "uuid") String uuid) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified room not found");
        }
        Room room = dao.get(uuid);
        return ResponseEntity.ok(new RoomDTO().convert(room));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<RoomDTO>> getRooms(
            @RequestParam(value = "name", required = false) String nameFilter,
            @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        long count = dao.getCount(token.getAuthenticatedUser().getAccount(), nameFilter);

        List<RoomDTO> result = dao.getAll(token.getAuthenticatedUser().getAccount(), limit, offset, nameFilter).stream()
                .map(room -> new RoomDTO().convert(room))
                .map(room -> {
                    room.setOnline(historyDAO.getOnlineCountToday(room.getUuid()));
                    return room;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(new BaseListDTO<>(result, limit, offset, count));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateRoom(@PathVariable(name = "uuid") String uuid, @RequestBody() RoomUpdateDTO roomUpdateDTO) {

        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified room not found");
        }
        Room previousRoom = this.dao.get(uuid);

        roomUpdateDTO.fromDTO(previousRoom);
        previousRoom.setDoors(lockerDAO.getLockers(roomUpdateDTO.getLockers()));
        dao.update(previousRoom);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RoomUpdateDTO> createRoom(@RequestBody() RoomUpdateDTO roomUpdateDTO) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Room room = new Room();
        roomUpdateDTO.fromDTO(room);
        room.setDoors(lockerDAO.getLockers(roomUpdateDTO.getLockers()));
        room.setAccount(token.getAuthenticatedUser().getAccount());
        dao.insert(room);
        roomUpdateDTO.setUuid(room.getUuid());
        return ResponseEntity.ok(roomUpdateDTO);
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteRoom(@PathVariable(name = "uuid") String uuid) {
        if (!dao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified room not found");
        }
        Room room = dao.get(uuid);

        dao.delete(room);
        return new ResponseEntity(HttpStatus.OK);
    }
}
