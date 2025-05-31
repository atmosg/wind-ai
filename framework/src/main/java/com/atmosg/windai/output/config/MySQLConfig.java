package com.atmosg.windai.output.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
  basePackages = "com.atmosg.windai.output.mysql",
  entityManagerFactoryRef = "mysqlEntityManager",
  transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLConfig {

  @Primary
  @Bean
  public PlatformTransactionManager mysqlTransactionManager() {
    var transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());

    return transactionManager;
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
    var em = new LocalContainerEntityManagerFactoryBean();

    em.setDataSource(mySqlDataSource());
    em.setPackagesToScan(new String[]{"com.atmosg.windai.output.mysql.data"});
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "update");

    em.setJpaPropertyMap(properties);

    return em;
  }
  
  @Bean
  public DataSource mySqlDataSource() {
    return DataSourceBuilder.create()
        .url("jdbc:mysql://localhost:3306/wind_ai?serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
        .username("root")
        .password("dnfl653538@")
        .driverClassName("com.mysql.cj.jdbc.Driver")
        .build();
  }

}
