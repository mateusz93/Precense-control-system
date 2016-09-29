package neo.dmcs.dao;

import neo.dmcs.model.SMSTemplate;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 9/29/16.
 */
public interface SMSTemplateDao extends GenericDao<SMSTemplateDao, Integer> {

    SMSTemplate findByName(String name);
    List<SMSTemplate> findAll();
}
