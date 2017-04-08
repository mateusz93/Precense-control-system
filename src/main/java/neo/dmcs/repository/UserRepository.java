package neo.dmcs.repository;

import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
    User findByEmail(String email);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);
    List<User> findByType(String type);
}
