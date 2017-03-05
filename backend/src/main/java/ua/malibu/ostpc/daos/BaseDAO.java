package ua.malibu.ostpc.daos;

import org.springframework.beans.factory.annotation.Autowired;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.EntityManager;

public abstract class BaseDAO<T extends BaseEntity> implements IDAO<T>{

    @Autowired
    protected EntityManager entityManager;

    @Override
    public void insert(T o) {
        entityManager.persist(o);
    }

    @Override
    public void update(T o) {
        entityManager.merge(o);
    }

    @Override
    public void delete(T o) {
        entityManager.detach(o);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
