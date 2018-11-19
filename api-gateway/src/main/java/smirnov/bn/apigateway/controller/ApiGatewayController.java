package smirnov.bn.apigateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.util.UriComponentsBuilder;
import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.EmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarWithEmployeeInfo;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/gateway_API")
public class ApiGatewayController {

    // https://stackoverflow.com/questions/30528255/how-to-access-a-value-defined-in-the-application-properties-file-in-spring-boot (:)
    @Value("${server.port}")
    private int port;
    private String server = "localhost";

    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

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
                    restTemplate.exchange("http://localhost:8191/ling_var_dict/read-all",
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

    @PostMapping("/create-ling_var")
    public ResponseEntity<String> createLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        return this.proxingExternalRequests(lingVarInfo.toString(), HttpMethod.POST, request);
    }

    //http://localhost:8193/employees/create-employee
    ///*
    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy (:)
    //@RequestMapping("/**")
    @RequestMapping(value = {"/ling_var_dict/**", "/biz_proc_desc/**", "/employees/**"}, method = {GET, POST, PUT, DELETE})
    @ResponseBody
    public ResponseEntity<String> proxingExternalRequests(@RequestBody(required = false) String body,
                                                  HttpMethod method, HttpServletRequest request) //, HttpServletResponse response)
            throws URISyntaxException {
        URI uri = new URI("http", null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri)
                .path(request.getRequestURI())
                .query(request.getQueryString())
                .build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
    //*/


    //N.B.: for Angular and authentication:
    //https://github.com/z17/GamePro100/blob/master/lesson-service/src/test/java/lesson/service/LessonsServiceTest.java
}
