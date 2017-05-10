package ua.nure.providence.controllers.history;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.HistoryDAO;
import ua.nure.providence.dtos.business.cardholder.NamedHolderDTO;
import ua.nure.providence.dtos.business.room.RoomDTO;
import ua.nure.providence.dtos.history.HistoryDTO;
import ua.nure.providence.dtos.history.session.HolderSessionDTO;
import ua.nure.providence.dtos.history.session.IntermediateSessionDTO;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.models.history.History;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 06.05.2017.
 */
@RestController
@Transactional
@RequestMapping("history")
public class HistoryController {

    @Autowired
    private HistoryDAO dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HistoryDTO>> getAccountHistory(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                              @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        List<History> histories = dao.getAccountHistory(token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(histories.stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/room/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HistoryDTO>> getRoomHistory(@PathVariable("uuid") String uuid,
                                                              @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                              @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        List<History> histories = dao.getRoomHistory(uuid, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(histories.stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/cardholder/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HistoryDTO>> getCardHolderHistory(@PathVariable("uuid") String uuid,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                           @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        List<History> histories = dao.getCardHolderHistory(uuid, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(histories.stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/cardholder/{holderId}/room/{roomId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HistoryDTO>> getRoomCardHolderHistory(@PathVariable("holderId") String holderId,
                                                                     @PathVariable("roomId") String roomId,
                                                                 @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                 @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        List<History> histories = dao.getCardHolderHistoryInRoom(holderId, roomId, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(histories.stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/room/{uuid}/online", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<NamedHolderDTO>> getPresentPeopleInRoom(@PathVariable("uuid") String uuid,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                           @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        List<CardHolder> holders = dao.getPresentCardHoldersInRoom(uuid, limit, offset);
        return ResponseEntity.ok(holders.stream().map(holder -> new NamedHolderDTO().convert(holder))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/cardHolder/{uuid}/find", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<RoomDTO> findCardHolderPlace(@PathVariable("uuid") String uuid) {
        Room room = dao.findCardHolderPosition(uuid);
        if (room == null) {
            return ResponseEntity.ok(new RoomDTO());
        }
        return ResponseEntity.ok(new RoomDTO().convert(room));
    }

    @RequestMapping(value = "/cardHolder/{uuid}/sessions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<HolderSessionDTO>> getCardHolderSessions(@PathVariable("uuid") String uuid) {
        List<HolderSessionDTO> result = new ArrayList<>();
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Map<Room, List<History>> histories = dao.getCardHolderSessions(uuid, token.getAuthenticatedUser());

        for (Map.Entry<Room, List<History>> entry : histories.entrySet()) {

            List<History> entries = entry.getValue().stream().filter(history -> history.getInOutState() == 1)
                    .sorted(Comparator.comparing(History::getTimeStamp))
                    .collect(Collectors.toList());
            List<History> exits = entry.getValue().stream().filter(history -> history.getInOutState() == 0)
                    .sorted(Comparator.comparing(History::getTimeStamp))
                    .collect(Collectors.toList());

            for (History exit : exits) {
                List<History> entrances = entries.stream()
                        .filter(entrance -> entrance.getTimeStamp().compareTo(exit.getTimeStamp()) <= 0)
                        .sorted(Comparator.comparing(History::getTimeStamp))
                        .collect(Collectors.toList());

                if (entrances.size() > 0) {
                    List<HolderSessionDTO> entrancesWithExit = new ArrayList<>();
                    entrances.forEach(subItem -> {
                        if (subItem.getTimeStamp().toLocalDate().compareTo(exit.getTimeStamp().toLocalDate()) == 0) {
                            HolderSessionDTO sessionItem = new HolderSessionDTO(
                                    subItem.getTimeStamp().toString(),
                                    exit.getTimeStamp().toString(),
                                    new RoomDTO().convert(entry.getKey()));
                            entrancesWithExit.add(sessionItem);
                        } else {
                            HolderSessionDTO sessionItem = new HolderSessionDTO(
                                    subItem.getTimeStamp().toString(),
                                    subItem.getTimeStamp().toLocalDate().toString(),
                                    new RoomDTO().convert(entry.getKey()));
                            entrancesWithExit.add(sessionItem);
                        }
                    });
                    if (entrancesWithExit.stream().noneMatch(elem ->
                            elem.getSessionEnd().equals(exit.getTimeStamp().toString()))) {
                        HolderSessionDTO sessionItem = new HolderSessionDTO(
                                exit.getTimeStamp().toLocalDate().toString(),
                                exit.getTimeStamp().toString(),
                                new RoomDTO().convert(entry.getKey()));
                        entrancesWithExit.add(sessionItem);
                    }
                    result.addAll(entrancesWithExit);
                } else {
                    HolderSessionDTO sessionItem = new HolderSessionDTO(
                            exit.getTimeStamp().toLocalDate().toString(),
                            exit.getTimeStamp().toString(),
                            new RoomDTO().convert(exit.getRoom()));
                    result.add(sessionItem);
                }

                entries = entries.stream().filter(item -> !entrances.contains(item))
                        .collect(Collectors.toList());
            }

            for (History item : entries) {
                HolderSessionDTO sessionItem = new HolderSessionDTO(
                        item.getTimeStamp().toString(),
                        item.getTimeStamp().toLocalDate().plusDays(1)
                                .toDateTimeAtStartOfDay().minusMinutes(1).toString(),
                        new RoomDTO().convert(item.getRoom()));
                result.add(sessionItem);
            }
        }
        return ResponseEntity.ok(result);

    }

}
