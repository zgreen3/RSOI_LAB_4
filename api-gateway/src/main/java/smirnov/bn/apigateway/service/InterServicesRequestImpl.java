package smirnov.bn.apigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

import smirnov.bn.apigateway.info_model_patterns.TokenInfo;

@Service
public class InterServicesRequestImpl implements InterServicesRequest {

    private static final Logger logger = LoggerFactory.getLogger(InterServicesRequestImpl.class);

    private static volatile String tokenUuidStringService_1_SavedLocally;
    private static volatile String tokenUuidStringService_2_SavedLocally;
    private static volatile String tokenUuidStringService_3_SavedLocally;

    public static String getTokenUuidStringService_1_SavedLocally() {
        return tokenUuidStringService_1_SavedLocally;
    }

    public static void setTokenUuidStringService_1_SavedLocally(String tokenUuidStringService_1_SavedLocally) {
        tokenUuidStringService_1_SavedLocally = tokenUuidStringService_1_SavedLocally;
    }

    public static String getTokenUuidStringService_2_SavedLocally() {
        return tokenUuidStringService_2_SavedLocally;
    }

    public static void setTokenUuidStringService_2_SavedLocally(String tokenUuidStringService_2_SavedLocally) {
        tokenUuidStringService_2_SavedLocally = tokenUuidStringService_2_SavedLocally;
    }

    public static String getTokenUuidStringService_3_SavedLocally() {
        return tokenUuidStringService_3_SavedLocally;
    }

    public static void setTokenUuidStringService_3_SavedLocally(String tokenUuidStringService_3_SavedLocally) {
        tokenUuidStringService_3_SavedLocally = tokenUuidStringService_3_SavedLocally;
    }

    private static final String API_GATEWAY_KEY_STRING = "API_GATEWAY_APP_KEY0_000_1";
    private static final String API_GATEWAY_SECRET_STRING = "API_GATEWAY_APP_0SECRET0STRING0_000_1";

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;
    private static final String GET_TOKEN_LNG_VR_GET_URI_STRING = "/authorization/get-token";

    private static final String SERVICE_2_PORT_STRING = "8192";
    private static final String SERVICE_2_URI_COMMON_DIR_STRING = "/biz_proc_desc/";
    private static final String SERVICE_2_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_2_PORT_STRING + SERVICE_2_URI_COMMON_DIR_STRING;
    private static final String GET_TOKEN_BP_DSC_GET_URI_STRING = "authorization/get-token";

    private static final String SERVICE_3_PORT_STRING = "8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;
    private static final String GET_TOKEN_EMP_GET_URI_STRING = "authorization/get-token";

    private static final String GET_TOKEN_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + GET_TOKEN_LNG_VR_GET_URI_STRING;
    private static final String GET_TOKEN_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + GET_TOKEN_BP_DSC_GET_URI_STRING;
    private static final String GET_TOKEN_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + GET_TOKEN_EMP_GET_URI_STRING;

    public <T> ResponseEntity<T> execute(String fullPathUriString, HttpMethod httpMethod, String serviceModuleName,
                                         ParameterizedTypeReference<T> parameterizedTypeReference) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> responseEntity = null;

        URI uri;
        try {
            uri = new URI(fullPathUriString);
        } catch (URISyntaxException uriEx) {
            logger.error("URISyntaxException in InterServicesRequestImpl api-gateway execute()", uriEx);
            return responseEntity;
        }

        String serviceTokenUuidSavedLocally;
        String serviceTokenRequestPath;
        if (serviceModuleName.equals("service_1")) {
            serviceTokenUuidSavedLocally = InterServicesRequestImpl.getTokenUuidStringService_1_SavedLocally();
            serviceTokenRequestPath = GET_TOKEN_LNG_VR_GET_URI_TMPLT;
        } else if (serviceModuleName.equals("service_2")) {
            serviceTokenUuidSavedLocally = InterServicesRequestImpl.getTokenUuidStringService_2_SavedLocally();
            serviceTokenRequestPath = GET_TOKEN_BP_DSC_GET_URI_TMPLT;
        } else if (serviceModuleName.equals("service_3")) {
            serviceTokenUuidSavedLocally = InterServicesRequestImpl.getTokenUuidStringService_3_SavedLocally();
            serviceTokenRequestPath = GET_TOKEN_EMP_GET_URI_TMPLT;
        } else { //по умолчанию считаем, что это "service_1"
            serviceTokenUuidSavedLocally = InterServicesRequestImpl.getTokenUuidStringService_1_SavedLocally();
            serviceTokenRequestPath = GET_TOKEN_LNG_VR_GET_URI_TMPLT;
        }

        logger.info("InterServicesRequestImpl api-gateway execute() - serviceTokenUuidSavedLocally at start is: " +
                serviceTokenUuidSavedLocally);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + serviceTokenUuidSavedLocally);
        HttpEntity<String> requestInfoEntity = new HttpEntity<>(httpHeaders);

        try {
            responseEntity =
                    restTemplate.exchange(uri, httpMethod, requestInfoEntity, parameterizedTypeReference);
        } catch (HttpStatusCodeException sce) {

            logger.error("HttpStatusCodeException in execute(...) in InterServicesRequestImpl", sce);

            if (sce.getStatusCode() == HttpStatus.UNAUTHORIZED) {

                HttpHeaders tokenInfoHeaders = new HttpHeaders();
                tokenInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                String base64AppKeyAndSecretColonSeparated =
                        java.util.Base64.getEncoder().encodeToString((API_GATEWAY_KEY_STRING + ":" + API_GATEWAY_SECRET_STRING).getBytes());
                tokenInfoHeaders.add("Authorization", "Basic " + base64AppKeyAndSecretColonSeparated);

                HttpEntity<String> requestEntityWIthAppKeyAndSecretHeaders = new HttpEntity<>(tokenInfoHeaders);

                ResponseEntity<TokenInfo> tokenInfoResponseEntity =
                        restTemplate.exchange(
                                //https://stackoverflow.com/questions/18791645/how-to-use-session-attributes-in-spring-mvc
                                //https://stackoverflow.com/questions/31616546/adding-parameters-to-exceptionhandler-for-methodargumentnotvalidexception-in-sp (:)
                                serviceTokenRequestPath,
                                HttpMethod.POST,
                                requestEntityWIthAppKeyAndSecretHeaders,
                                new ParameterizedTypeReference<TokenInfo>() {
                                });
                String tokenUuidAsString;
                if (tokenInfoResponseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
                    tokenUuidAsString = tokenInfoResponseEntity.getBody().getAccessTokenUuid().toString();
                } else {
                    tokenUuidAsString = "";
                }

                if (serviceModuleName.equals("service_1")) {
                    InterServicesRequestImpl.setTokenUuidStringService_1_SavedLocally(tokenUuidAsString);
                    serviceTokenUuidSavedLocally = tokenUuidAsString;
                } else if (serviceModuleName.equals("service_2")) {
                    InterServicesRequestImpl.setTokenUuidStringService_2_SavedLocally(tokenUuidAsString);
                    serviceTokenUuidSavedLocally = tokenUuidAsString;
                } else if (serviceModuleName.equals("service_3")) {
                    InterServicesRequestImpl.setTokenUuidStringService_3_SavedLocally(tokenUuidAsString);
                    serviceTokenUuidSavedLocally = tokenUuidAsString;
                } else { //по умолчанию считаем, что это был сервис обработки данных по сотрудникам:
                    InterServicesRequestImpl.setTokenUuidStringService_1_SavedLocally(tokenUuidAsString);
                    serviceTokenUuidSavedLocally = tokenUuidAsString;
                }

                logger.info("InterServicesRequestImpl api-gateway execute() - serviceTokenUuidSavedLocally after given is: " +
                        serviceTokenUuidSavedLocally);

                logger.info("InterServicesRequestImpl api-gateway execute() - in HttpStatusCodeException catch: after service token given");

                httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                httpHeaders.add("Authorization", "Bearer " + serviceTokenUuidSavedLocally);
                requestInfoEntity = new HttpEntity<>(httpHeaders);
                try {
                    responseEntity =
                            restTemplate.exchange(uri, httpMethod, requestInfoEntity, parameterizedTypeReference);
                } catch (Exception e) {

                    logger.error("Exception in InterServicesRequestImpl api-gateway execute(), in catch() (#2) after token given", e);
                    return responseEntity;
                }
            }

            return responseEntity;
        }

        return responseEntity;
    }
}
