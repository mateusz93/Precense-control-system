package neo.dmcs.dao.impl;


import neo.dmcs.dao.StudentCourseDao;
import neo.dmcs.model.StudentCourse;
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
public class StudentCourseDaoImpl extends GenericDaoImpl<StudentCourse, Integer> implements StudentCourseDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<StudentCourse> findByTeacherCourseId(int id) {
        return em.createNamedQuery(StudentCourse.FIND_BY_TEACHER_COURSE_ID, StudentCourse.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<StudentCourse> findByStudentId(int id) {
        return em.createNamedQuery(StudentCourse.FIND_BY_STUDENT_ID, StudentCourse.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<StudentCourse> findAll() {
        return em.createNamedQuery(StudentCourse.FIND_ALL, StudentCourse.class).getResultList();
    }

}
