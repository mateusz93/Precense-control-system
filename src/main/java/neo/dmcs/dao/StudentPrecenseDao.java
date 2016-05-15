package neo.dmcs.dao;

import neo.dmcs.model.StudentPrecense;
import java.util.List;

public interface StudentPrecenseDao extends GenericDao<StudentPrecense, Integer> {

    List<StudentPrecense> findByStudentId(int id);
    StudentPrecense findByCourseDateId(int id);
    List<StudentPrecense> findAll();
}
