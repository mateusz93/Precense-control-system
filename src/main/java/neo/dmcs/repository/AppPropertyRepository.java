package neo.dmcs.repository;

import neo.dmcs.model.AppProperty;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
public interface AppPropertyRepository extends CrudRepository<AppProperty, Integer> {

    AppProperty findByName(String name);

}
