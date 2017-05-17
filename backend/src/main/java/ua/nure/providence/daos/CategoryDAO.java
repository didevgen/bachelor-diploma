package ua.nure.providence.daos;

import com.mysema.commons.lang.Pair;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.QCardHolder;
import ua.nure.providence.models.hierarchy.QStructuralCategory;
import ua.nure.providence.models.hierarchy.StructuralCategory;

import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("categoryDao")
@Transactional
public class CategoryDAO extends BaseDAO<StructuralCategory> {

    @Override
    public StructuralCategory get(String uuid) {
        throw new NotImplementedException();
    }

    @Override
    public boolean exists(String uuid) {
        return new JPAQuery<StructuralCategory>(entityManager)
                .from(QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.uuid.eq(uuid))
                .fetchCount() > 0;
    }

    public StructuralCategory get(String uuid, User user) {
        QStructuralCategory category = new QStructuralCategory("source");
        QStructuralCategory parent = new QStructuralCategory("parent");
        QStructuralCategory children = new QStructuralCategory("children");

        return new JPAQuery<StructuralCategory>(entityManager)
                .from(category)
                .leftJoin(category.parent, parent)
                .leftJoin(category.children, children)
                .where(category.uuid.eq(uuid)
                        .and(category.account.eq(user.getAccount())))
                .fetchOne();
    }

    public StructuralCategory simpleGet(String uuid, User user) {
        QStructuralCategory category = new QStructuralCategory("source");

        return new JPAQuery<StructuralCategory>(entityManager)
                .from(category)
                .where(category.uuid.eq(uuid)
                        .and(category.account.eq(user.getAccount())))
                .fetchOne();
    }

    public StructuralCategory getDetail(String uuid, User user) {
        QStructuralCategory category = new QStructuralCategory("source");
        QStructuralCategory children = new QStructuralCategory("children");
        QStructuralCategory parent = new QStructuralCategory("parent");
        QCardHolder cardHolder = new QCardHolder("holder");
        return new JPAQuery<StructuralCategory>(entityManager)
                .from(category)
                .leftJoin(category.cardHolders, cardHolder)
                .leftJoin(category.children, children)
                .leftJoin(category.parent, parent)
                .where(category.uuid.eq(uuid)
                        .and(category.account.eq(user.getAccount())))
                .fetchOne();
    }

    public Pair<List<StructuralCategory>, Long> getAll(User user, long limit, long offset) {
        JPAQuery<StructuralCategory> query = new JPAQuery<StructuralCategory>(entityManager)
                .from(QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.account.eq(user.getAccount()))
                .orderBy(QStructuralCategory.structuralCategory.name.asc());
        return new Pair<>(query.limit(limit).offset(offset)
                .fetch(), query.fetchCount());
    }


    public Pair<List<StructuralCategory>, Long> getCategoriesWithHolders(User user, long limit, long offset) {
        JPAQuery<StructuralCategory> query = new JPAQuery<StructuralCategory>(entityManager)
                .from(QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.account.eq(user.getAccount()))
                .leftJoin(QStructuralCategory.structuralCategory.cardHolders, QCardHolder.cardHolder);
        long count = query.fetchCount();
        List<StructuralCategory> result = query.limit(limit).offset(offset)
                .fetch();
        return new Pair(result, count);
    }
}
