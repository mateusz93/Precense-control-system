package neo.dmcs.dao.impl;

import neo.dmcs.dao.SMSTemplateDao;
import neo.dmcs.model.SMSTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author Mateusz Wieczorek on 9/29/16.
 */

@Transactional
@Repository
public class SMSTemplateDaoImpl extends GenericDaoImpl<SMSTemplateDao, Integer> implements SMSTemplateDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public SMSTemplate findByName(String name) {
        return em.createNamedQuery(SMSTemplate.FIND_BY_NAME, SMSTemplate.class).setParameter("name", name).getSingleResult();

    }

    @Override
    public List<SMSTemplate> findAll() {
        return em.createNamedQuery(SMSTemplate.FIND_ALL, SMSTemplate.class).getResultList();

    }
}
