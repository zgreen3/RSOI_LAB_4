package smirnov.bn.apigateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import smirnov.bn.apigateway.interceptor.ApiGatewayLogInCheckInterceptor;

@EnableWebMvc
@Configuration
public class CustomConfigForInterceptors extends WebMvcConfigurerAdapter  {
    //https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm (:)
    @Autowired
    ApiGatewayLogInCheckInterceptor apiGatewayLogInCheckInterceptor;

    /*
    //https://stackoverflow.com/questions/23349180/java-config-for-spring-interceptor-where-interceptor-is-using-autowired-spring-b (:)
    @Bean
    ApiGatewayLogInCheckInterceptor apiGatewayLogInCheckInterceptor() {
        return new ApiGatewayLogInCheckInterceptor();
    }
    //*/

    //@Autowired
    //RestTemplate restTemplate; //= new RestTemplate();

    ////http://www.geekabyte.io/2014/06/how-to-autowire-bean-with-constructor.html (:)
    ////[ Constructor Injection][:}
    //@Autowired
    //ApiGatewayLogInCheckInterceptor(RestTemplate restTemplate) {
    //    this.restTemplate = restTemplate;
    //}

    private static final Logger logger = LoggerFactory.getLogger(CustomConfigForInterceptors.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.info("CustomConfigForInterceptors apigateway addInterceptors() - START");

        //Log In Check Interceptor applied to all URLs (:)
        //https://stackoverflow.com/questions/33864252/spring-mvc-handler-interceptor-with-exclude-path-pattern-with-pathparam?lq=1 (,)
        //https://stackoverflow.com/questions/34970179/exclude-spring-request-handlerinterceptor-by-path-pattern/34974725 (,)
        //https://stackoverflow.com/questions/9908124/spring-mvc-3-interceptor-on-all-excluding-some-defined-paths/33729938 (:)
        registry.addInterceptor(apiGatewayLogInCheckInterceptor
                //new ApiGatewayLogInCheckInterceptor(restTemplate)
        ).excludePathPatterns(
                "/**/security_service/authorization/**",
                "/**/create-auth-code",
                "/**/auth-code-validation",
                "/**/create-access-token",
                "/**/access-token-validation");
    }
}
