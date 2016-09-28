package neo.dmcs.dao.impl;

import neo.dmcs.dao.EmailTemplateDao;
import neo.dmcs.model.EmailTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author Mateusz Wieczrek on 9/27/16.
 */

@Transactional
@Repository
public class EmailTemplateDaoImpl extends GenericDaoImpl<EmailTemplate, Integer> implements EmailTemplateDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public EmailTemplate findByName(String name) {
        return em.createNamedQuery(EmailTemplate.FIND_BY_NAME, EmailTemplate.class).setParameter("name", name).getSingleResult();

    }

    @Override
    public List<EmailTemplate> findAll() {
        return em.createNamedQuery(EmailTemplate.FIND_ALL, EmailTemplate.class).getResultList();

    }
}
