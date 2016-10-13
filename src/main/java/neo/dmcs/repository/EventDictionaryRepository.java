package neo.dmcs.repository;

import neo.dmcs.model.EventDictionary;

public interface EventDictionaryRepository extends CrudRepository<EventDictionary, Integer> {

    EventDictionary findByName(String name);
}
