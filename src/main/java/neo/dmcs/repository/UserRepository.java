package neo.dmcs.repository;

import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
    User findByEmail(String email);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);
}
