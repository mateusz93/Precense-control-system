package neo.dmcs.repository;

import neo.dmcs.model.SMSTemplate;

/**
 * @Author Mateusz Wieczorek on 9/29/16.
 */
public interface SMSTemplateRepository extends CrudRepository<SMSTemplate, Integer> {

    SMSTemplate findByName(String name);

}
