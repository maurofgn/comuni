package org.mf.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.mf.business.service" })
//@ComponentScan({ "org.mf.business.service, org.mf.task"})	//service and scheduled task
//@EnableScheduling											//need to schedule
public class ServiceConfig {
    //
}
