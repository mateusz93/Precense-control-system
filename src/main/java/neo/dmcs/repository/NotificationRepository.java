package neo.dmcs.repository;

import neo.dmcs.model.Notification;
import neo.dmcs.model.User;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    Notification findByUser(User user);
}