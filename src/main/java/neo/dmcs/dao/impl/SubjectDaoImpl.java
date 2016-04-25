package neo.dmcs.dao.impl;


import neo.dmcs.dao.SubjectDao;
import neo.dmcs.model.Subject;
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
public class SubjectDaoImpl extends GenericDaoImpl<Subject, Integer> implements SubjectDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<Subject> findByName(String name) {
        return em.createNamedQuery(Subject.FIND_BY_NAME, Subject.class).setParameter("name", name).getResultList();
    }

    @Override
    public List<Subject> findAll() {
        return em.createNamedQuery(Subject.FIND_ALL, Subject.class).getResultList();
    }

}
