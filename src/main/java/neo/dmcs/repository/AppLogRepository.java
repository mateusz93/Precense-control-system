package neo.dmcs.repository;

import neo.dmcs.model.AppLog;
import neo.dmcs.model.EventDictionary;
import neo.dmcs.model.User;

import java.util.List;

public interface AppLogRepository extends CrudRepository<AppLog, Integer> {

    List<AppLog> findByEventDictionary(EventDictionary eventDictionary);
    List<AppLog> findByStudent(User student);
}
