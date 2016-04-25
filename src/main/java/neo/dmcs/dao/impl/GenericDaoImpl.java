package neo.dmcs.dao.impl;

import neo.dmcs.dao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @Author Mateusz Wieczorek, 16.04.16.
 */
@Transactional
@Repository
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> entityClass;

    @PersistenceContext(name = "data")
    private EntityManager em;

    public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void save(T object) {
        em.persist(object);
    }

    @Override
    public void update(T object) {
        em.merge(object);
    }

    @Override
    public void delete(T object) {
        em.remove(em.contains(object) ? object : em.merge(object));
    }

    @Override
    public T findById(PK id) {
        return em.find(entityClass, id);
    }

}
