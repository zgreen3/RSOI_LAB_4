package smirnov.bn.service_2.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import smirnov.bn.service_2.interceptor.TokenCheckInterceptor;

/*
@Configuration
public class CustomConfigForInterceptors extends WebMvcConfigurerAdapter {
    //https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm (:)
    @Autowired
    TokenCheckInterceptor tokenCheckInterceptor;

    private static final Logger logger = LoggerFactory.getLogger(CustomConfigForInterceptors.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.info("CustomConfigForInterceptors service_1 addInterceptors() - START");
//*/
//        registry.addInterceptor(tokenCheckInterceptor).excludePathPatterns("/**/get-token");
//    }
//}

//
//https://stackoverflow.com/questions/23349180/java-config-for-spring-interceptor-where-interceptor-is-using-autowired-spring-b (:)

@EnableWebMvc
@Configuration
public class CustomConfigForInterceptors extends WebMvcConfigurerAdapter {

    @Autowired
    TokenCheckInterceptor tokenCheckInterceptor;

//    @Bean
//    TokenCheckInterceptor tokenCheckInterceptor() {
//        return new TokenCheckInterceptor();
//    }

    private static final Logger logger = LoggerFactory.getLogger(CustomConfigForInterceptors.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.info("CustomConfigForInterceptors service_2 addInterceptors() - START");

        registry.addInterceptor(tokenCheckInterceptor).excludePathPatterns("/**/get-token");
    }
}