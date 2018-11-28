package smirnov.bn.apigateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import smirnov.bn.apigateway.info_model_patterns.EmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarWithEmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.BusinessProcDescInfo;

@RestController
@RequestMapping("/gateway_API")
public class ApiGatewayController {

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String SERVICE_1_PORT_STRING = "8191";
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

    private static final String SERVICE_2_PORT_STRING = "8192";
    private static final String SERVICE_2_URI_COMMON_DIR_STRING = "/biz_proc_desc/";
    private static final String SERVICE_2_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_2_PORT_STRING + SERVICE_2_URI_COMMON_DIR_STRING;

    private static final String CREATE_BP_DSC_POST_URI_STRING = "create-biz_proc_desc";
    private static final String READ_BY_ID_BP_DSC_GET_URI_STRING = "read-";
    private static final String READ_ALL_BP_DSC_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_STRING = "update-biz_proc_desc";
    private static final String DELETE_BP_DSC_DELETE_URI_STRING = "delete-";

    private static final String CREATE_BP_DSC_POST_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + CREATE_BP_DSC_POST_URI_STRING;
    private static final String READ_BY_ID_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_BY_ID_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_BP_DSC_GET_URI_STRING;
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + UPDATE_BY_ID_BP_DSC_PUT_URI_STRING;
    private static final String DELETE_BP_DSC_DELETE_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + DELETE_BP_DSC_DELETE_URI_STRING;

    private static final String SERVICE_3_PORT_STRING = "8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;

    private static final String CREATE_EMP_POST_URI_STRING = "create-employee";
    private static final String READ_BY_UUID_EMP_GET_URI_STRING = "read-";
    private static final String READ_ALL_EMP_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_EMP_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_STRING = "update-employee";
    private static final String DELETE_EMP_DELETE_URI_STRING = "delete-";

    private static final String CREATE_EMP_POST_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + CREATE_EMP_POST_URI_STRING;
    private static final String READ_BY_UUID_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_BY_UUID_EMP_GET_URI_STRING;
    private static final String READ_ALL_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_EMP_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_EMP_GET_URI_STRING;
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + UPDATE_BY_UUID_EMP_PUT_URI_STRING;
    private static final String DELETE_EMP_DELETE_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + DELETE_EMP_DELETE_URI_STRING;

    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy (:)
    @ResponseBody
    private <T> ResponseEntity<String> proxingExternalRequests(@RequestBody(required = false) T body,
                                                               HttpMethod method, HttpServletRequest request,
                                                               String microServiceURIString) //, HttpServletResponse response)
            throws URISyntaxException {

        URI uri = new URI(microServiceURIString);

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_1_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/ling_var_dict/create-ling_var" (:)
    @PostMapping(SERVICE_1_URI_COMMON_DIR_STRING + CREATE_LNG_VR_POST_URI_STRING)
    public ResponseEntity<String> createLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createLingVar() - START");
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.POST, request,
                CREATE_LNG_VR_POST_URI_TMPLT);
    }

    //"/ling_var_dict/read-{id}" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING + "{id}")
    public ResponseEntity<String> findLingVarById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findLingVarById() - START" + "\n" + "id param: " + String.valueOf(id));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_LNG_VR_GET_URI_TMPLT + String.valueOf(id));
    }

    //"/ling_var_dict/read-by-emp-uuid-{employeeUuid}" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING + "{employeeUuid}")
    public ResponseEntity<String> findLingVarsByEmployeeUuid(HttpServletRequest request, @PathVariable String employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findLingVarsByEmployeeUuid() - START" + "\n" + "id param: " + employeeUuid);
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT + employeeUuid);
    }

    //"/ling_var_dict/read-all" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_ALL_LNG_VR_GET_URI_STRING)
    public ResponseEntity<String> findAllLingVars(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllLingVars() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_LNG_VR_GET_URI_TMPLT);
    }

    //"/ling_var_dict/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_1_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllLingVarPaginated(HttpServletRequest request,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllLingVarPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/ling_var_dict/update-ling_var" (:)
    @PutMapping(SERVICE_1_URI_COMMON_DIR_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING)
    public ResponseEntity<String> updateLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateLingVar() - START" + "\n" + "id param: " + String.valueOf(lingVarInfo.getLingVarId()));
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT);
    }

    //"/ling_var_dict/delete-{lingVarId}" (:)
    @DeleteMapping(SERVICE_1_URI_COMMON_DIR_STRING + DELETE_LNG_VR_DELETE_URI_STRING)
    public ResponseEntity<String> deleteLingVarById(HttpServletRequest request, @PathVariable Integer lingVarId)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteLingVarById() - START" + "\n" + "id param: " + String.valueOf(lingVarId));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_LNG_VR_DELETE_URI_TMPLT + String.valueOf(lingVarId));
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_2_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/biz_proc_desc/create-biz_proc_desc" (:)
    @PostMapping(SERVICE_2_URI_COMMON_DIR_STRING + CREATE_BP_DSC_POST_URI_TMPLT)
    public ResponseEntity<String> createBusinessProcDesc(HttpServletRequest request, @RequestBody BusinessProcDescInfo businessProcDescInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createBusinessProcDesc() - START");
        return this.proxingExternalRequests(businessProcDescInfo, HttpMethod.POST, request,
                CREATE_BP_DSC_POST_URI_TMPLT);
    }

    //"/biz_proc_desc/read-{id}" (:)
    @GetMapping(SERVICE_2_URI_COMMON_DIR_STRING + READ_BY_ID_BP_DSC_GET_URI_TMPLT + "{id}")
    public ResponseEntity<String> findBusinessProcDescById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findBusinessProcDescById() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_BP_DSC_GET_URI_TMPLT + String.valueOf(id));
    }

    //"/biz_proc_desc/read-all" (:)
    @GetMapping(SERVICE_2_URI_COMMON_DIR_STRING + READ_ALL_BP_DSC_GET_URI_TMPLT)
    public ResponseEntity<String> findAllBusinessProcDescs(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllBusinessProcDescs() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_BP_DSC_GET_URI_TMPLT);
    }

    //"/biz_proc_desc/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_2_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_BP_DSC_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllBusinessProcDescPaginated(HttpServletRequest request,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllBusinessProcDescPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/biz_proc_desc/update-biz_proc_desc" (:)
    @PutMapping(SERVICE_2_URI_COMMON_DIR_STRING + UPDATE_BY_ID_BP_DSC_PUT_URI_STRING)
    public ResponseEntity<String> updateBusinessProcDesc(HttpServletRequest request, @RequestBody BusinessProcDescInfo businessProcDescInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateBusinessProcDesc() - START" + "\n" + "id param: "
                + String.valueOf(businessProcDescInfo.getBizProcId()));
        return this.proxingExternalRequests(businessProcDescInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT);
    }

    //"/biz_proc_desc/delete-{bizProcId}" (:)
    @DeleteMapping(SERVICE_2_URI_COMMON_DIR_STRING + DELETE_BP_DSC_DELETE_URI_TMPLT)
    public ResponseEntity<String> deleteBusinessProcDescById(HttpServletRequest request, @PathVariable Integer bizProcId)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteBusinessProcDescById() - START" + "\n" + "id param: " + String.valueOf(bizProcId));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_BP_DSC_DELETE_URI_TMPLT + String.valueOf(bizProcId));
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_3_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/employees/create-employee" (:)
    @PostMapping(SERVICE_3_URI_COMMON_DIR_STRING + CREATE_EMP_POST_URI_TMPLT)
    public ResponseEntity<String> createEmployee(HttpServletRequest request, @RequestBody EmployeeInfo employeeInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createEmployee() - START");
        return this.proxingExternalRequests(employeeInfo, HttpMethod.POST, request,
                CREATE_EMP_POST_URI_TMPLT);
    }

    //"/employees/read-{id}" (:)
    @GetMapping(SERVICE_3_URI_COMMON_DIR_STRING + READ_BY_UUID_EMP_GET_URI_TMPLT + "{id}")
    public ResponseEntity<String> findEmployeeByUuid(HttpServletRequest request, @PathVariable UUID employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findEmployeeByUuid() - START" + "\n" + "employeeUuid param: " + String.valueOf(employeeUuid));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuid.toString());
    }

    //"/employees/read-all" (:)
    @GetMapping(SERVICE_3_URI_COMMON_DIR_STRING + READ_ALL_EMP_GET_URI_TMPLT)
    public ResponseEntity<String> findAllEmployees(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllEmployees() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_EMP_GET_URI_TMPLT);
    }

    //"/employees/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_3_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_EMP_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllEmployeesPaginated(HttpServletRequest request,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllEmployeesPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/employees/update-employee" (:)
    @PutMapping(SERVICE_3_URI_COMMON_DIR_STRING + UPDATE_BY_UUID_EMP_PUT_URI_STRING)
    public ResponseEntity<String> updateEmployee(HttpServletRequest request, @RequestBody EmployeeInfo employeeInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateEmployee() - START" + "\n" + "employeeUuid param: " + String.valueOf(employeeInfo.getEmployeeUuid()));
        return this.proxingExternalRequests(employeeInfo, HttpMethod.PUT, request,
                UPDATE_BY_UUID_EMP_PUT_URI_TMPLT);
    }

    //"/employees/delete-{employeeUuid}" (:)
    @DeleteMapping(SERVICE_3_URI_COMMON_DIR_STRING + DELETE_EMP_DELETE_URI_TMPLT)
    public ResponseEntity<String> deleteEmployeeByUuid(HttpServletRequest request, @PathVariable UUID employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteEmployeeByUuid() - START" + "\n" + "employeeUuid param: " + String.valueOf(employeeUuid));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_EMP_DELETE_URI_TMPLT + employeeUuid.toString());
    }

    //**********************************************************************************************************************
    //*******************************MULTI_SERVICES_API*********************************************************************
    //**********************************************************************************************************************
    ///ling_var AND employee's info read-all (:)
    @GetMapping("/ling_var_and_employee/read-all")
    public ResponseEntity<List<LingVarWithEmployeeInfo>> findAllLingVarWithEmployeeData() {
        try {
            logger.info("findAllLingVarWithEmployeeData() - START");

            //Сначала получаем данные по всем Лингвистическим переменным:
            //[
            //https://www.baeldung.com/spring-rest-template-list
            //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
            //https://stackoverflow.com/questions/15218462/how-to-aggregate-jax-rs-responses-from-multiple-services
            //]
            ResponseEntity<List<LingVarInfo>> lingVarInfoResponse =
                    restTemplate.exchange(READ_ALL_LNG_VR_GET_URI_TMPLT, //"http://localhost:8191/ling_var_dict/read-all",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<LingVarInfo>>() {
                            });
            List<LingVarInfo> lingVarInfoList = lingVarInfoResponse.getBody();

            //Затем получаем данные по всем Сотрудникам:
            ResponseEntity<List<EmployeeInfo>> employeeInfoResponse =
                    restTemplate.exchange(READ_ALL_EMP_GET_URI_TMPLT, //"http://localhost:8193/employees/read-all",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                            });
            List<EmployeeInfo> employeeInfoList = employeeInfoResponse.getBody();

            //Наконец, для каждой Лингвистической переменной получаем информацию об использовавшем её Сотруднике,
            //технчески осуществляя INNER JOIN Лингвистических переменных и Сотрудниках
            //(см. подробности join-а тут:
            //https://stackoverflow.com/questions/37003220/inner-join-of-two-arraylists-purely-in-java):
            final Map<String, EmployeeInfo> employeeInfoListMappedById = employeeInfoList.stream()
                    .collect(Collectors.toMap(k -> k.getEmployeeUuid().toString(), k -> k));

            final List<LingVarWithEmployeeInfo> lingVarWithEmployeeInfoList = lingVarInfoList.stream()
                    .map(lingVarInfo -> new LingVarWithEmployeeInfo(lingVarInfo,
                            employeeInfoListMappedById.get(lingVarInfo.getEmployeeUuid())))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(lingVarWithEmployeeInfoList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findAllLingVarWithEmployeeData(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    ///ling_var AND employee's info update (:)
    @PutMapping("/ling_var_and_employee/update-ling_var_and_employee")
    public ResponseEntity<String> updateEmployeeWithLingVarData(HttpServletRequest request, @RequestBody LingVarWithEmployeeInfo lingVarWithEmployeeInfo) {
        try {
            logger.info("updateEmployeeWithLingVarData() - START");

            //Сначала изменяем данные по сотруднику (:)
            EmployeeInfo employeeInfo = new EmployeeInfo();
            //N.B.: employeeId никак не будет затронут в процессе обновления, поэтому можем передать сюда null:
            employeeInfo.setEmployeeId(null); //lingVarWithEmployeeInfo.getEmployeeId());
            employeeInfo.setEmployeeName(lingVarWithEmployeeInfo.getEmployeeName());
            employeeInfo.setEmployeeEmail(lingVarWithEmployeeInfo.getEmployeeEmail());
            employeeInfo.setEmployeeLogin(lingVarWithEmployeeInfo.getEmployeeLogin());
            employeeInfo.setEmployeeUuid(UUID.fromString(lingVarWithEmployeeInfo.getEmployeeUuid()));

            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            HttpHeaders employeeInfoHeaders = new HttpHeaders();
            employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EmployeeInfo> requestEmployeeInfoEntity = new HttpEntity<>(employeeInfo, employeeInfoHeaders);
            restTemplate.exchange(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                    HttpMethod.PUT, requestEmployeeInfoEntity, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                    });

            //Затем получаем и потом изменяем данные по всем Лингвистическим переменным (на одинаковые данные, передаваемые в lingVarWithEmployeeInfo),
            //связанным с данным сотрудником по UUID, который также передаётся в lingVarWithEmployeeInfo (:)
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            //HttpHeaders lingVarInfoHeaders = new HttpHeaders();
            //employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            //HttpEntity<EmployeeInfo> requestLingVarInfoEntity = new HttpEntity<>(lingVarInfo, lingVarInfoHeaders);
            ResponseEntity<List<LingVarInfo>> lingVarInfoResponse =
                    restTemplate.exchange(READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT
                                    + String.valueOf(employeeInfo.getEmployeeUuid()), //"http://localhost:8191/ling_var_dict/read-by-emp-uuid-{employeeUuid}",
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

                lingVarInfo.setLingVarName(lingVarWithEmployeeInfo.getLingVarName());
                lingVarInfo.setLingVarTermLowVal(lingVarWithEmployeeInfo.getLingVarTermLowVal());
                lingVarInfo.setLingVarTermMedVal(lingVarWithEmployeeInfo.getLingVarTermMedVal());
                lingVarInfo.setLingVarTermHighVal(lingVarWithEmployeeInfo.getLingVarTermHighVal());

                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
                HttpHeaders lingVarInfoHeaders = new HttpHeaders();
                lingVarInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<LingVarInfo> requestLingVarInfoEntity = new HttpEntity<>(lingVarInfo, lingVarInfoHeaders);
                restTemplate.exchange(UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT,
                        HttpMethod.PUT, requestLingVarInfoEntity, new ParameterizedTypeReference<List<LingVarInfo>>() {
                        });

                logger.info("Another lingVarInfo in lingVarInfoList in updateEmployeeWithLingVarData() UPDATED");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateEmployeeWithLingVarData(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //N.B.: for Angular and authentication:
    //https://github.com/z17/GamePro100/blob/master/lesson-service/src/test/java/lesson/service/LessonsServiceTest.java
}
