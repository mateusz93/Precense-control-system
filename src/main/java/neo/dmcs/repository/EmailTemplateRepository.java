package neo.dmcs.repository;

import neo.dmcs.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Mateusz Wieczrek on 9/27/16.
 */
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer> {

    EmailTemplate findByName(String name);
}
