package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.Role;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.security.CurrentUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Mateusz Wieczorek, 02.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {//implements UserDetailsService {

    private final UserRepository userRepository;

    public boolean canAccessUser(CurrentUser currentUser, int userId) {
        return currentUser != null && (currentUser.getRole() == Role.ADMIN || currentUser.getId() == userId);
    }

    //@Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username=%s was not found", username));
        }
        return new CurrentUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

}
