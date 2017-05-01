package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.authentication.QAccount;

import javax.persistence.EntityManager;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("accountDao")
@Transactional
public class AccountDAO extends BaseDAO<Account> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Account get(String uuid) {
        return new JPAQuery<Account>(entityManager)
                .from(QAccount.account)
                .where(QAccount.account.uuid.eq(uuid))
                .fetchOne();
    }

    public Account get(long id) {
        return new JPAQuery<Account>(entityManager)
                .from(QAccount.account)
                .where(QAccount.account.id.eq(id))
                .fetchOne();
    }
}
