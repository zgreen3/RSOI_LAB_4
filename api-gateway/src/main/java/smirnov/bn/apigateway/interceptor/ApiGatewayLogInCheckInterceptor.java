package smirnov.bn.apigateway.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import smirnov.bn.apigateway.info_model_patterns.TokenInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;

import static java.lang.Boolean.TRUE;

@Component
public class ApiGatewayLogInCheckInterceptor extends HandlerInterceptorAdapter {

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

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayLogInCheckInterceptor.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            /*
            long startTime = System.currentTimeMillis();
            System.out.println("\n-------- LogInterception.preHandle --- ");
            System.out.println("Request URL: " + request.getRequestURL());
            System.out.println("Start Time: " + System.currentTimeMillis());

            request.setAttribute("startTime", startTime);
            //*/

        logger.info("ApiGatewayLogInCheckInterceptor apigateway preHandle() - START");

        //https://stackoverflow.com/questions/33118342/java-get-cookie-value-by-name-in-spring-mvc (:)
        if ((request.getCookies() != null) &&
                (Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().equals("AccessTokenID"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null) != null)) {
            //checkTokenValidityOnSecurityServer()
            return true;
        } else if ((request.getHeader("Authorization") != null) &&
                (!request.getHeader("Authorization").isEmpty())) {

            if (request.getHeader("Authorization").equals("Basic [CUSTOM] " +
                    Base64.getEncoder().encodeToString((REST_API_FRONTEND_ID_STRING + ":" + REST_API_FRONTEND_SECRET_STRING).getBytes()))) {
                return true;
            } else {
                //**********проверка access token-а валидности / корректности:**********
                logger.info("check Access Token() in api-gateway class in preHandle() interceptor - START");

                //проверяем наличие валидного корректного токена, отбрасываем значение "Bearer":
                String[] accessTokenArray = request.getHeader("Authorization").split(" ");
                String accessTokenUuidAsString = "0";
                if (accessTokenArray.length > 1) {
                    accessTokenUuidAsString = accessTokenArray[1];
                }
                String boolCheckValStr = "false";
                if (!accessTokenUuidAsString.equals("0")) {
                    TokenInfo tokenInfo = new TokenInfo(accessTokenUuidAsString);
                    HttpHeaders authCodeInfoHeaders = new HttpHeaders();
                    authCodeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<TokenInfo> requestTokenInfoEntity = new HttpEntity<>(tokenInfo, authCodeInfoHeaders);
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
            }
        } else {
            //https://stackoverflow.com/questions/39554740/springboot-how-to-return-error-status-code-in-prehandle-of-handlerinterceptor (:)
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }


        /*
        EmployeeInfo employeeInfo = new EmployeeInfo();

        ResponseEntity<List<LingVarInfo>> lingVarInfoResponse =
                restTemplate.exchange("http://localhost:8191/ling_var_dict/read-all", //READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT
                                //+ String.valueOf(employeeInfo.getEmployeeUuid()), //"http://localhost:8191/ling_var_dict/read-by-emp-uuid-{employeeUuid}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<LingVarInfo>>() {
                        });
        List<LingVarInfo> lingVarInfoList = lingVarInfoResponse.getBody();

        for (LingVarInfo lingVarInfo : lingVarInfoList) {
            logger.info("Another lingVarInfo in lingVarInfoList in updateEmployeeWithLingVarData() params"
                    + "\n" + "Id param: " + String.valueOf(lingVarInfo.getLingVarId())
                    + "\n" + "LingVarName param: " + lingVarInfo.getLingVarName()
                    + "\n" + "EmployeeUuid param: " + lingVarInfo.getEmployeeUuid()
                    + "\n" + "LingVarTermLowVal param: " + String.valueOf(lingVarInfo.getLingVarTermLowVal())
                    + "\n" + "LingVarTermMedVal param: " + String.valueOf(lingVarInfo.getLingVarTermMedVal())
                    + "\n" + "LingVarTermHighVal param: " + String.valueOf(lingVarInfo.getLingVarTermHighVal()));
        }
        //*/

        ////https://o7planning.org/en/11689/spring-boot-interceptors-tutorial (:)
        //response.sendRedirect(request.getContextPath() + "/loginUser");
        //return false;
        //return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //super.postHandle(request, response, handler, modelAndView);
        logger.info("ApiGatewayLogInCheckInterceptor web_spring_app_1 postHandle() - START");
        /*
        if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            response.sendRedirect("http://localhost:8203/loginUser");
        }
        //*/
    }
}
