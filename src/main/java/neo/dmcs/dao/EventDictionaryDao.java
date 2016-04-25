package neo.dmcs.dao;

import neo.dmcs.model.EventDictionary;
import java.util.List;

public interface EventDictionaryDao extends GenericDao<EventDictionary, Integer> {

    EventDictionary findByName(String name);
    List<EventDictionary> findAll();
}
