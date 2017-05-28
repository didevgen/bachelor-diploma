package ua.nure.providence.controllers.subscriptions;

import com.mysema.commons.lang.Pair;
import com.querydsl.jpa.impl.JPADeleteClause;
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
import ua.nure.providence.dtos.subscription.SubscriptionDTO;
import ua.nure.providence.enums.SubscriptionType;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.subscription.Subscription;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.List;
import java.util.Optional;
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

    @RequestMapping(value = "/subscribe/signal", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity addSubscriptions(@RequestBody() SubscriptionDTO data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        CardHolder cardHolder = cardHolderDAO.getHolder(data.getHolderUuid());
        User originalUser = userDao.get(user.getUuid());

        Subscription subscription = new Subscription();
        subscription.setSubscriptionKey(data.getOneSignalId());
        subscription.setUser(originalUser);
        subscription.getSubsribedHolders().add(cardHolder);
        subscription.setSubscriptionType(SubscriptionType.ONE_SIGNAL);
        originalUser.getSubscriptions().add(subscription);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/unsubscribe/signal", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeSubscriptions(@RequestBody() SubscriptionDTO data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        User originalUser = userDao.get(user.getUuid());
        Optional<Subscription> subscription = originalUser.getSubscriptions().stream().filter(item ->
                item.getSubscriptionKey().equals(data.getOneSignalId())).findFirst();
        if (subscription.isPresent()) {
            originalUser.getSubscriptions().removeIf(item -> item.getUuid().equals(subscription.get().getUuid()));
            dao.deleteSubscription(originalUser, subscription.get().getSubscriptionKey());
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
