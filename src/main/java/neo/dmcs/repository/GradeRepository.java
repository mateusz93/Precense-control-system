package neo.dmcs.repository;

import neo.dmcs.model.Grade;
import neo.dmcs.model.Subject;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
public interface GradeRepository extends CrudRepository<Grade, Integer> {

    List<Grade> findBySubject(Subject subject);
    List<Grade> findByPreviousGrade(Grade previousGrade);
    List<Grade> findByValue(Integer value);

}
