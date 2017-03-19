package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.QShift;
import ua.nure.providence.models.Shift;

@Repository("shiftDao")
@Transactional
public class ShiftDAO extends BaseDAO<Shift> {
    @Override
    public Shift get(String uuid) {
        return new JPAQuery<Shift>(entityManager).from(QShift.shift).where(QShift.shift.uuid.eq(uuid)).fetchOne();
    }

}
