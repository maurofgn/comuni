package org.mf.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * This class is same as real JPAConfiguration class in sources.
 * Only difference is that method dataSource & hibernateProperties 
 * implementations are specific to Hibernate working with H2 database.
 */

@Configuration
@EnableJpaRepositories(basePackages="org.mf.data.repository", entityManagerFactoryRef="entityManagerFactory")
@EnableTransactionManagement
public class JPAConfTest {
	
	
	@Value("${database.driverClassName:org.h2.Driver}")
	private String driverClassName;

	@Value("${database.url:jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE}") 
	private String url;
	
	@Value("${database.username:sa}") 
	private String username;
	
	@Value("${database.password:@null}") 
	private String password;
	
	@Value("${database.databasePlatform:org.hibernate.dialect.H2Dialect}") 
	private String dialect;

	@Value("${database.showSql:tre}") 
	private String showSql;
	
	@Value("${database.generateDdl:false}") 
	private String generateDdl;
	
	@Value("${database.database:H2}") 	//see: enum  org.springframework.orm.jpa.vendor.Database
	private String database;
	
	@Value("${hibernate.format_sql:false}") 
	private String formatSql;
	
	@Value("${hibernate.hbm2ddl.auto:@null}") 
	private String hbm2ddlAuto;	//validate | update | create | create-drop

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
    	
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "org.mf.bean.jpa" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    
	  private Properties hibernateProperties() {
	      Properties properties = new Properties();
	      properties.put(Environment.DIALECT, dialect);
	      properties.put(Environment.SHOW_SQL, showSql);
	      properties.put(Environment.FORMAT_SQL, formatSql);
//	      properties.put(Environment.DEFAULT_SCHEMA, "torneo");
	      
	      if (hbm2ddlAuto != null && !hbm2ddlAuto.isEmpty())
	      	properties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
	      
	      return properties;
	  }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    	
    	LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "org.mf.bean.jpa" });
        
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        
        Database dbType = Database.DEFAULT;
    	for( Database db : Database.values() ) {
    		if (database.equalsIgnoreCase(db.toString())) {
    			dbType = db;
    			break;
    		}
        }

        jpaVendorAdapter.setDatabase(dbType);
        jpaVendorAdapter.setDatabasePlatform(dialect);
        jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setGenerateDdl(Boolean.valueOf(generateDdl));
        sessionFactory.setJpaVendorAdapter(jpaVendorAdapter);
        
        Properties jpaProperties = new Properties();
        if (Boolean.getBoolean(formatSql.toLowerCase()))
        	jpaProperties.put("hibernate.format_sql", formatSql);
        
        if (hbm2ddlAuto != null)
        	jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        
       	sessionFactory.setJpaProperties(jpaProperties);
        
        return sessionFactory;
     }
    
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		
		if (driverClassName == null) {
	        EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	        return datasource;
		} else {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			return dataSource;
		}
	}    

	@Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
    }
	
}

