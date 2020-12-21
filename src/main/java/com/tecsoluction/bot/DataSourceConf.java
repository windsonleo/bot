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
        dataSource.setUrl("jdbc:postgresql://ec2-52-206-44-27.compute-1.amazonaws.com:5432/dc66mrokcu9vi7");
        dataSource.setUsername("mqfxnxxnfdibrw");        
        dataSource.setPassword("f2ee575663c572081d8413865dc4940474fcc7a4908e232c81d3bab71b8a5949");

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
