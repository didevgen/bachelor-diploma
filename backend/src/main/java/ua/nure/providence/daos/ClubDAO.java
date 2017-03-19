package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.Club;
import ua.malibu.ostpc.models.QClub;

@Repository
@Transactional
public class ClubDAO extends BaseDAO<Club> {
    @Override
    public Club get(String uuid) {
        return new JPAQuery<Club>(entityManager)
                .from(QClub.club)
                .where(QClub.club.uuid.eq(uuid))
                .fetchOne();
    }

}
