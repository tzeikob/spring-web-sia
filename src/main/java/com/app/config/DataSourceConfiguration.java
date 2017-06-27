package com.app.config;

import com.mongodb.MongoClient;
import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(
        basePackages = "com",
        excludeFilters = {
            @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
@EnableMongoRepositories(basePackages = "com.app.db")
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
public class DataSourceConfiguration {

    private Logger logger = Logger.getLogger(DataSourceConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    @Profile("memory")
    public SessionFactory embeddedSessionFactory() {
        logger.info("Application is running in the memory profile");

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase dataSource = builder.setType(EmbeddedDatabaseType.HSQL)
                .addScript("create-db.sql")
                .addScript("insert-db.sql")
                .build();

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.app.model");

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    @Profile("development")
    public SessionFactory developmentSessionFactory() {
        logger.info("Application is running in the development profile");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("development.datasource.driver.class.name"));
        dataSource.setUrl(environment.getProperty("development.datasource.url"));
        dataSource.setUsername(environment.getProperty("development.datasource.username"));
        dataSource.setPassword(environment.getProperty("development.datasource.password"));
        dataSource.setMinIdle(environment.getProperty("development.datasource.min.idle", Integer.class));
        dataSource.setMaxIdle(environment.getProperty("development.datasource.max.idle", Integer.class));
        dataSource.setMaxTotal(environment.getProperty("development.datasource.max.active", Integer.class));

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", environment.getProperty("development.datasource.dialect"));

        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.app.model");

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    @Profile("production")
    public SessionFactory productionSessionFactory() throws NamingException {
        logger.info("Application is running in the production profile");

        JndiTemplate jndiTemplate = new JndiTemplate();
        String jndiName = environment.getProperty("production.datasource.jndi.name");
        DataSource dataSource = (DataSource) jndiTemplate.lookup(jndiName);

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.dialect", environment.getProperty("production.datasource.dialect"));

        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.app.model");

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

        return transactionManager;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        String host = environment.getProperty("development.mongo.host");
        int port = environment.getProperty("development.mongo.port", Integer.class);
        String name = environment.getProperty("development.mongo.db.name");

        MongoClient mongoClient = new MongoClient(host, port);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, name);

        return new MongoTemplate(mongoDbFactory);
    }
}
