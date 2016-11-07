package neo.dmcs.repository;

import neo.dmcs.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByLogin(String login);
    User findByEmail(String email);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);

}
