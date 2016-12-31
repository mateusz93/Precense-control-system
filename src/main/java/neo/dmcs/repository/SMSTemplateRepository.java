package neo.dmcs.repository;

import neo.dmcs.model.SMSTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Mateusz Wieczorek on 9/29/16.
 */
public interface SMSTemplateRepository extends JpaRepository<SMSTemplate, Integer> {

    SMSTemplate findByName(String name);

}
