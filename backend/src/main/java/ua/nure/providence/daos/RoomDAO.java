package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.business.QRoom;
import ua.nure.providence.models.business.Room;

import javax.persistence.EntityManager;

/**
 * Created by Providence Team on 01.05.2017.
 */
@Repository("roomDao")
@Transactional
public class RoomDAO extends BaseDAO<Room> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Room get(String uuid) {
        return new JPAQuery<Room>(entityManager)
                .from(QRoom.room)
                .where(QRoom.room.uuid.eq(uuid))
                .fetchOne();
    }

}
