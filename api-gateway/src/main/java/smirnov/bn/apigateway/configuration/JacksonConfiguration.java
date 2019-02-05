package smirnov.bn.apigateway.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://stackoverflow.com/questions/7854030/configuring-objectmapper-in-spring (:)
@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        //https://stackoverflow.com/questions/14588727/can-not-deserialize-instance-of-java-util-arraylist-out-of-value-string (:)
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        return objectMapper;
    }
}