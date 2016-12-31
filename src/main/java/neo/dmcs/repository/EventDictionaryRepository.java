package neo.dmcs.repository;

import neo.dmcs.model.EventDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDictionaryRepository extends JpaRepository<EventDictionary, Integer> {

    EventDictionary findByName(String name);
}
