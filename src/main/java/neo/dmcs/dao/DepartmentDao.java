package neo.dmcs.dao;

import neo.dmcs.model.Department;
import java.util.List;

public interface DepartmentDao extends GenericDao<Department, Integer> {

    Department findByName(String name);
    List<Department> findAll();
}
