package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.QWorkDay;
import ua.malibu.ostpc.models.WorkDay;

@Repository
@Transactional
public class WorkDayDAO extends BaseDAO<WorkDay> {
    @Override
    public WorkDay get(String uuid) {
        return new JPAQuery<WorkDay>(entityManager).from(QWorkDay.workDay).where(QWorkDay.workDay.uuid.eq(uuid)).fetchOne();
    }
}
