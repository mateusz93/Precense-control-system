package neo.dmcs.repository;

import neo.dmcs.model.EmailTemplate;

/**
 * @Author Mateusz Wieczrek on 9/27/16.
 */
public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, Integer> {

    EmailTemplate findByName(String name);
}
