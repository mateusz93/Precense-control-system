package neo.dmcs.dao.impl;


import neo.dmcs.dao.TeacherCourseDao;
import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
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
public class TeacherCourseDaoImpl extends GenericDaoImpl<TeacherCourse, Integer> implements TeacherCourseDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<TeacherCourse> findBySubject(Subject subject) {
        return em.createNamedQuery(TeacherCourse.FIND_BY_SUBJECT, TeacherCourse.class).setParameter("subject", subject).getResultList();
    }

    @Override
    public List<TeacherCourse> findByTeacher(User teacher) {
        return em.createNamedQuery(TeacherCourse.FIND_BY_TEACHER, TeacherCourse.class).setParameter("teacher", teacher).getResultList();
    }

    @Override
    public List<TeacherCourse> findAll() {
        return em.createNamedQuery(TeacherCourse.FIND_ALL, TeacherCourse.class).getResultList();
    }

}
