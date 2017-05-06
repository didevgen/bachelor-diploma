package ua.nure.providence.controllers.history;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.HistoryDAO;
import ua.nure.providence.daos.RoomDAO;
import ua.nure.providence.dtos.history.HistoryDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.models.history.History;
import ua.nure.providence.models.zk.internal.EventType;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

}