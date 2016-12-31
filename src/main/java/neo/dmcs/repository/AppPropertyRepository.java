package neo.dmcs.repository;

import neo.dmcs.model.AppProperty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
public interface AppPropertyRepository extends JpaRepository<AppProperty, Integer> {

    AppProperty findByName(String name);

}
