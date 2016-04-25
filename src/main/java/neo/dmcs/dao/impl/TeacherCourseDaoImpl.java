package neo.dmcs.dao.impl;


import neo.dmcs.dao.TeacherCourseDao;
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
    public List<TeacherCourse> findBySubjectId(int id) {
        return em.createNamedQuery(TeacherCourse.FIND_BY_SUBJECT_ID, TeacherCourse.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<TeacherCourse> findByTeacherId(int id) {
        return em.createNamedQuery(TeacherCourse.FIND_BY_TEACHER_ID, TeacherCourse.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<TeacherCourse> findAll() {
        return em.createNamedQuery(TeacherCourse.FIND_ALL, TeacherCourse.class).getResultList();
    }

}
