package neo.dmcs.configuration;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@Configuration
public class BpmConfiguration {
//
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource ds = new SimpleDriverDataSource();
//        ds.setDriverClass(com.mysql.cj.jdbc.Driver.class);
//        ds.setSchema("data");
//        ds.setUrl("jdbc:mysql://localhost:3306/data?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false");
//        ds.setUsername("root");
//        ds.setPassword("root");
//        return ds;
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        return transactionManager;
//    }
//
//    @Bean(name="processEngineFactoryBean")
//    public ProcessEngineFactoryBean processEngineFactoryBean() {
//        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
//        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
//        return factoryBean;
//    }
//
//    @Bean(name="processEngine")
//    public ProcessEngine processEngine() {
//        // Safe to call the getObject() on the @Bean annotated processEngineFactoryBean(), will be
//        // the fully initialized object instanced from the factory and will NOT be created more than once
//        try {
//            return processEngineFactoryBean().getObject();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Bean(name="processEngineConfiguration")
//    public ProcessEngineConfigurationImpl processEngineConfiguration() {
//        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
//        processEngineConfiguration.setDataSource(dataSource());
//        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        processEngineConfiguration.setTransactionManager(annotationDrivenTransactionManager());
//        processEngineConfiguration.setJobExecutorActivate(false);
//        processEngineConfiguration.setAsyncExecutorEnabled(true);
//        processEngineConfiguration.setAsyncExecutorActivate(false);
//        processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
//        return processEngineConfiguration;
//    }
}
