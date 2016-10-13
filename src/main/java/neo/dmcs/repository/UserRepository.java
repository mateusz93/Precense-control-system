package neo.dmcs.repository;

import neo.dmcs.model.Contact;
import neo.dmcs.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByContact(Contact contact);
    User findByLogin(String login);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);

}
