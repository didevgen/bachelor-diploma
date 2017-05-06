package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.QCardHolder;
import ua.nure.providence.models.hierarchy.QStructuralCategory;
import ua.nure.providence.models.hierarchy.StructuralCategory;
import ua.nure.providence.utils.auth.LoginToken;

import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("categoryDao")
@Transactional
public class CategoryDAO extends BaseDAO<StructuralCategory> {

    @Override
    public StructuralCategory get(String uuid) {
        return null;
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

    public List<StructuralCategory> getAll(User user, long limit, long offset) {
        return new JPAQuery<StructuralCategory>(entityManager)
                .from(QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.account.eq(user.getAccount()))
                .orderBy(QStructuralCategory.structuralCategory.name.asc())
                .limit(limit).offset(offset)
                .fetch();
    }

    public List<StructuralCategory> getCategoriesWithHolders(User user, long limit, long offset) {
        return new JPAQuery<StructuralCategory>(entityManager)
                .from(QStructuralCategory.structuralCategory)
                .where(QStructuralCategory.structuralCategory.account.eq(user.getAccount()))
                .leftJoin(QStructuralCategory.structuralCategory.cardHolders, QCardHolder.cardHolder)
                .limit(limit).offset(offset)
                .fetch();
    }
}
