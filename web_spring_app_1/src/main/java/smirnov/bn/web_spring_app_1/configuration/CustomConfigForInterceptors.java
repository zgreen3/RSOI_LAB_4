package smirnov.bn.web_spring_app_1.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import smirnov.bn.web_spring_app_1.controller.MainController;
import smirnov.bn.web_spring_app_1.interceptor.LogInCheckInterceptor;

@Configuration
public class CustomConfigForInterceptors extends WebMvcConfigurerAdapter {
    //https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm (:)
    @Autowired
    LogInCheckInterceptor logInCheckInterceptor;

    private static final Logger logger = LoggerFactory.getLogger(CustomConfigForInterceptors.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.info("CustomConfigForInterceptors web_spring_app_1 addInterceptors() - START");

        //Log In Check Interceptor applied to all URLs:
        registry.addInterceptor(logInCheckInterceptor).excludePathPatterns("/loginUser");
    }
}
