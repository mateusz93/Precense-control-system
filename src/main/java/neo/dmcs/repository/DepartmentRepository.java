package neo.dmcs.repository;

import neo.dmcs.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    Department findByName(String name);

}
