package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.QSchedule;
import ua.nure.providence.models.Schedule;

@Repository
@Transactional
public class ScheduleDAO extends BaseDAO<Schedule> {
    @Override
    public Schedule get(String uuid) {
        return new JPAQuery<Schedule>(entityManager).from(QSchedule.schedule).where(QSchedule.schedule.uuid.eq(uuid)).fetchOne();
    }
}
