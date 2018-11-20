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
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.EmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarWithEmployeeInfo;

@RestController
@RequestMapping("/gateway_API")
public class ApiGatewayController {

    private static final String MAIN_WEB_SERVER_STRING = "http://localhost:";

    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;

    private static final String CREATE_LNG_VR_POST_URI_STRING = "/create-ling_var";
    private static final String READ_BY_ID_LNG_VR_GET_URI_STRING = "/read-";
    private static final String READ_ALL_LNG_VR_GET_URI_STRING = "/read-all";
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_STRING = "/read-all-paginated";
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_STRING = "/update-ling_var";
    private static final String DELETE_LNG_VR_DELETE_URI_STRING = "/delete-";

    private static final String CREATE_LNG_VR_POST_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + CREATE_LNG_VR_POST_URI_STRING;
    private static final String DELETE_LNG_VR_DELETE_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + DELETE_LNG_VR_DELETE_URI_STRING;
    private static final String READ_BY_ID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING;
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING;

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

    //"/create-ling_var" (:)
    @PostMapping(SERVICE_1_URI_COMMON_DIR_STRING + CREATE_LNG_VR_POST_URI_STRING)
    public ResponseEntity<String> createLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.POST, request,
                CREATE_LNG_VR_POST_URI_TMPLT);
    }

    //"/read-{id}" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING + "-{id}")
    public ResponseEntity<String> findLingVarById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_LNG_VR_GET_URI_TMPLT + '{' + String.valueOf(id) + '}');
    }

    //"/read-all" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_ALL_LNG_VR_GET_URI_STRING)
    public ResponseEntity<String> findAllLingVars(HttpServletRequest request)
            throws URISyntaxException {
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_LNG_VR_GET_URI_TMPLT);
    }

    //"/read-all-paginated" (:)
    @RequestMapping(value = READ_ALL_PGNTD_LNG_VR_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllLingVarPaginated(HttpServletRequest request,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                                            READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?" + "page:" + String.valueOf(page) + "&" +
                                            "sizeLimit:" + String.valueOf(sizeLimit));
    }

    //"/update-ling_var" (:)
    @PutMapping(SERVICE_1_URI_COMMON_DIR_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING)
    public ResponseEntity<String> updateLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT);
    }

    //"/delete-{lingVarId}" (:)
    @DeleteMapping(SERVICE_1_URI_COMMON_DIR_STRING + DELETE_LNG_VR_DELETE_URI_STRING)
    public ResponseEntity<String> deleteLingVarById(HttpServletRequest request, @PathVariable Integer lingVarId)
            throws URISyntaxException {
        return this.proxingExternalRequests(null, HttpMethod.POST, request,
                DELETE_LNG_VR_DELETE_URI_TMPLT + '{' + String.valueOf(lingVarId) + '}');
    }

    @GetMapping("/ling_var-read-all")
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
                    restTemplate.exchange("http://localhost:8193/employees/read-all",
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

    //N.B.: for Angular and authentication:
    //https://github.com/z17/GamePro100/blob/master/lesson-service/src/test/java/lesson/service/LessonsServiceTest.java
}
