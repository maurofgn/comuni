package org.mf.configuration;

//@Configuration
//@EnableTransactionManagement
public class HibernateConfiguration {


//	@Value("${jdbc.driverClassName:com.mysql.jdbc.Driver}") 
//	private String driverClassName;
//	
//	@Value("${jdbc.url:jdbc:mysql://localhost:3306/comuni}") 
//	private String url;
//	
//	@Value("${jdbc.username:root}") 
//	private String username;
//	
//	@Value("${jdbc.password:toor}") 
//	private String password;
//	
//	@Value("${hibernate.dialect:org.hibernate.dialect.MySQLDialect}") 
//	private String dialect;
//	
//	@Value("${hibernate.show_sql:false}") 
//	private String showSql;
//	
//	@Value("${hibernate.format_sql:false}") 
//	private String formatSql;
//	
//	@Value("${hibernate.hbm2ddl.auto:@null}") 
//	private String hbm2ddlAuto;	//validate | update | create | create-drop 
//	
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {    	
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(new String[] { "org.mf.bean.jpa" });
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//     }
//	
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//		
//        return dataSource;
//    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put(Environment.DIALECT, dialect);
//        properties.put(Environment.SHOW_SQL, showSql);
//        properties.put(Environment.FORMAT_SQL, formatSql);
//        
//        if (hbm2ddlAuto != null && !hbm2ddlAuto.isEmpty())
//        	properties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
//        
//        return properties;        
//    }
//    
//	@Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory s) {
//		HibernateTransactionManager txManager = new HibernateTransactionManager();
//		txManager.setSessionFactory(s);
//		return txManager;
//    }
}

