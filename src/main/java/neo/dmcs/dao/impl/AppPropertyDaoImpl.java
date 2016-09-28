package neo.dmcs.dao.impl;

import neo.dmcs.dao.AppPropertyDao;
import neo.dmcs.model.AppProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */

@Transactional
@Repository
public class AppPropertyDaoImpl extends GenericDaoImpl<AppProperty, Integer> implements AppPropertyDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public AppProperty findByName(String propertyName) {
        return em.createNamedQuery(AppProperty.FIND_BY_NAME, AppProperty.class).setParameter("name", propertyName).getSingleResult();
    }

    @Override
    public List<AppProperty> findAll() {
        return em.createNamedQuery(AppProperty.FIND_ALL, AppProperty.class).getResultList();
    }
}
