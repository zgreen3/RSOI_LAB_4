package smirnov.bn.web_spring_app_1.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smirnov.bn.web_spring_app_1.controller.MainController;
import smirnov.bn.web_spring_app_1.model.EmployeeInfo;
import smirnov.bn.web_spring_app_1.model.LingVarInfo;

import java.util.List;

@Component
public class LogInCheckInterceptor extends HandlerInterceptorAdapter {

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING = "/read-by-emp-uuid-";
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING;

    private static final Logger logger = LoggerFactory.getLogger(LogInCheckInterceptor.class);
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

        logger.info("LogInCheckInterceptor web_spring_app_1 preHandle() - START");

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
        return true;
    }
}
