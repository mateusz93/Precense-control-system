package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.Role;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CacheValue;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author Mateusz Wieczorek, 02.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public boolean canAccessUser(CurrentUser currentUser, int userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId() == userId);
    }

    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username=%s was not found", username));
        }
        return new CurrentUser(user);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User getUserById(int id) {
        return repository.findOne(id);
    }
}
