package neo.dmcs.dao.impl;


import neo.dmcs.dao.StudentCourseDao;
import neo.dmcs.model.StudentCourse;
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
public class StudentCourseDaoImpl extends GenericDaoImpl<StudentCourse, Integer> implements StudentCourseDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<StudentCourse> findByTeacherCourse(TeacherCourse teacherCourse) {
        return em.createNamedQuery(StudentCourse.FIND_BY_TEACHER_COURSE, StudentCourse.class).setParameter("teacherCourse", teacherCourse).getResultList();
    }

    @Override
    public List<StudentCourse> findByStudent(User student) {
        return em.createNamedQuery(StudentCourse.FIND_BY_STUDENT, StudentCourse.class).setParameter("student", student).getResultList();
    }

    @Override
    public StudentCourse findByStudentAndTeacherCourse(User student, TeacherCourse teacherCourse) {
        return em.createNamedQuery(StudentCourse.FIND_BY_STUDENT_AND_TEACHER_COURSE, StudentCourse.class).setParameter("student", student).setParameter("teacherCourse", teacherCourse).getSingleResult();
    }

    @Override
    public List<StudentCourse> findAll() {
        return em.createNamedQuery(StudentCourse.FIND_ALL, StudentCourse.class).getResultList();
    }

}
