package neo.dmcs.dao;

import neo.dmcs.model.AppLog;

import java.util.List;

public interface AppLogDao extends GenericDao<AppLog, Integer> {

    List<AppLog> findByEventDictionaryId(int id);
    List<AppLog> findByTeacherId(int id);
    List<AppLog> findByStudentId(int id);
    List<AppLog> findAll();
}
