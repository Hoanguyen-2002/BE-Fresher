package com.lg.fresher.lgcommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
@EnableJpaRepositories(basePackages = "com.lg.fresher.lgcommerce.repository")
//@MapperScan(
//    basePackages = {"com.lgcns.esg.mapper"},
//    annotationClass = Mapper.class)
@RequiredArgsConstructor
public class JpaConfiguration {

  private final JpaProperties jpaPropertiesBuilder;
  private final HibernateProperties hibernateProperties;
  private final Environment env;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.lg.fresher.lgcommerce.entity");
    em.setJpaVendorAdapter(jpaVendorAdapter());
    em.setJpaPropertyMap(jpaProperties());
    em.setJpaProperties(additionalProperties());
    return em;
  }

  @Bean
  public HibernateJpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    properties.setProperty(
        "hibernate.current_session_context_class",
        env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
    properties.setProperty(
        "hibernate.jdbc.lob.non_contextual_creation",
        env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
    properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
    properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
    properties.setProperty(
        "hibernate.naming.physical-strategy", env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
    return properties;
  }

  protected Map<String, String> jpaProperties() {
    return jpaPropertiesBuilder.getProperties();
  }
}
