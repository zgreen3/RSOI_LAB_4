package smirnov.bn.service_1.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import smirnov.bn.service_1.model.TokenInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;

import static java.lang.Boolean.TRUE;

@Component
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING = "/read-by-emp-uuid-";
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING;

    //private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String SCRT_SERVICE_PORT_STRING = "8202";
    private static final String SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING = "/security_service/authorization";
    private static final String CHECK_ACCESS_TOKEN_POST_URI_STRING = "/access-token-validation";
    private static final String SCRT_AUTH_SERVICE_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING;
    private static final String CHECK_ACCESS_TOKEN_POST_URI_TMPLT = SCRT_AUTH_SERVICE_ABS_URI_COMMON_STRING + CHECK_ACCESS_TOKEN_POST_URI_STRING;

    //Client ID and Client Secret from web_frontend (:)
    private static final String REST_API_FRONTEND_ID_STRING = "REST_API_FRONTEND_1_CLT_ID0_000_2";
    private static final String REST_API_FRONTEND_SECRET_STRING = "REST_API_FRONTEND_1_CLT_0SECRET0STRING0_000_2";

    private static final Logger logger = LoggerFactory.getLogger(TokenCheckInterceptor.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            /*//DEBUG_CODE:
            long startTime = System.currentTimeMillis();
            System.out.println("\n-------- LogInterception.preHandle --- ");
            System.out.println("Request URL: " + request.getRequestURL());
            System.out.println("Start Time: " + System.currentTimeMillis());
            request.setAttribute("startTime", startTime);
            //*/

        logger.info("TokenCheckInterceptor service_1 preHandle() - START");

        if ((request.getHeader("Authorization") != null) &&
                (!request.getHeader("Authorization").isEmpty()) &&
                (!request.getHeader("Authorization").contains("Basic"))) {
            //**********проверка access token-а валидности / корректности:**********
            logger.info("check [AppKey & AppSecret] Token() in service_1 class in preHandle() interceptor - START");

            //проверяем наличие валидного корректного токена, отбрасываем значение "Basic":
            String[] accessTokenArray = request.getHeader("Authorization").split(" ");
            String tokenUuidAsString = "0";
            if (accessTokenArray.length > 1) {
                tokenUuidAsString = accessTokenArray[1];
            }
            String boolCheckValStr = "false";
            if (!tokenUuidAsString.equals("0")) {
                TokenInfo tokenInfo = new TokenInfo(tokenUuidAsString);
                HttpHeaders authCodeInfoHeaders = new HttpHeaders();
                authCodeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<TokenInfo> requestTokenInfoEntity = new HttpEntity<>(tokenInfo, authCodeInfoHeaders);

                //TODO: 1) rewrite this code to check token from this service, 2) write code to give new token and correct gateway invocation of it:
                ResponseEntity<String> tokenUuidResponseString =
                        restTemplate.exchange(CHECK_ACCESS_TOKEN_POST_URI_TMPLT, //check access token
                                HttpMethod.POST, requestTokenInfoEntity, new ParameterizedTypeReference<String>() {
                                });

                boolCheckValStr = tokenUuidResponseString.getBody();
            }

            if (TRUE.equals(Boolean.valueOf(boolCheckValStr))) {
                return true;
            } else {
                //https://stackoverflow.com/questions/39554740/springboot-how-to-return-error-status-code-in-prehandle-of-handlerinterceptor (:)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        } else {
            //https://stackoverflow.com/questions/39554740/springboot-how-to-return-error-status-code-in-prehandle-of-handlerinterceptor (:)
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
