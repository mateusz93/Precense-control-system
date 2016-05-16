package neo.dmcs.dao;

import neo.dmcs.model.AppLog;
import neo.dmcs.model.EventDictionary;
import neo.dmcs.model.User;

import java.util.List;

public interface AppLogDao extends GenericDao<AppLog, Integer> {

    List<AppLog> findByEventDictionary(EventDictionary eventDictionary);
    List<AppLog> findByStudent(User student);
    List<AppLog> findAll();
}
