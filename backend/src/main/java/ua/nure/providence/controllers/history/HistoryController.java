package ua.nure.providence.controllers.history;

import com.mysema.commons.lang.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.HistoryDAO;
import ua.nure.providence.dtos.BaseListDTO;
import ua.nure.providence.dtos.business.cardholder.CardHolderDTO;
import ua.nure.providence.dtos.business.cardholder.NamedHolderDTO;
import ua.nure.providence.dtos.business.room.RoomDTO;
import ua.nure.providence.dtos.history.HistoryDTO;
import ua.nure.providence.dtos.history.session.HolderSessionDTO;
import ua.nure.providence.dtos.history.session.SessionDetailDTO;
import ua.nure.providence.enums.DeviceType;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.models.business.SubscribedDevice;
import ua.nure.providence.models.history.History;
import ua.nure.providence.services.notifications.NotificationFactory;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private CardHolderDAO holderDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<HistoryDTO>> getAccountHistory(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                     @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Pair<List<History>, Long> pair = dao.getAccountHistory(token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream()
                .map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/room/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<HistoryDTO>> getRoomHistory(@PathVariable("uuid") String uuid,
                                                                  @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                  @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Pair<List<History>, Long> pair = dao.getRoomHistory(uuid, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/cardholder/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<HistoryDTO>> getCardHolderHistory(@PathVariable("uuid") String uuid,
                                                                        @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                        @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        if (!holderDao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Pair<List<History>, Long> pair = dao.getCardHolderHistory(uuid, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/cardholder/{holderId}/room/{roomId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<HistoryDTO>> getRoomCardHolderHistory(@PathVariable("holderId") String holderId,
                                                                            @PathVariable("roomId") String roomId,
                                                                            @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                            @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        if (!holderDao.exists(holderId)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Pair<List<History>, Long> pair = dao.getCardHolderHistoryInRoom(holderId, roomId, token.getAuthenticatedUser(), limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream().map(history -> new HistoryDTO().convert(history))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/room/{uuid}/online", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<NamedHolderDTO>> getPresentPeopleInRoom(@PathVariable("uuid") String uuid,
                                                                              @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                              @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        Pair<List<CardHolder>, Long> pair = dao.getPresentCardHoldersInRoom(uuid, limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream().map(holder -> new NamedHolderDTO().convert(holder))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/room/{uuid}/holders/online", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<CardHolderDTO>> getDetailedPresentPeople(@PathVariable("uuid") String uuid,
                                                                                @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                                @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        Pair<List<CardHolder>, Long> pair = dao.getPresentCardHoldersInRoom(uuid, limit, offset);
        return ResponseEntity.ok(new BaseListDTO<>(pair.getFirst().stream().map(holder -> new CardHolderDTO().convert(holder))
                .collect(Collectors.toList()), limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity test() {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        History history = dao.getAccountHistory(token.getAuthenticatedUser(), 1, 0).getFirst().get(0);
        CardHolder holder = history.getCardHolder();
        holder.getSubscribers()
                .stream().map(User::getDevices)
                .map(subscribedDevices -> subscribedDevices.stream()
                        .collect(Collectors.groupingBy(SubscribedDevice::getType)))
                .forEach(subscriber -> subscriber.forEach((key, value) -> NotificationFactory.get(key)
                        .sendNotification(
                                history,
                                value
                                        .stream().map(SubscribedDevice::getSubscriptionKey)
                                        .collect(Collectors.toList()),
                                holder
                        )));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/cardHolder/{uuid}/find", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<RoomDTO> findCardHolderPlace(@PathVariable("uuid") String uuid) {
        if (!holderDao.exists(uuid)) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }
        Room room = dao.findCardHolderPosition(uuid);
        if (room == null) {
            return ResponseEntity.ok(new RoomDTO());
        }
        return ResponseEntity.ok(new RoomDTO().convert(room));
    }

    @RequestMapping(value = "/cardHolder/{uuid}/sessions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SessionDetailDTO> getCardHolderSessions(@PathVariable("uuid") String uuid) {
        List<HolderSessionDTO> result = new ArrayList<>();
        CardHolder holder = holderDao.getLightHolder(uuid);
        if (holder == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }
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
        return ResponseEntity.ok(new SessionDetailDTO(holder.getFullName(), result));

    }

}
