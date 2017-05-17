package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.QDoorLocker;
import ua.nure.providence.models.business.QRoom;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.utils.auth.LoginToken;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("roomDao")
@Transactional
public class RoomDAO extends BaseDAO<Room> {

    @Override
    public Room get(String uuid) {
        return new JPAQuery<Room>(entityManager)
                .from(QRoom.room)
                .where(QRoom.room.uuid.eq(uuid))
                .fetchOne();
    }

    public List<Room> getAll(Account account, long limit, long offset) {
        return this.getAllBaseQuery(account)
                .orderBy(QRoom.room.name.asc())
                .limit(limit).offset(offset).fetch();
    }

    public long getCount(Account account) {
        return this.getAllBaseQuery(account).fetchCount();
    }

    @Override
    public boolean exists(String uuid) {
        return new JPAQuery<Room>(entityManager)
                .from(QRoom.room)
                .where(QRoom.room.uuid.eq(uuid))
                .fetchCount() > 0;
    }

    private JPAQuery<Room> getAllBaseQuery(Account account) {
        return new JPAQuery<Room>(entityManager)
                .from(QRoom.room)
                .where(QRoom.room.account.eq(account));
    }
}
