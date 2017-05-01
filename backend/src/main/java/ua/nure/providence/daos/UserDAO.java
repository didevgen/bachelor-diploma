package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.QUser;
import ua.nure.providence.models.authentication.User;

import java.util.Optional;

@Repository("userDao")
@Transactional
public class UserDAO extends BaseDAO<User>{

    public User getByEmail(String email) {
        return new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.email.eq(email))
                .fetchOne();
    }

    public boolean exists(String email, String password) {
        return new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.email.eq(email).and(QUser.user.password.eq(password)))
                .fetchCount() > 0;
    }
    public Optional<User> getByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.email.eq(email).and(QUser.user.password.eq(password)))
                .fetchOne());
    }
    @Override
    public User get(String uuid) {
        return new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.uuid.eq(uuid))
                .fetchOne();
    }


}
