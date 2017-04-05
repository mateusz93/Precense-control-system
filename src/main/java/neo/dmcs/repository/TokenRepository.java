package neo.dmcs.repository;

import neo.dmcs.model.Token;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mateusz Wieczorek on 05.04.2017.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String token);
    Token findByUser(User user);
}
