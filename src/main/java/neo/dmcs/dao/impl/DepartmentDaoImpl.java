package neo.dmcs.dao.impl;


import neo.dmcs.dao.DepartmentDao;
import neo.dmcs.model.Department;
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
public class DepartmentDaoImpl extends GenericDaoImpl<Department, Integer> implements DepartmentDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public Department findByName(String name) {
        return em.createNamedQuery(Department.FIND_BY_NAME, Department.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Department> findAll() {
        return em.createNamedQuery(Department.FIND_ALL, Department.class).getResultList();
    }

}
