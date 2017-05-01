package ua.nure.providence.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig{

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.database.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(
                env.getProperty("entitymanager.packagesToScan"));
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        Properties additionalProperties = new Properties();
        additionalProperties.put(
                "hibernate.dialect",
                env.getProperty("spring.jpa.database-platform"));
        additionalProperties.put(
                "hibernate.show_sql",
                env.getProperty("spring.jpa.show-sql"));
        additionalProperties.put(
                "hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto"));
        entityManagerFactory.setJpaProperties(additionalProperties);
        entityManagerFactory.getJpaPropertyMap().put("jadira.usertype.autoRegisterUserTypes", "true");
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory.getObject());
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxIdle(Integer.valueOf(env.getProperty("spring.redis.pool.max-idle")));
        JedisConnectionFactory jedisFactory = new JedisConnectionFactory(jedisConfig);
        jedisFactory.setHostName(env.getProperty("spring.redis.host"));
        jedisFactory.setPort(Integer.valueOf(env.getProperty("spring.redis.port")));
        return jedisFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new StringRedisTemplate(jedisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public SpringLiquibase liquibase() {
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDataSource(getDataSource(liquibaseProperties));
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(true);
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setChangeLog("classpath:mainchangelog.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    private DataSource getDataSource(LiquibaseProperties liquibaseProperties) {
        if (liquibaseProperties.getUrl() == null) {
            return this.dataSource;
        }
        return DataSourceBuilder.create().url(liquibaseProperties.getUrl())
                .username(liquibaseProperties.getUser())
                .password(liquibaseProperties.getPassword()).build();
    }
}
