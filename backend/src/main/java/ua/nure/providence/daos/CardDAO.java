package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.business.Card;
import ua.nure.providence.models.business.QCard;

/**
 * Created by Providence Team on 02.05.2017.
 */
@Repository("cardDao")
@Transactional
public class CardDAO extends BaseDAO<Card> {

    @Override
    public Card get(String uuid) {
        return new JPAQuery<Card>(entityManager)
                .from(QCard.card)
                .where(QCard.card.uuid.eq(uuid))
                .fetchOne();
    }
}
