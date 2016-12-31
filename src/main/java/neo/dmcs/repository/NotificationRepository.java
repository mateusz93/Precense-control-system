package neo.dmcs.repository;

import neo.dmcs.model.Notification;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Notification findByUser(User user);
}