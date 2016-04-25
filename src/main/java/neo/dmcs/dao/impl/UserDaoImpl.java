package neo.dmcs.dao.impl;

import neo.dmcs.dao.UserDao;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mateusz Wieczorek
 */

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public User findByContact(Contact contact) {
        return em.createNamedQuery(User.FIND_BY_CONTACT, User.class).setParameter("contact", contact).getSingleResult();
    }

    @Override
    public User findByUsername(String username) {
        return em.createNamedQuery(User.FIND_BY_USERNAME, User.class).setParameter("login", username).getSingleResult();
    }

    @Override
    public User findByFirstname(String firstname) {
        return em.createNamedQuery(User.FIND_BY_FIRSTNAME, User.class).setParameter("firstname", firstname).getSingleResult();
    }

    @Override
    public User findByLastname(String lastname) {
        return em.createNamedQuery(User.FIND_BY_LASTNAME, User.class).setParameter("lastname", lastname).getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery(User.FIND_ALL, User.class).getResultList();
    }

}
