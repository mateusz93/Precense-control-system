package neo.dmcs.dao;

import neo.dmcs.model.EmailTemplate;

import java.util.List;

/**
 * @Author Mateusz Wieczrek on 9/27/16.
 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate, Integer> {

    EmailTemplate findByName(String name);
    List<EmailTemplate> findAll();
}
