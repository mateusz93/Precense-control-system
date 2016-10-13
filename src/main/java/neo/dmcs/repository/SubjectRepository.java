package neo.dmcs.repository;

import neo.dmcs.model.Subject;
import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    List<Subject> findByName(String name);
}
