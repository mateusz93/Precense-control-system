package neo.dmcs.dao.impl;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mateusz Wieczorek
 */

@Transactional
@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Integer> implements ContactDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public Contact findByEmail(String email) {
        return em.createNamedQuery(Contact.FIND_BY_EMAIL, Contact.class).setParameter("email", email).getSingleResult();
    }

    @Override
    public List<Contact> findByGroup(String group) {
        return em.createNamedQuery(Contact.FIND_BY_GROUP, Contact.class).setParameter("group", group).getResultList();
    }

    @Override
    public Contact findByPhone(String phone) {
        return em.createNamedQuery(Contact.FIND_BY_PHONE, Contact.class).setParameter("phone", phone).getSingleResult();
    }

    @Override
    public List<Contact> findByCity(String city) {
        return em.createNamedQuery(Contact.FIND_BY_CITY, Contact.class).setParameter("city", city).getResultList();
    }

    @Override
    public List<Contact> findAll() {
        return em.createNamedQuery(Contact.FIND_ALL, Contact.class).getResultList();
    }

}
