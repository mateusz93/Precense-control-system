package neo.dmcs.repository;

import neo.dmcs.model.AppLog;
import neo.dmcs.model.EventDictionary;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppLogRepository extends JpaRepository<AppLog, Integer> {

    List<AppLog> findByEventDictionary(EventDictionary eventDictionary);
    List<AppLog> findByStudent(User student);
}
