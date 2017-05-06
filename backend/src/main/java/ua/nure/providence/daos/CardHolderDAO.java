package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.QCard;
import ua.nure.providence.models.business.QCardHolder;
import ua.nure.providence.models.hierarchy.QStructuralCategory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("CardHolderDao")
@Transactional
public class CardHolderDAO extends BaseDAO<CardHolder> {

    @Override
    public CardHolder get(String uuid) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .where(QCardHolder.cardHolder.uuid.eq(uuid))
                .fetchOne();
    }
    public List<CardHolder> getAll(Account account, long limit, long offset) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.account.eq(account))
                .orderBy(QCardHolder.cardHolder.fullName.asc())
                .limit(limit).offset(offset).fetch();
    }

    @Override
    public boolean exists(String uuid) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .where(QCardHolder.cardHolder.uuid.eq(uuid))
                .fetchCount() > 0;
    }
}
