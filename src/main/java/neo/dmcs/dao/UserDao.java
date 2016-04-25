package neo.dmcs.dao;

import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import java.util.List;

public interface UserDao extends GenericDao<User, Integer> {

    User findByContact(Contact contact);
    User findByUsername(String username);
    User findByFirstname(String firstname);
    User findByLastname(String lastname);
    List<User> findAll();
}
