package smirnov.bn.apigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.TokenInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;

public class InterServicesRequestImpl implements InterServicesRequest {

    private static final Logger logger = LoggerFactory.getLogger(InterServicesRequestImpl.class);

    private String tokenUuidStringService_1_SavedLocally;
    private String tokenUuidStringService_2_SavedLocally;
    private String tokenUuidStringService_3_SavedLocally;

    public String getTokenUuidStringService_1_SavedLocally() {
        return tokenUuidStringService_1_SavedLocally;
    }

    public void setTokenUuidStringService_1_SavedLocally(String tokenUuidStringService_1_SavedLocally) {
        this.tokenUuidStringService_1_SavedLocally = tokenUuidStringService_1_SavedLocally;
    }

    public String getTokenUuidStringService_2_SavedLocally() {
        return tokenUuidStringService_2_SavedLocally;
    }

    public void setTokenUuidStringService_2_SavedLocally(String tokenUuidStringService_2_SavedLocally) {
        this.tokenUuidStringService_2_SavedLocally = tokenUuidStringService_2_SavedLocally;
    }

    public String getTokenUuidStringService_3_SavedLocally() {
        return tokenUuidStringService_3_SavedLocally;
    }

    public void setTokenUuidStringService_3_SavedLocally(String tokenUuidStringService_3_SavedLocally) {
        this.tokenUuidStringService_3_SavedLocally = tokenUuidStringService_3_SavedLocally;
    }

    public ResponseEntity<Object> execute(String fullPathUriString, HttpMethod httpMethod, String serviceTokenSavedLocally,
                                          String serviceTokenRequestPath) {
        ///*
        URI uri;
        try {
            uri = new URI(fullPathUriString);
        } catch (URISyntaxException uriEx) {
            logger.error("URISyntaxException in InterServicesRequestImpl api-gateway execute()", uriEx);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URISyntaxException in InterServicesRequestImpl api-gateway execute(): "
                    + uriEx.getMessage());
        }

        ///*
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + serviceTokenSavedLocally);
        HttpEntity<String> requestInfoEntity = new HttpEntity<>(httpHeaders);
        //session.setAttribute("lastServiceTokenRequestPathBuffered", serviceTokenRequestPath);
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<List<LingVarInfo>> lingVarInfoResponse;
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity =
                    restTemplate.exchange(uri, httpMethod, requestInfoEntity, new ParameterizedTypeReference<Object>() {
                    });
        } catch (HttpStatusCodeException sce) {
            logger.error("HttpStatusCodeException in execute(...) in InterServicesRequestImpl", sce);
            //throw sce;
            /*
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                //TokenInfoWithAppKeyAndSecret tokenInfoWithAppKeyAndSecret = new TokenInfoWithAppKeyAndSecret(API_GATEWAY_KEY_STRING, API_GATEWAY_SECRET_STRING);
                HttpHeaders tokenInfoHeaders = new HttpHeaders();
                tokenInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                //https://stackoverflow.com/questions/3538021/why-do-we-use-base64
                //https://stackoverflow.com/questions/21920268/basic-authentication-for-rest-api-using-spring-resttemplate (:)
                String appKeyAndSecretColonSeparated = API_GATEWAY_KEY_STRING + ":" + API_GATEWAY_SECRET_STRING;
                String base64AppKeyAndSecretColonSeparated = //Base64.encodeBase64(appKeyAndSecretColonSeparated.getBytes()).toString();
                        java.util.Base64.getEncoder().encodeToString((API_GATEWAY_KEY_STRING + ":" + API_GATEWAY_SECRET_STRING).getBytes());
                tokenInfoHeaders.add("Authorization", "Basic " + base64AppKeyAndSecretColonSeparated);
                HttpEntity<String> requestEntityWIthAppKeyAndSecretHeaders = new HttpEntity<>(tokenInfoHeaders);
                //HttpEntity<TokenInfoWithAppKeyAndSecret> requestTokenInfoEntity = new HttpEntity<>(tokenInfoWithAppKeyAndSecret, tokenInfoHeaders);
                //N.B.: просим тот или иной service (в зависимости от значения lastServiceTokenRequestPath)
                //создать для нас новый токен (поэтому HttpMethod.POST) и прислать его UUID нам в замен
                //на наши API_GATEWAY_KEY_STRING и API_GATEWAY_SECRET_STRING (:)
                ResponseEntity<TokenInfo> tokenInfoResponseEntity =
                        restTemplate.exchange(
                                //https://stackoverflow.com/questions/18791645/how-to-use-session-attributes-in-spring-mvc
                                //https://stackoverflow.com/questions/31616546/adding-parameters-to-exceptionhandler-for-methodargumentnotvalidexception-in-sp (:)
                                lastServiceTokenRequestPath, //CREATE_TOKEN_POST_URI_TMPLT, //create Token
                                HttpMethod.POST,
                                requestEntityWIthAppKeyAndSecretHeaders, //requestTokenInfoEntity,
                                new ParameterizedTypeReference<TokenInfo>() {
                                });
                String tokenUuidAsString;
                if (tokenInfoResponseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
                    tokenUuidAsString = tokenInfoResponseEntity.getBody().getAccessTokenUuid().toString();
                } else {
                    tokenUuidAsString = "";
                }

                //проверяем, к какому сервису относится выданный токен (исходя из того, у какого сервиса мы его запросили),
                //и сохраняем его в соответствующую локальную переменную:
                if (lastServiceTokenRequestPath.contains(SERVICE_1_URI_COMMON_DIR_STRING)) {
                    setTokenUuidStringService_1_SavedLocally(tokenUuidAsString);
                } else if (lastServiceTokenRequestPath.contains(SERVICE_2_URI_COMMON_DIR_STRING)) {
                    setTokenUuidStringService_2_SavedLocally(tokenUuidAsString);
                } else if (lastServiceTokenRequestPath.contains(SERVICE_3_URI_COMMON_DIR_STRING)) {
                    setTokenUuidStringService_3_SavedLocally(tokenUuidAsString);
                } else { //по умолчанию считаем, что это был сервис обработки данных по сотрудникам:
                    setTokenUuidStringService_1_SavedLocally(tokenUuidAsString);
                }
                logger.info("API_Gateway_controller api-gateway handleHttpUnauthorizedStatusCodeException() - lastMethodRequestMappingValueBuffered: " +
                        MAIN_WEB_SERVER_HOST_STRING + API_SERVICE_PORT_STRING + session.getAttribute("lastMethodRequestMappingValueBuffered"));
                //*/
                return null; //responseEntity;
        }
        //*/
        /*
        //RequestEntity request = new RequestEntity();
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }
        //https://stackoverflow.com/questions/52523173/get-request-values-from-exceptionhandler-using-spring-mvc (:)
        HttpEntity<Object> httpEntity = new HttpEntity<>(request.getAttribute("requestBodyCustom"), headers);
        try {
            return restTemplate.exchange(uri, httpMethod, httpEntity, Object.class).getBody().toString();
        } catch (HttpStatusCodeException sce) {
            logger.error("HttpStatusCodeException error in InterServicesRequestImpl api-gateway execute()");
            return ResponseEntity.status(sce.getRawStatusCode())
                    .headers(sce.getResponseHeaders())
                    .body(sce.getResponseBodyAsString()).getBody();
        }
        //*/
        /*
        try {

        } catch() {

        }
        //*/
        return responseEntity;
    }
}
