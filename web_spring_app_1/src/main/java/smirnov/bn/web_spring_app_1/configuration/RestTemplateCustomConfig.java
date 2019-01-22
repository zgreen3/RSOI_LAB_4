package smirnov.bn.web_spring_app_1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import smirnov.bn.web_spring_app_1.interceptor.RestTemplateCustomAccessTokenSettingInterceptor;

//https://www.baeldung.com/spring-rest-template-interceptor (:)

@Configuration
public class RestTemplateCustomConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RestTemplateCustomAccessTokenSettingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}