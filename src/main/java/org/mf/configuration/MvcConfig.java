package org.mf.configuration;

import java.util.List;
import java.util.Locale;

import org.mf.interceptor.ExecuteTimeInterceptor;
import org.mf.validation.EmailValidator;
import org.mf.validation.PasswordMatchesValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;


@Configuration
@ComponentScan(basePackages = {"org.mf.rest", "org.mf.web"})
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Value("${env.test:false}") 
	private boolean envTest;

    public MvcConfig() {
        super();
    }

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
		
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
	    viewResolver.setViewClass(TilesView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jspx");
		registry.viewResolver(viewResolver);
	}
	
	@Bean 
	public TilesConfigurer tilesConfigurer() {
//		<bean class="org.springframework.web.servlet.view.tiles2.TilesConfigurer" id="tilesConfigurer">
//			<property name="definitions">
//				<list>
//					<value>/WEB-INF/layouts/layouts.xml</value>
//					<value>/WEB-INF/views/**/views.xml</value>
//				</list>
//			</property>
//		</bean>		
	    TilesConfigurer tilesConfigurer = new TilesConfigurer();
	    tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/layouts/layouts.xml", "/WEB-INF/views/**/views.xml"});
    	tilesConfigurer.setCheckRefresh(envTest);
	    return tilesConfigurer;
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
	    resolver.setFallbackPageable(new PageRequest(0, 10));
	    argumentResolvers.add(resolver);
	}

	/**
	 * static view for rendering without the need for an explicit controller.
	 * 
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);

	    registry.addViewController("/").setViewName("index");
//        registry.addViewController("/").setViewName("forward:/login");
//        registry.addViewController("/login");
//        registry.addViewController("/registration.html");
//        registry.addViewController("/logout.html");
//        registry.addViewController("/homepage.html");
//        registry.addViewController("/expiredAccount.html");
//        registry.addViewController("/badUser.html");
//        registry.addViewController("/emailError.html");
//        registry.addViewController("/home.html");
//        registry.addViewController("/invalidSession.html");
//        registry.addViewController("/console.html");
//        registry.addViewController("/admin.html");
//        registry.addViewController("/successRegister.html");
//        registry.addViewController("/forgetPassword.html");
//        registry.addViewController("/updatePassword.html");
//        registry.addViewController("/changePassword.html");
	    registry.addViewController("/uncaughtException");	//.setViewName("uncaughtException");
	    registry.addViewController("/resourceNotFound");	//.setViewName("resourceNotFound");
	    registry.addViewController("/dataAccessFailure");	//.setViewName("dataAccessFailure");
	    registry.addViewController("/accessDenied");
    }
    
    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/, classpath:/META-INF/web-resources/");
    }
    
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");	//LocaleChangeInterceptor.DEFAULT_PARAM_NAME;	//locale
        registry.addInterceptor(localeChangeInterceptor);
        
        ExecuteTimeInterceptor timeInterceptor = new ExecuteTimeInterceptor();
        registry.addInterceptor(timeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ITALIAN);
        return cookieLocaleResolver;
    }

     @Bean
     public MessageSource messageSource() {
	     final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	 	 messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
	     messageSource.setUseCodeAsDefaultMessage(true);		//default is false
	     messageSource.setDefaultEncoding("UTF-8");
	     messageSource.setCacheSeconds(3000);
	     return messageSource;
     }
     
    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
    
}