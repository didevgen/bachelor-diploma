package ua.nure.providence.daos;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.*;
import ua.nure.providence.models.history.History;
import ua.nure.providence.models.history.QHistory;
import ua.nure.providence.models.zk.internal.EventType;
import ua.nure.providence.models.zk.internal.QEventType;
import static com.querydsl.core.group.GroupBy.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Providence Team on 06.05.2017.
 */
@Repository("historyDao")
@Transactional
public class HistoryDAO extends BaseDAO<History> {

    public List<History> getAccountHistory(User user, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .where(QRoom.room.account.eq(user.getAccount()))
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<History> getRoomHistory(String roomUuid, User user, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QRoom.room.uuid.eq(roomUuid)))
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<History> getCardHolderHistory(String cardHolderUuid, User user, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QCardHolder.cardHolder.uuid.eq(cardHolderUuid)))
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public Map<Room, List<History>> getCardHolderSessions(String cardHolderUuid, User user) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QCardHolder.cardHolder.uuid.eq(cardHolderUuid)))
                .orderBy(QHistory.history.timeStamp.asc())
                .transform(groupBy(QRoom.room).as(list(QHistory.history)));
    }

    public List<History> getCardHolderHistoryInRoom(String cardHolderUuid, String roomUuid,
                                                    User user, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QCardHolder.cardHolder.uuid.eq(cardHolderUuid))
                        .and(QRoom.room.uuid.eq(roomUuid))
                ).limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<History> getRangedAccountHistory(User user, DateTime start, DateTime end, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QHistory.history.timeStamp.goe(start))
                        .and(QHistory.history.timeStamp.loe(end))
                )
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<History> getRangedRoomHistory(String roomUuid, DateTime start, DateTime end,
                                              User user, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QRoom.room.uuid.eq(roomUuid))
                        .and(QHistory.history.timeStamp.goe(start))
                        .and(QHistory.history.timeStamp.loe(end)))
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<History> getRangedCardHolderHistory(String cardHolderUuid, User user, DateTime start,
                                                    DateTime end, long limit, long offset) {
        return new JPAQuery<History>(entityManager)
                .from(QHistory.history)
                .leftJoin(QHistory.history.room, QRoom.room)
                .leftJoin(QHistory.history.eventType, QEventType.eventType)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QRoom.room.account.eq(user.getAccount())
                        .and(QCardHolder.cardHolder.uuid.eq(cardHolderUuid))
                        .and(QHistory.history.timeStamp.goe(start))
                        .and(QHistory.history.timeStamp.loe(end)))
                .limit(limit).offset(offset)
                .orderBy(QHistory.history.timeStamp.desc())
                .fetch();
    }

    public List<EventType> getEventTypes() {
        return new JPAQuery<EventType>(entityManager)
                .from(QEventType.eventType)
                .fetch();
    }

    public List<CardHolder> getPresentCardHoldersInRoom(String roomUuid, long limit, long offset) {
        QHistory parent = new QHistory("parent");
        QHistory child = new QHistory("child");
        QCardHolder cardHolder = new QCardHolder("holder");
        QRoom room = new QRoom("room");
        return new JPAQuery<CardHolder>(entityManager)
                .select(cardHolder)
                .from(parent)
                .leftJoin(parent.room, room)
                .leftJoin(parent.cardHolder, cardHolder)
                .where(room.uuid.eq(roomUuid).and(parent.inOutState.eq(1)))
                .groupBy(cardHolder.id, cardHolder.uuid, cardHolder.fullName)
                .having(parent.timeStamp.max().goe(
                        JPAExpressions.select(child.timeStamp.max()).from(child)
                                .where(cardHolder.id.eq(child.cardHolder.id)
                                        .and(child.room.uuid.eq(roomUuid))
                                        .and(child.inOutState.eq(0))
                                )))
                .limit(limit).offset(offset)
                .fetch();
    }

    public Room findCardHolderPosition(String holderUuid) {
        DateTime latestEntrance = new JPAQuery<DateTime>(entityManager)
                .select(QHistory.history.timeStamp.max())
                .from(QHistory.history)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QCardHolder.cardHolder.uuid.eq(holderUuid).and(QHistory.history.inOutState.eq(1)))
                .fetchOne();
        DateTime latestExit = new JPAQuery<DateTime>(entityManager)
                .select(QHistory.history.timeStamp.max())
                .from(QHistory.history)
                .leftJoin(QHistory.history.cardHolder, QCardHolder.cardHolder)
                .where(QCardHolder.cardHolder.uuid.eq(holderUuid).and(QHistory.history.inOutState.eq(0)))
                .fetchOne();
        if (latestEntrance.getMillis() > latestExit.getMillis()) {
            QHistory history = new QHistory("history");
            QRoom room = new QRoom("room");
            return new JPAQuery<History>(entityManager)
                    .select(room)
                    .from(history)
                    .leftJoin(history.room, room)
                    .where(history.timeStamp.eq(latestEntrance))
                    .fetchOne();
        }
        return null;
    }

    @Override
    public History get(String uuid) {
        throw new NotImplementedException();
    }

    @Override
    public boolean exists(String uuid) {
        throw new NotImplementedException();
    }
}
