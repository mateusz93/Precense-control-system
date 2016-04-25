package neo.dmcs.dao.impl;

import neo.dmcs.dao.StudentPrecenseDao;
import neo.dmcs.model.StudentPrecense;
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
public class StudentPrecenseDaoImpl extends GenericDaoImpl<StudentPrecense, Integer> implements StudentPrecenseDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<StudentPrecense> findByStudentId(int id) {
        return em.createNamedQuery(StudentPrecense.FIND_BY_STUDENT_ID, StudentPrecense.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<StudentPrecense> findByCourseDateId(int id) {
        return em.createNamedQuery(StudentPrecense.FIND_BY_COURSE_DATA_ID, StudentPrecense.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<StudentPrecense> findAll() {
        return em.createNamedQuery(StudentPrecense.FIND_ALL, StudentPrecense.class).getResultList();
    }

}
