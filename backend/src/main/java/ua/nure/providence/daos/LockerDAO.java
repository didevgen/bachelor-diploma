package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.Account;
import ua.nure.providence.models.authentication.QAccount;
import ua.nure.providence.models.business.DoorLocker;
import ua.nure.providence.models.business.QDoorLocker;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("lockerDao")
@Transactional
public class LockerDAO extends BaseDAO<DoorLocker> {

    @Override
    public DoorLocker get(String uuid) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .where(QDoorLocker.doorLocker.uuid.eq(uuid))
                .fetchOne();
    }

    public List<DoorLocker> getLockers(List<String> uuids) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .where(QDoorLocker.doorLocker.uuid.in(uuids)).fetch();
    }

    @Override
    public boolean exists(String uuid) {
        return new JPAQuery<DoorLocker>(entityManager)
                .from(QDoorLocker.doorLocker)
                .where(QDoorLocker.doorLocker.uuid.eq(uuid))
                .fetchCount() > 0;
    }
}
