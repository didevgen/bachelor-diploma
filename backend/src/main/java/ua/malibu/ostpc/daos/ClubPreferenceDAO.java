package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.ClubPreference;
import ua.malibu.ostpc.models.QClubPreference;

public class ClubPreferenceDAO extends BaseDAO<ClubPreference> {
    @Override
    public ClubPreference get(String uuid) {
        return new JPAQuery<ClubPreference>(entityManager)
                .from(QClubPreference.clubPreference)
                .where(QClubPreference.clubPreference.uuid.eq(uuid)).fetchOne();
    }
}
