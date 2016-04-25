package neo.dmcs.dao.impl;


import neo.dmcs.dao.CourseDateDao;
import neo.dmcs.model.CourseDate;
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
public class CourseDateDaoImpl extends GenericDaoImpl<CourseDate, Integer> implements CourseDateDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<CourseDate> findByCourseId(int id) {
        return em.createNamedQuery(CourseDate.FIND_BY_COURSE_ID, CourseDate.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<CourseDate> findAll() {
        return em.createNamedQuery(CourseDate.FIND_ALL, CourseDate.class).getResultList();
    }

}
