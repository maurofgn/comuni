package org.mf.configuration;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
//@ComponentScan(basePackages = { "org.mf.registration" })
@PropertySource(value = { "classpath:email.properties" , "classpath:application.properties"})
public class AppConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    // beans

	/**
	 * Add PropertySourcesPlaceholderConfigurer to make placeholder work.
	 * This method MUST be static
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setNullValue("@null");	//stringa con la quale si riconosce il null
		String activeProfile = System.getProperty("spring.profiles.active", "production");
		ClassPathResource resource = null;
		// choose different property files for different active profile
		if ("development".equalsIgnoreCase(activeProfile)) {
			resource = new ClassPathResource("development.properties");
		} else if ("test".equalsIgnoreCase(activeProfile)) {
			resource = new ClassPathResource("test.properties");
		} else {
			resource = new ClassPathResource("database.properties");
		}

		// load the property file
		propertySourcesPlaceholderConfigurer.setLocation(resource);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );

		return propertySourcesPlaceholderConfigurer;
	}

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();

        try {
            mailSenderImpl.setHost(env.getRequiredProperty("smtp.host"));
            mailSenderImpl.setPort(env.getRequiredProperty("smtp.port", Integer.class));
            mailSenderImpl.setProtocol(env.getRequiredProperty("smtp.protocol"));
            mailSenderImpl.setUsername(env.getRequiredProperty("smtp.username"));
            mailSenderImpl.setPassword(env.getRequiredProperty("smtp.password"));
        } catch (IllegalStateException ise) {
            LOGGER.error("Could not resolve email.properties.  See email.properties.sample");
            throw ise;
        }
        final Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

}