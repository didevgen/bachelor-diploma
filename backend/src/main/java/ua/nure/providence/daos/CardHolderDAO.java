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
import ua.nure.providence.models.subscription.QSubscription;

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

    public List<CardHolder> getAll(Account account, String nameFilter, long limit, long offset) {
        JPAQuery<CardHolder> query = this.getAllBaseQuery(account);
        if (nameFilter != null) {
            query.where(QCardHolder.cardHolder.fullName.contains(nameFilter));
        }
        return query.orderBy(QCardHolder.cardHolder.fullName.asc())
                .limit(limit).offset(offset).fetch();
    }

    public CardHolder getHolder(String holder) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.subscriptions, QSubscription.subscription)
                .leftJoin(QSubscription.subscription.user, QUser.user)
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

    private JPAQuery<CardHolder> getAllBaseQuery(Account account) {
        return new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .leftJoin(QCardHolder.cardHolder.subscriptions, QSubscription.subscription)
                .leftJoin(QSubscription.subscription.user, QUser.user)
                .where(QStructuralCategory.structuralCategory.account.eq(account));
    }
}
