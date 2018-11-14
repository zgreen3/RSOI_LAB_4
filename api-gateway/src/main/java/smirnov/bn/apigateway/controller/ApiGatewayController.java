package smirnov.bn.apigateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.EmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarWithEmployeeInfo;

@RestController
@RequestMapping("/gateway_API")
public class ApiGatewayController {

    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

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

            //

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

            /*
            List<LingVarWithEmployeeInfo> lingVarWithEmployeeInfoList = new ArrayList<>();
            for (LingVarInfo lingVarInfo : lingVarInfoList) {
                LingVarWithEmployeeInfo lingVarWithEmployeeInfo = new LingVarWithEmployeeInfo();
                lingVarWithEmployeeInfo.setLingVarId(lingVarInfo.getLingVarId());
                lingVarWithEmployeeInfo.setLingVarName(lingVarInfo.getLingVarName());
                lingVarWithEmployeeInfo.setLingVarTermLowVal(lingVarInfo.getLingVarTermLowVal());
                lingVarWithEmployeeInfo.setLingVarTermMedVal(lingVarInfo.getLingVarTermMedVal());
                lingVarWithEmployeeInfo.setLingVarTermHighVal(lingVarInfo.getLingVarTermHighVal());

                //...

                lingVarWithEmployeeInfoList.add(lingVarWithEmployeeInfo);
            }
            //*/

            return new ResponseEntity<List<LingVarWithEmployeeInfo>>(lingVarWithEmployeeInfoList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findAllLingVarWithEmployeeData(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    //N.B.: for Angular and authentication:
    //https://github.com/z17/GamePro100/blob/master/lesson-service/src/test/java/lesson/service/LessonsServiceTest.java
}
