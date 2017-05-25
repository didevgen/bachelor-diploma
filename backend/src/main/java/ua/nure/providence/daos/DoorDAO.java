package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.DoorLocker;
import ua.nure.providence.models.business.QDoorConfiguration;
import ua.nure.providence.models.business.QDoorLocker;
import ua.nure.providence.models.business.QRoom;

import java.util.List;

/**
 * Created by Providence Team on 07.05.2017.
 */
@Repository("doorDao")
@Transactional
public class DoorDAO extends BaseDAO<DoorLocker> {

    @Override
    public DoorLocker get(String uuid) {
        return null;
    }

    public DoorLocker get(String uuid, User user) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .leftJoin(QDoorLocker.doorLocker.room, QRoom.room)
                .leftJoin(QDoorLocker.doorLocker.configuration, QDoorConfiguration.doorConfiguration)
                .where(QDoorLocker.doorLocker.uuid.eq(uuid)
                        .and(QRoom.room.account.eq(user.getAccount())))
                .fetchOne();
    }

    public boolean exists(String uuid, User user) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .leftJoin(QDoorLocker.doorLocker.room, QRoom.room)
                .where(QDoorLocker.doorLocker.uuid.eq(uuid)
                        .and(QRoom.room.account.eq(user.getAccount())))
                .fetchCount() > 0;
    }

    public void deleteDoorConfigurations(DoorLocker door) {
        new JPADeleteClause(entityManager, QDoorConfiguration.doorConfiguration)
                .where(QDoorConfiguration.doorConfiguration.locker.eq(door))
                .execute();
    }

    public List<DoorLocker> getAllDoorConfigurations(Account account, long limit, long offset) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .leftJoin(QDoorLocker.doorLocker.room, QRoom.room)
                .where(QRoom.room.account.eq(account))
                .orderBy(QRoom.room.name.asc())
                .limit(limit).offset(offset)
                .fetch();
    }

    public List<DoorLocker> getRoomDoorConfiguration(Account account, String uuid, long limit, long offset) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .leftJoin(QDoorLocker.doorLocker.configuration, QDoorConfiguration.doorConfiguration)
                .leftJoin(QDoorLocker.doorLocker.room, QRoom.room)
                .where(QRoom.room.account.eq(account)
                        .and(QRoom.room.uuid.eq(uuid)))
                .limit(limit).offset(offset)
                .fetch();
    }

    @Override
    public boolean exists(String uuid) {
        return false;
    }
}
