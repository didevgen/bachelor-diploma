package ua.nure.providence.daos;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.providence.models.authentication.QUser;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.QSubscribedDevice;
import ua.nure.providence.models.business.SubscribedDevice;

/**
 * Created by User on 02.06.2017.
 */
@Repository("deviceDao")
@Transactional
public class DeviceDAO extends BaseDAO<SubscribedDevice> {

    @Override
    public SubscribedDevice get(String subscriptionKey) {
        return null;
    }

    @Override
    public boolean exists(String subscriptionKey) {
        return false;
    }

    public boolean exists(User user, String subscriptionKey) {
        return new JPAQuery<Boolean>(entityManager)
                .from(QSubscribedDevice.subscribedDevice)
                .leftJoin(QSubscribedDevice.subscribedDevice.owner, QUser.user)
                .where(QUser.user.eq(user).and(QSubscribedDevice.subscribedDevice
                        .subscriptionKey.eq(subscriptionKey))).fetchCount() > 0;
    }

    public void deleteSubscribedDevice(String subscriptionKey) {
        new JPADeleteClause(entityManager, QSubscribedDevice.subscribedDevice)
                .where(QSubscribedDevice.subscribedDevice.subscriptionKey.eq(subscriptionKey))
                .execute();
    }

}
