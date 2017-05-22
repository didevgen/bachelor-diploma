package ua.nure.providence.controllers.subscriptions;

import com.mysema.commons.lang.Pair;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.SubscriptionDAO;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.dtos.BaseDataDTO;
import ua.nure.providence.dtos.BaseListDTO;
import ua.nure.providence.dtos.business.cardholder.CardHolderDTO;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 21.05.2017.
 */
@Transactional
@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionDAO dao;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private CardHolderDAO cardHolderDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BaseListDTO<CardHolderDTO>> getSubscriptions(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                                       @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        Pair<List<CardHolder>, Long> pair = dao.getAllSubscriptions(user, limit, offset);
        return ResponseEntity.ok(
                new BaseListDTO<>(pair.getFirst()
                        .stream().map(item -> new CardHolderDTO().convert(item))
                        .collect(Collectors.toList()),
                        limit, offset, pair.getSecond()));
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addSubscriptions(@RequestBody() BaseDataDTO<String> data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        CardHolder cardHolder = cardHolderDAO.getHolder(data.getData());
        cardHolder.getSubscribers().add(user);
        cardHolderDAO.updateCardHolderSubscribers(cardHolder);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeSubscriptions(@RequestBody() BaseDataDTO<List<String>> data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        user.setHolderSubscriptions(user.getHolderSubscriptions().stream().filter(item ->
                data.getData().stream().noneMatch(dataItem -> dataItem.equals(item.getUuid())))
                .collect(Collectors.toList()));
        userDao.update(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
