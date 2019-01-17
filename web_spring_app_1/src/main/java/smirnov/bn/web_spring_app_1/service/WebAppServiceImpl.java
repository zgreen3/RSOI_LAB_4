package smirnov.bn.web_spring_app_1.service;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import smirnov.bn.web_spring_app_1.controller.MainController;
import smirnov.bn.web_spring_app_1.model.EmployeeInfo;
import smirnov.bn.web_spring_app_1.model.UserInfo;

public class WebAppServiceImpl implements WebAppService {

    private static final Logger logger = LoggerFactory.getLogger(WebAppServiceImpl.class);

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String API_SERVICE_URI_CMN_DIR_STRING = "/gateway_API";
    private static final String API_SERVICE_PORT_STRING = "8194" + API_SERVICE_URI_CMN_DIR_STRING;

    private static final String SERVICE_1_PORT_STRING = API_SERVICE_PORT_STRING; //"8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;

    private static final String CREATE_LNG_VR_POST_URI_STRING = "/create-ling_var";
    private static final String READ_BY_ID_LNG_VR_GET_URI_STRING = "/read-";
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING = "/read-by-emp-uuid-";
    private static final String READ_ALL_LNG_VR_GET_URI_STRING = "/read-all";
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_STRING = "/read-all-paginated";
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_STRING = "/update-ling_var";
    private static final String DELETE_LNG_VR_DELETE_URI_STRING = "/delete-";

    private static final String CREATE_LNG_VR_POST_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + CREATE_LNG_VR_POST_URI_STRING;
    private static final String READ_BY_ID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING;
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING;
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING;
    private static final String DELETE_LNG_VR_DELETE_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + DELETE_LNG_VR_DELETE_URI_STRING;

    private static final String SERVICE_2_PORT_STRING = API_SERVICE_PORT_STRING; //"8192";
    private static final String SERVICE_2_URI_COMMON_DIR_STRING = "/biz_proc_desc/";
    private static final String SERVICE_2_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_2_PORT_STRING + SERVICE_2_URI_COMMON_DIR_STRING;

    private static final String CREATE_BP_DSC_POST_URI_STRING = "create-biz_proc_desc";
    private static final String READ_BY_ID_BP_DSC_GET_URI_STRING = "read-";
    private static final String READ_BY_EMP_UUID_BP_DSC_GET_URI_STRING = "read-by-emp-uuid-";
    private static final String READ_ALL_BP_DSC_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_STRING = "update-biz_proc_desc";
    private static final String DELETE_BP_DSC_DELETE_URI_STRING = "delete-";

    private static final String CREATE_BP_DSC_POST_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + CREATE_BP_DSC_POST_URI_STRING;
    private static final String READ_BY_ID_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_BY_ID_BP_DSC_GET_URI_STRING;
    private static final String READ_BY_EMP_UUID_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_BP_DSC_GET_URI_STRING;
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + UPDATE_BY_ID_BP_DSC_PUT_URI_STRING;
    private static final String DELETE_BP_DSC_DELETE_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + DELETE_BP_DSC_DELETE_URI_STRING;

    private static final String SERVICE_3_PORT_STRING = API_SERVICE_PORT_STRING; //"8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    //http://localhost:8194/gateway_API/employees/ //read-all/
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;

    private static final String CREATE_EMP_POST_URI_STRING = "create-employee";
    //private static final String READ_BY_UUID_EMP_GET_URI_STRING = "read-";
    private static final String READ_BY_EMP_UUID_EMP_GET_URI_STRING = "read-by-emp-uuid-";
    private static final String READ_ALL_EMP_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_EMP_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_STRING = "update-employee";
    private static final String DELETE_EMP_DELETE_URI_STRING = "delete-";

    private static final String CREATE_EMP_POST_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + CREATE_EMP_POST_URI_STRING;
    private static final String READ_BY_UUID_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_EMP_GET_URI_STRING; //READ_BY_UUID_EMP_GET_URI_STRING;
    private static final String READ_ALL_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_EMP_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_EMP_GET_URI_STRING;
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + UPDATE_BY_UUID_EMP_PUT_URI_STRING;
    private static final String DELETE_EMP_DELETE_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + DELETE_EMP_DELETE_URI_STRING;

    private static final String SCRT_SERVICE_PORT_STRING = API_SERVICE_PORT_STRING; //"8202";
    private static final String SCRT_SERVICE_URI_COMMON_DIR_STRING = "/security_service";
    private static final String SCRT_SERVICE_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_URI_COMMON_DIR_STRING;

    private static final String CREATE_USER_POST_URI_STRING = "/create-user";
    //private static final String READ_BY_ID_USER_GET_URI_STRING = "/read-";
    private static final String READ_BY_USR_UUID_USER_GET_URI_STRING = "/read-by-usr-uuid-";
    private static final String READ_BY_LGN_EML_USER_GET_URI_STRING = "/read-by-usr-login-"; //[+]{userLogin}-email-{userEmail}
    private static final String READ_ALL_USER_GET_URI_STRING = "/read-all";
    private static final String READ_ALL_PGNTD_USER_GET_URI_STRING = "/read-all-paginated";
    private static final String UPDATE_BY_ID_USER_PUT_URI_STRING = "/update-user";
    private static final String DELETE_USER_DELETE_URI_STRING = "/delete-";

    private static final String CREATE_USER_POST_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + CREATE_USER_POST_URI_STRING; //private static final String READ_BY_ID_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_ID_USER_GET_URI_STRING;
    private static final String READ_BY_USR_UUID_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_USR_UUID_USER_GET_URI_STRING;
    private static final String READ_BY_LGN_EML_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_LGN_EML_USER_GET_URI_STRING;
    private static final String READ_ALL_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_ALL_USER_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_USER_GET_URI_STRING;
    private static final String UPDATE_BY_ID_USER_PUT_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + UPDATE_BY_ID_USER_PUT_URI_STRING;
    private static final String DELETE_USER_DELETE_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + DELETE_USER_DELETE_URI_STRING;

    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    /*
     @Nullable
     void createEmployee(String employeeName, String employeeEmail, String employeeLogin);
     //*/
    public UUID createEmployee(EmployeeInfo employeeInfo) {
        logger.info("createEmployee() in WebAppServiceImpl class in web_spring_app_1 module - START");

        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
        HttpHeaders employeeInfoHeaders = new HttpHeaders();
        employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeInfo> requestEmployeeInfoEntity = new HttpEntity<>(employeeInfo, employeeInfoHeaders);
        ResponseEntity<String> employeesUuidResponseString =
                restTemplate.exchange(CREATE_EMP_POST_URI_TMPLT, //SERVICE_3_URI_COMMON_DIR_STRING + CREATE_EMP_POST_URI_STRING, //UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                        HttpMethod.POST, requestEmployeeInfoEntity, new ParameterizedTypeReference<String>() {
                        });
        if (employeesUuidResponseString.getStatusCode() != HttpStatus.NO_CONTENT) {
            return UUID.fromString(employeesUuidResponseString.getBody());
        } else {
            return null;
        }
    }

    @Nullable
    public List<EmployeeInfo> findAllEmployees() {
        logger.info("findAllEmployees() in WebAppServiceImpl class in web_spring_app_1 module - START");
        return restTemplate.exchange(READ_ALL_EMP_GET_URI_TMPLT, //http://localhost:8194/gateway_API/employees/read-all/ //"http://localhost:8193/employees/read-all",
                //"http://localhost:8194/gateway_API/employees/read-all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                }).getBody();
    }

    @Nullable
    public List<EmployeeInfo> findAllEmployeesPaginated(int page, int sizeLimit) {
        logger.info("findAllEmployeesPaginated() in WebAppServiceImpl class in web_spring_app_1 module - START");
        return restTemplate.exchange(READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit), //"http://localhost:8193/employees/read-all-paginated",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                }).getBody();
    }

    /*
    @Nullable
    EmployeeInfo findEmployeeById(@Nonnull Integer lingVarId);
    //*/

    @Nullable
    public EmployeeInfo findEmployeeByUuid(UUID employeeUuid) {
        logger.info("findEmployeeByUuid() in WebAppServiceImpl class in web_spring_app_1 module - START");
        return restTemplate.exchange(READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuid.toString(),
                //"http://localhost:8193/employees/read-{id}",
                HttpMethod.GET, null, new ParameterizedTypeReference<EmployeeInfo>() {
                }).getBody();
    }

    //void updateEmployee(String employeeName, String employeeEmail, String employeeLogin, UUID employeeUuid);

    public void updateEmployee(EmployeeInfo employeeInfo) {
        logger.info("updateEmployee() in WebAppServiceImpl class in web_spring_app_1 module - START");
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
        HttpHeaders employeeInfoHeaders = new HttpHeaders();
        employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeInfo> requestEmployeeInfoEntity = new HttpEntity<>(employeeInfo, employeeInfoHeaders);
        //ResponseEntity<EmployeeInfo> employeesResponseBuffer =
        restTemplate.exchange(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                HttpMethod.PUT, requestEmployeeInfoEntity, new ParameterizedTypeReference<EmployeeInfo>() {
                });
        //return ;
    }

    //void deleteEmployeeById(Integer employeeId);

    public void deleteEmployeeByUuid(UUID employeeUuid) {
        logger.info("deleteEmployeeByUuid() in WebAppServiceImpl class in web_spring_app_1 module - START");
        restTemplate.exchange(DELETE_EMP_DELETE_URI_TMPLT + employeeUuid.toString(),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<EmployeeInfo>() {
                });
        //return ;
    }

    /*
     @Nullable
     void createEmployee(String userLogin, String userPassword, String userEmail);
     //*/
    public UUID createUser(UserInfo userInfo) {
        logger.info("createUser() in WebAppServiceImpl class in web_spring_app_1 module - START");

        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
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
        logger.info("hashPassword() in WebAppServiceImpl class in web_spring_app_1 module - START");
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
        logger.info("findUserByLoginEmail() in WebAppServiceImpl class in web_spring_app_1 module - START");
        //localhost:8194/gateway_API/security_service/read-by-usr-login-{userLogin}-email-{userEmail}
        return restTemplate.exchange(READ_BY_LGN_EML_USER_GET_URI_TMPLT + userLogin + "-email-" + userEmail,
                //"http://localhost:8202/security_service/read-by-usr-login-{userLogin}-email-{userEmail}",
                HttpMethod.GET, null, new ParameterizedTypeReference<UserInfo>() {
                }).getBody();
    }
}
