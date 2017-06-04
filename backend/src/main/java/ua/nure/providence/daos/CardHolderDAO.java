package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.authentication.QUser;
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
                .where(QCardHolder.cardHolder.uuid.eq(uuid)
                        .and(QCardHolder.cardHolder.invalid.isFalse()))
                .fetchOne();
    }

    public CardHolder getLightHolder(String uuid) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .where(QCardHolder.cardHolder.uuid.eq(uuid)
                        .and(QCardHolder.cardHolder.invalid.isFalse()))
                .fetchOne();
    }

    public List<CardHolder> getAll(Account account, String nameFilter, long limit, long offset) {
        JPAQuery<CardHolder> query = this.getAllBaseQuery(account);
        if (nameFilter != null) {
            query.where(QCardHolder.cardHolder.fullName.containsIgnoreCase(nameFilter));
        }
        return query.orderBy(QCardHolder.cardHolder.fullName.asc())
                .limit(limit).offset(offset).fetch();
    }

    public List<CardHolder> getInvalidHolders(Account account, String cardNumber, long limit, long offset) {
        JPAQuery<CardHolder> query = this.getAllInvalidBaseQuery(account);
        if (cardNumber != null) {
            query.where(QCard.card.cardNumber.containsIgnoreCase(cardNumber));
        }
        return query.where(QCardHolder.cardHolder.invalid.isTrue())
                .limit(limit).offset(offset).fetch();
    }

    public void updateCardHolderSubscribers(CardHolder holder) {
        new JPAUpdateClause(entityManager, QCardHolder.cardHolder)
                .set(QCardHolder.cardHolder.subscribers, holder.getSubscribers())
                .where(QCardHolder.cardHolder.uuid.eq(holder.getUuid()))
                .execute();
    }

    public CardHolder getHolder(String holder) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.subscribers, QUser.user)
                .where(QCardHolder.cardHolder.uuid.eq(holder)).fetchOne();
    }

    public long getCount(Account account) {
        return this.getAllBaseQuery(account).fetchCount();
    }

    @Override
    public boolean exists(String uuid) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .where(QCardHolder.cardHolder.uuid.eq(uuid))
                .fetchCount() > 0;
    }

    public CardHolder getHolderByCardNumber(Account account, String cardNumber) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .where(QCard.card.cardNumber.eq(cardNumber)
                        .and(QCardHolder.cardHolder.account.eq(account)))
                .fetchOne();
    }

    private JPAQuery<CardHolder> getAllInvalidBaseQuery(Account account) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .where(QCardHolder.cardHolder.account.eq(account));
    }

    private JPAQuery<CardHolder> getAllBaseQuery(Account account) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .leftJoin(QCardHolder.cardHolder.subscribers, QUser.user)
                .where(QCardHolder.cardHolder.account.eq(account)
                        .and(QCardHolder.cardHolder.invalid.isFalse()));
    }
}
