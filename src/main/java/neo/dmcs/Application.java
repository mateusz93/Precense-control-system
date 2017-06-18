package neo.dmcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author Mateusz Wieczorek on 30.12.2016.
 */
@EnableJpaRepositories("neo.dmcs.repository")
@Configuration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.RestApiAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class})
@ComponentScan({"neo.dmcs"})
@EntityScan("neo.dmcs.model")
@EnableTransactionManagement
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
