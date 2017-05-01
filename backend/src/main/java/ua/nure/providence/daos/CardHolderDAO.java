package ua.nure.providence.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.business.CardHolder;

import javax.persistence.EntityManager;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("CardHolderDao")
@Transactional
public class CardHolderDAO extends BaseDAO<CardHolder> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public CardHolder get(String uuid) {
        return null;
    }
}
