package neo.dmcs.repository;

import neo.dmcs.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 14.01.2017.
 */
@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

    Field findByName(String name);
}
