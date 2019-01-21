package smirnov.bn.apigateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import smirnov.bn.apigateway.interceptor.ApiGatewayLogInCheckInterceptor;

@Configuration
public class CustomConfigForInterceptors extends WebMvcConfigurerAdapter {
    //https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm (:)
    @Autowired
    ApiGatewayLogInCheckInterceptor apiGatewayLogInCheckInterceptor;

    private static final Logger logger = LoggerFactory.getLogger(CustomConfigForInterceptors.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.info("CustomConfigForInterceptors apigateway addInterceptors() - START");

        //Log In Check Interceptor applied to all URLs (:)
        //https://stackoverflow.com/questions/33864252/spring-mvc-handler-interceptor-with-exclude-path-pattern-with-pathparam?lq=1 (,)
        //https://stackoverflow.com/questions/34970179/exclude-spring-request-handlerinterceptor-by-path-pattern/34974725 (,)
        //https://stackoverflow.com/questions/9908124/spring-mvc-3-interceptor-on-all-excluding-some-defined-paths/33729938 (:)
        registry.addInterceptor(apiGatewayLogInCheckInterceptor).excludePathPatterns(
                "/security_service/authorization/**",
                "/**/create-auth-code",
                "/**/auth-code-validation",
                "/**/create-access-token",
                "/**/access-token-validation");
    }
}
