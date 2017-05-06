package ua.nure.providence.controllers.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.CardDAO;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.CategoryDAO;
import ua.nure.providence.dtos.business.cardholder.CardHolderDTO;
import ua.nure.providence.dtos.business.cardholder.CardHolderUpdateDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.business.Card;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.hierarchy.StructuralCategory;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 02.05.2017.
 */
@RestController
@Transactional
@RequestMapping("holder")
public class CardHolderController extends BaseController {

    @Autowired
    private CardHolderDAO dao;

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CardHolderDTO> getCardHolder(@PathVariable(name = "uuid") String uuid) {
        CardHolder holder = dao.get(uuid);
        if (holder == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404005, "Specified holder not found");
        }
        return ResponseEntity.ok(new CardHolderDTO().convert(holder));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<CardHolderDTO>> getCardHolders(@RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
                                                  @RequestParam(value = "offset", required = false, defaultValue = "0") long offset) {
        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        List<CardHolderDTO> result = dao.getAll(token.getAuthenticatedUser().getAccount(), limit, offset).stream()
                .map(holder -> new CardHolderDTO().convert(holder))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CardHolderDTO> updateCardHolder(@RequestBody() CardHolderUpdateDTO dto) {
        CardHolder cardHolder = this.dao.get(dto.getUuid());

        if (cardHolder == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }

        dto.fromDTO(cardHolder);
        dto.getCards().stream()
                .filter(card -> cardHolder.getCards().stream()
                        .noneMatch(item -> item.getCardNumber().equals(card.getCardNumber())))
                .forEach(result -> {
                    Card card = new Card();
                    card.setCardNumber(result.getCardNumber());
                    card.setHolder(cardHolder);
                    cardDAO.insert(card);
                });

        dto.getCategories().stream()
                .filter(category -> cardHolder.getCategories().stream()
                        .noneMatch(holderCategory -> holderCategory.getUuid().equals(category)))
                .forEach(result -> {
                    StructuralCategory category = categoryDAO.get(result);
                    if (category == null) {
                        throw new RestException(HttpStatus.BAD_REQUEST, 400007, "Invalid category was provided");
                    }
                    cardHolder.getCategories().add(category);
                });

        dao.update(cardHolder);
        return ResponseEntity.ok(new CardHolderDTO().convert(cardHolder));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CardHolderDTO> createCardHolder(@RequestBody() CardHolderUpdateDTO dto) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        CardHolder cardHolder = new CardHolder();
        dto.fromDTO(cardHolder);
        dao.insert(cardHolder);

        dto.getCards().forEach(item -> {
            Card card = new Card();
            card.setCardNumber(item.getCardNumber());
            card.setHolder(cardHolder);
            cardHolder.getCards().add(card);
        });

        dto.getCategories().forEach(item -> {
            StructuralCategory category = categoryDAO.get(item);
            if (category == null) {
                throw new RestException(HttpStatus.BAD_REQUEST, 400007, "Invalid category was provided");
            }
            cardHolder.getCategories().add(category);
        });

        return ResponseEntity.ok(new CardHolderDTO().convert(cardHolder));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteHolder(@PathVariable(name = "uuid") String uuid) {
        CardHolder cardHolder = dao.get(uuid);
        if (cardHolder == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404004, "Specified holder not found");
        }
        dao.delete(cardHolder);
        return new ResponseEntity(HttpStatus.OK);
    }

}
