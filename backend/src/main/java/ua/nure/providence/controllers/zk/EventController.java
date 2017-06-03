package ua.nure.providence.controllers.zk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.daos.AccountDAO;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.dtos.events.AccountEventDTO;
import ua.nure.providence.dtos.events.EventDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.business.Card;
import ua.nure.providence.models.business.CardHolder;

import java.util.List;

/**
 * Created by User on 03.06.2017.
 */
@Transactional
@RestController
@RequestMapping("events")
public class EventController {

    @Autowired
    private CardHolderDAO cardHolderDAO;

    @Autowired
    private AccountDAO accountDAO;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addEvents(@RequestBody() AccountEventDTO data) {
        Account account = accountDAO.get(data.getAccountId());
        if (account == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, 400001, "Such account number is not found");
        }
        data.getEvents().forEach(event -> {
            CardHolder holder = cardHolderDAO.getHolderByCardNumber(account, event.getCardNumber());
            if (holder == null) {

            } else {

            }
        });
        return new ResponseEntity(HttpStatus.OK);
    }

    private CardHolder createInvalidHolder(String cardNumber) {
        CardHolder holder = new CardHolder();
        holder.setInvalid(true);
        Card card  = new Card();
        card.setCardNumber(cardNumber);
        card.setHolder(holder);
        return holder;
    }
}
