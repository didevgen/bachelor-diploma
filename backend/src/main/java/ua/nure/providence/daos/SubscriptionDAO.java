package ua.nure.providence.daos;

import com.mysema.commons.lang.Pair;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.QUser;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.QCard;
import ua.nure.providence.models.business.QCardHolder;
import ua.nure.providence.models.hierarchy.QStructuralCategory;

import java.util.List;

/**
 * Created by Providence Team on 21.05.2017.
 */
@Repository("subscriptionDao")
@Transactional
public class SubscriptionDAO extends BaseDAO<CardHolder> {

    @Override
    public CardHolder get(String uuid) {
        return null;
    }

    @Override
    public boolean exists(String uuid) {
        return false;
    }

    public Pair<List<CardHolder>, Long> getAllSubscriptions(User user, long limit, long offset) {
        JPAQuery<CardHolder> query = new JPAQuery<CardHolder>(entityManager)
                .from(QCardHolder.cardHolder)
                .leftJoin(QCardHolder.cardHolder.subscribers, QUser.user)
                .leftJoin(QCardHolder.cardHolder.cards, QCard.card)
                .leftJoin(QCardHolder.cardHolder.categories, QStructuralCategory.structuralCategory)
                .where(QUser.user.eq(user));
        return new Pair<>(query
                .limit(limit).offset(offset)
                .fetch(), query.fetchCount());
    }

}
