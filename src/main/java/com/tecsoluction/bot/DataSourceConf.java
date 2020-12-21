package com.tecsoluction.bot;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;




@Configuration
public class DataSourceConf {
	
	
	@Bean
    public DataSource dataSource() {
           
//        local
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/bot");
//        dataSource.setUsername("postgres");        
//        dataSource.setPassword("");
		
		//heroku
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ec2-52-44-139-108.compute-1.amazonaws.com:5432/d4fcv1t5c4n5to");
        dataSource.setUsername("evyyqsvwvpwabv");        
        dataSource.setPassword("48e705de3fc33bd401dc52b83a4483ab8cc30603225f93e84bd5c373b4569e36");

    	return dataSource;
    }
	
	
	
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){


        LocalContainerEntityManagerFactoryBean lcemfb
                = new LocalContainerEntityManagerFactoryBean();

        lcemfb.setDataSource(dataSource());
        lcemfb.setPackagesToScan(new String[] {"com.tecsoluction.bot"});

        lcemfb.setPersistenceUnitName("PU-BOT");

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);
        va.setDatabase(Database.POSTGRESQL);

        va.setGenerateDdl(true);
        va.setShowSql(true);
        va.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        Properties ps = new Properties();
        ps.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        ps.put("spring.jpa.hibernate.ddl-auto", "create");
		ps.put("useSSL","false");
		ps.put("spring.thymeleaf.encoding","UTF-8");
		ps.put("spring.jpa.properties.hibernate.format_sql","true");
		ps.put("spring.datasource.validationQuery","SELECT 1");
		ps.put("spring.thymeleaf.cache","false");
		ps.put("security.basic.enabled","false");
		ps.put("spring.thymeleaf.mode","LEGACYHTML5");

//		ps.put("spring.social.facebook.appId","1047331868960513");
//		ps.put("spring.social.facebook.appSecret","88b9a5181187e858176ae7f09a4bb6b4");
		

		
        lcemfb.setJpaProperties(ps);

        lcemfb.afterPropertiesSet();

        return lcemfb;

    }
    
    @Bean
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager tm = new JpaTransactionManager();

        tm.setEntityManagerFactory(
                this.entityManagerFactory().getObject() );

        return tm;

    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    
    


}
