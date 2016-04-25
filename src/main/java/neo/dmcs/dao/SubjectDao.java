package neo.dmcs.dao;

import neo.dmcs.model.Subject;
import java.util.List;

public interface SubjectDao extends GenericDao<Subject, Integer> {

    List<Subject> findByName(String name);
    List<Subject> findAll();
}
