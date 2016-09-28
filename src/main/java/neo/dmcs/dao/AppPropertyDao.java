package neo.dmcs.dao;

import neo.dmcs.model.AppProperty;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
public interface AppPropertyDao extends GenericDao<AppProperty, Integer> {

    AppProperty findByName(String propertyName);
    List<AppProperty> findAll();
}
