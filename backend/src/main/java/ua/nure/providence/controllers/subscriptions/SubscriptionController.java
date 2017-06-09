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
import ua.nure.providence.daos.DeviceDAO;
import ua.nure.providence.daos.SubscriptionDAO;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.dtos.BaseDataDTO;
import ua.nure.providence.dtos.BaseListDTO;
import ua.nure.providence.dtos.business.cardholder.CardHolderDTO;
import ua.nure.providence.dtos.business.device.DeviceDTO;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.SubscribedDevice;
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
    private DeviceDAO deviceDAO;

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
    @Transactional
    public ResponseEntity addSubscriptions(@RequestBody() BaseDataDTO<String> data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        CardHolder cardHolder = cardHolderDAO.getHolder(data.getData());
        User originalUser = userDao.get(user.getUuid());
        if (originalUser.getHolderSubscriptions().stream()
                .noneMatch(item -> item.getUuid().equals(cardHolder.getUuid()))) {
            originalUser.getHolderSubscriptions().add(cardHolder);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeSubscriptions(@RequestBody() BaseDataDTO<String> data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        User originalUser = userDao.get(user.getUuid());
        originalUser.getHolderSubscriptions().removeIf(item ->
                data.getData().equals(item.getUuid()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/device/register", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity registerDevice(@RequestBody() DeviceDTO data) {
        User user = ((LoginToken) SecurityContextHolder.getContext().getAuthentication()).getAuthenticatedUser();
        User originalUser = userDao.get(user.getUuid());
        if (!deviceDAO.exists(originalUser, data.getSubscriptionKey())) {
            SubscribedDevice device = new SubscribedDevice();
            device.setSubscriptionKey(data.getSubscriptionKey());
            device.setType(data.getDeviceType());
            device.setOwner(user);
            deviceDAO.insert(device);
            originalUser.getDevices().add(device);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
