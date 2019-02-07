package smirnov.bn.apigateway.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface InterServicesRequest {
    ResponseEntity<Object> execute(String fullPathUriString, HttpMethod httpMethod, String serviceTokenSavedLocally, String serviceTokenRequestPath);
}
