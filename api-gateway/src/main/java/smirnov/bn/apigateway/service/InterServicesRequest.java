package smirnov.bn.apigateway.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface InterServicesRequest {
    <T> ResponseEntity<T> execute(String fullPathUriString, HttpMethod httpMethod, String serviceModuleName,
                                   ParameterizedTypeReference<T> parameterizedTypeReference);
}
