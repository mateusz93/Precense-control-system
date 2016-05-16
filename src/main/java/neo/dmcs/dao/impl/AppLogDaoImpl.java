package neo.dmcs.dao.impl;

import neo.dmcs.dao.AppLogDao;
import neo.dmcs.model.AppLog;
import neo.dmcs.model.EventDictionary;
import neo.dmcs.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mateusz Wieczorek
 */

@Transactional
@Repository
public class AppLogDaoImpl extends GenericDaoImpl<AppLog, Integer> implements AppLogDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<AppLog> findByEventDictionary(EventDictionary eventDictionary) {
        return em.createNamedQuery(AppLog.FIND_BY_EVENT_DICTIONARY, AppLog.class).setParameter("eventDictionary", eventDictionary).getResultList();
    }

    @Override
    public List<AppLog> findByStudent(User user) {
        return em.createNamedQuery(AppLog.FIND_BY_STUDENT, AppLog.class).setParameter("user", user).getResultList();
    }

    @Override
    public List<AppLog> findAll() {
        return em.createNamedQuery(AppLog.FIND_ALL, AppLog.class).getResultList();
    }

}
