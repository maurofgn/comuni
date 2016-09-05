package org.mf.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages="org.mf.data.repository", entityManagerFactoryRef="entityManagerFactory")
@EnableTransactionManagement
public class JPAConfiguration {


	@Value("${database.driverClassName:com.mysql.jdbc.Driver}") 
	private String driverClassName;

	@Value("${database.url:jdbc:mysql://localhost:3306/comuni}") 
	private String url;
	
	@Value("${database.username:root}") 
	private String username;
	
	@Value("${database.password:toor}") 
	private String password;
	
	@Value("${database.databasePlatform:org.hibernate.dialect.MySQLDialect}") 
	private String dialect;

	@Value("${database.showSql:false}") 
	private String showSql;
	
	@Value("${database.generateDdl:false}") 
	private String generateDdl;
	
	@Value("${database.database:MYSQL}") 	//see: enum org.springframework.orm.jpa.vendor.Database
	private String database;
	
	@Value("${hibernate.format_sql:false}") 
	private String formatSql;
	
	@Value("${hibernate.hbm2ddl.auto:@null}") 
	private String hbm2ddlAuto;	//validate | update | create | create-drop
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		<bean id="entityManagerFactory"
//		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
//		<property name="dataSource" ref="dataSource" />
//		<property name="jpaVendorAdapter">
//			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
//				<property name="database" value="${database.database}"/>
//				<property name="databasePlatform" value="${database.databasePlatform}"/>
//				<property name="showSql" value="${database.showSql}"/>
//				<property name="generateDdl" value="${database.generateDdl}"/>
//			</bean>
//		</property>
//		<property name="packagesToScan" value="org.mf.bean"/>
//	</bean>
    	
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

//    /**
//     * propriet� specifiche per hibernate
//     * @return Properties
//     */
//    private Properties getHibernateProp() {
//
//        Properties jpaProperties = new Properties();
//        if (Boolean.getBoolean(formatSql.toLowerCase()))
//        	jpaProperties.put("hibernate.format_sql", formatSql);
//        
//        if (hbm2ddlAuto != null)
//        	jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
//        
//        return jpaProperties;
//    }
//    
//    /**
//     * 
//     * @return enum value from database field
//     */
//    private Database getDatabaseType() {
//    	
//    	for( Database db : Database.values() ) {
//    		if (database.equalsIgnoreCase(db.toString()))
//    			return db;
//        }
//    	return Database.DEFAULT;
//    }
	
    @Bean
    public DataSource dataSource() {
    	
//    	<bean class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close" id="dataSource">
//			<property name="driverClassName" value="${database.driverClassName}" />
//			<property name="url" value="${database.url}" />
//			<property name="username" value="${database.username}" />
//			<property name="password" value="${database.password}" />
    	
//			<property name="testOnBorrow" value="true" />
//			<property name="testOnReturn" value="true" />
//			<property name="testWhileIdle" value="true" />
//			<property name="timeBetweenEvictionRunsMillis" value="1800000" />
//			<property name="numTestsPerEvictionRun" value="3" />
//			<property name="minEvictableIdleTimeMillis" value="1800000" />
//			<property name="initialSize" value="1" />
//			<property name="maxActive" value="50" />
//			<property name="maxIdle" value="20" />
//		</bean>
    	
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000);
		dataSource.setNumTestsPerEvictionRun(3);
		dataSource.setMinEvictableIdleTimeMillis(1800000);
		dataSource.setInitialSize(1);
		dataSource.setMaxActive(50);
		dataSource.setMaxIdle(20);
    	
        return dataSource;
    }

	@Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//	<bean id="transactionManager"
//		class="org.springframework.orm.jpa.JpaTransactionManager">
//		<property name="entityManagerFactory" ref="entityManagerFactory"/>
//	</bean>
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
    }
	
}
