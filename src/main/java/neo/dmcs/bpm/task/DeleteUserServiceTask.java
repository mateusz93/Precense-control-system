package neo.dmcs.bpm.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.Token;
import neo.dmcs.model.User;
import neo.dmcs.repository.TokenRepository;
import neo.dmcs.repository.UserRepository;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Mateusz Wieczorek on 10.06.2017.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserServiceTask implements JavaDelegate {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    //@Transactional
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delete incorrect user data from database");
        User user = (User) execution.getVariable("user");
        Token token = tokenRepository.findByUser(user);
        Query query = em.createNativeQuery("DELETE FROM Token WHERE ID = :tokenId");
        query.setParameter("tokenId", token.getId());
        query.executeUpdate();
        Query query1 = em.createNativeQuery("DELETE FROM User WHERE ID = :userId");
        query1.setParameter("userId", user.getId());
        query1.executeUpdate();
//        tokenRepository.delete(token);
//        userRepository.delete(user);
    }
}
