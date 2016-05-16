package neo.dmcs.dao.impl;

import neo.dmcs.dao.StudentPrecenseDao;
import neo.dmcs.model.CourseDate;
import neo.dmcs.model.StudentPrecense;
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
public class StudentPrecenseDaoImpl extends GenericDaoImpl<StudentPrecense, Integer> implements StudentPrecenseDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<StudentPrecense> findByStudent(User student) {
        return em.createNamedQuery(StudentPrecense.FIND_BY_STUDENT, StudentPrecense.class).setParameter("student", student).getResultList();
    }

    @Override
    public StudentPrecense findByCourseDate(CourseDate courseDate) {
        return em.createNamedQuery(StudentPrecense.FIND_BY_COURSE_DATE, StudentPrecense.class).setParameter("courseDate", courseDate).getSingleResult();
    }

    @Override
    public List<StudentPrecense> findAll() {
        return em.createNamedQuery(StudentPrecense.FIND_ALL, StudentPrecense.class).getResultList();
    }

}
