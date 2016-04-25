package neo.dmcs.dao.impl;

import neo.dmcs.dao.AppLogDao;
import neo.dmcs.model.AppLog;
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
    public List<AppLog> findByEventDictionaryId(int id) {
        return em.createNamedQuery(AppLog.FIND_BY_EVENT_DICTIONARY_ID, AppLog.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<AppLog> findByTeacherId(int id) {
        return em.createNamedQuery(AppLog.FIND_BY_TEACHER_ID, AppLog.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<AppLog> findByStudentId(int id) {
        return em.createNamedQuery(AppLog.FIND_BY_STUDENT_ID, AppLog.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<AppLog> findAll() {
        return em.createNamedQuery(AppLog.FIND_ALL, AppLog.class).getResultList();
    }

}
