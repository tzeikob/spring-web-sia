package com.app.db;

import com.app.model.AbstractEntity;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractRepository<T extends AbstractEntity> implements Serializable {

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> clazz;

    public AbstractRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> select() {
        List<T> list = (List<T>) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return list;
    }

    public T find(long id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    public void save(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
