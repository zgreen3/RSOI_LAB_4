package smirnov.bn.REST_API_frontend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

import smirnov.bn.REST_API_frontend.model.AuthorizationCodeInfo;
import smirnov.bn.REST_API_frontend.model.UserInfo;


public class RestApiFrontendServiceImpl implements RestApiFrontendService {

    private static final Logger logger = LoggerFactory.getLogger(RestApiFrontendServiceImpl.class);

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String API_SERVICE_URI_CMN_DIR_STRING = "/gateway_API";
    private static final String API_SERVICE_PORT_STRING = "8194" + API_SERVICE_URI_CMN_DIR_STRING;

    private static final String SCRT_SERVICE_PORT_STRING = API_SERVICE_PORT_STRING; //"8202";

    private static final String SCRT_SERVICE_URI_COMMON_DIR_STRING = "/security_service";
    private static final String SCRT_SERVICE_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_URI_COMMON_DIR_STRING;

    private static final String CREATE_USER_POST_URI_STRING = "/create-user";
    private static final String READ_BY_LGN_EML_USER_GET_URI_STRING = "/read-by-usr-login-"; //[+]{userLogin}-email-{userEmail}
    private static final String CREATE_USER_POST_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + CREATE_USER_POST_URI_STRING;
    private static final String READ_BY_LGN_EML_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_LGN_EML_USER_GET_URI_STRING;

    private static final String SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING = "/security_service/authorization";
    private static final String SCRT_SERVICE_AUTH_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING;
    private static final String CREATE_AUTH_CODE_POST_URI_STRING = "/create-auth-code";
    private static final String CREATE_AUTH_CODE_POST_URI_TMPLT = SCRT_SERVICE_AUTH_ABS_URI_COMMON_STRING + CREATE_AUTH_CODE_POST_URI_STRING;

    private static final String REST_API_FRONTEND_ID_STRING = "REST_API_FRONTEND_1_CLT_ID0_000_2";
    private static final String REST_API_FRONTEND_SECRET_STRING = "REST_API_FRONTEND_1_CLT_0SECRET0STRING0_000_2";


    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    /*
     @Nullable
     void createEmployee(String userLogin, String userPassword, String userEmail);
     //*/
    public UUID createUser(UserInfo userInfo) {
        logger.info("createUser() in RestApiFrontendServiceImpl class in rest_api_frontend module - START");

        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
        //https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate (:)
        String encodedAuthentication = "Basic [CUSTOM] " +
                Base64.getEncoder().encodeToString((REST_API_FRONTEND_ID_STRING + ":" + REST_API_FRONTEND_SECRET_STRING).getBytes());
        userInfoHeaders.set("Authorization", encodedAuthentication);
        HttpEntity<UserInfo> requestUserInfoEntity = new HttpEntity<>(userInfo, userInfoHeaders);
        ResponseEntity<String> usersUuidResponseString =
                restTemplate.exchange(CREATE_USER_POST_URI_TMPLT, //SERVICE_3_URI_COMMON_DIR_STRING + CREATE_EMP_POST_URI_STRING, //UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                        HttpMethod.POST, requestUserInfoEntity, new ParameterizedTypeReference<String>() {});
        if (usersUuidResponseString.getStatusCode() != HttpStatus.NO_CONTENT) {
            return UUID.fromString(usersUuidResponseString.getBody());
        } else {
            return null;
        }
    }

    //passwords hashing (:)
    //https://github.com/defuse/password-hashing/blob/master/PasswordStorage.java
    //https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
    //https://stackoverflow.com/questions/19348501/pbkdf2withhmacsha512-vs-pbkdf2withhmacsha1
    public String hashPassword(String password) {
        logger.info("hashPassword() in RestApiFrontendServiceImpl class in rest_api_frontend module - START");
        String hashedPassword;
        String exceptionString;
        try {
            hashedPassword = PasswordHashingHelper.createHash(password);
        } catch (PasswordHashingHelper.CannotPerformOperationException e) {
            exceptionString = "PasswordHashingHelper.CannotPerformOperationException: " + e.toString();
            System.out.println(exceptionString);
            return exceptionString;
        }
        return hashedPassword;
    }

    public UserInfo findUserByLoginEmail(String userLogin, String userEmail) {
        logger.info("findUserByLoginEmail() in RestApiFrontendServiceImpl class in rest_api_frontend module - START");
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        //https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate (:)
        String encodedAuthentication = "Basic [CUSTOM] " +
                Base64.getEncoder().encodeToString((REST_API_FRONTEND_ID_STRING + ":" + REST_API_FRONTEND_SECRET_STRING).getBytes());
        customHeaders.set("Authorization", encodedAuthentication);
        HttpEntity<UserInfo> customRequestEntity = new HttpEntity<>(null, customHeaders);
        //localhost:8194/gateway_API/security_service/read-by-usr-login-{userLogin}-email-{userEmail}
        return restTemplate.exchange(READ_BY_LGN_EML_USER_GET_URI_TMPLT + userLogin + "-email-" + userEmail,
                //"http://localhost:8202/security_service/read-by-usr-login-{userLogin}-email-{userEmail}",
                HttpMethod.GET, customRequestEntity, new ParameterizedTypeReference<UserInfo>() {
                }).getBody();
    }

    public UUID createAuthenticationCode(String clientId, String redirectionUri) {
        logger.info("createAuthenticationCode() in RestApiFrontendServiceImpl class in rest_api_frontend module - START");

        //[https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html] [:]
        //localhost:8194/gateway_API/security_service/authorization/create-auth-code (:)
        AuthorizationCodeInfo authorizationCodeInfo = new AuthorizationCodeInfo(clientId, redirectionUri);
        HttpHeaders authorizationCodeHeaders = new HttpHeaders();
        authorizationCodeHeaders.setContentType(MediaType.APPLICATION_JSON);
        //https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate (:)
        String encodedAuthentication = "Basic [CUSTOM] " +
                Base64.getEncoder().encodeToString((REST_API_FRONTEND_ID_STRING + ":" + REST_API_FRONTEND_SECRET_STRING).getBytes());
        authorizationCodeHeaders.set("Authorization", encodedAuthentication);
        HttpEntity<AuthorizationCodeInfo> requestAuthorizationCodeInfoEntity = new HttpEntity<>(authorizationCodeInfo, authorizationCodeHeaders);
        ResponseEntity<String> authorizationCodeUuidResponseString =
                restTemplate.exchange(CREATE_AUTH_CODE_POST_URI_TMPLT,
                        HttpMethod.POST, requestAuthorizationCodeInfoEntity, new ParameterizedTypeReference<String>() {});
        if (authorizationCodeUuidResponseString.getStatusCode() != HttpStatus.NO_CONTENT) {
            return UUID.fromString(authorizationCodeUuidResponseString.getBody());
        } else {
            return null;
        }
    }

    public Boolean isAuthCodeValid() {
        return true;
    }

    public String buildOAuth2FirstAuthorizationUri() {
        return "http://8203";
    }
}
