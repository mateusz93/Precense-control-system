package neo.dmcs.dao.impl;


import neo.dmcs.dao.EventDictionaryDao;
import neo.dmcs.model.EventDictionary;
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
public class EventDictionaryDaoImpl extends GenericDaoImpl<EventDictionary, Integer>implements EventDictionaryDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public EventDictionary findByName(String name) {
        return em.createNamedQuery(EventDictionary.FIND_BY_NAME, EventDictionary.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<EventDictionary> findAll() {
        return em.createNamedQuery(EventDictionary.FIND_ALL, EventDictionary.class).getResultList();
    }

}
