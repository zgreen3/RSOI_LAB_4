package smirnov.bn.apigateway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultMatcher;
import smirnov.bn.apigateway.info_model_patterns.BizDescWithEmployeeInfo;
import smirnov.bn.apigateway.info_model_patterns.LingVarWithEmployeeInfo;

import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class ApiGatewayApplicationTests {

    @Autowired
    private MockMvc mvc;
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplicationTests.class);
    private Gson gson = new GsonBuilder().create();

    private String employeeUuidStr = "9cbc6e2a-417d-4313-955c-fb58c2da7dc8"; //"d1833bb4-e453-4333-b784-8262fdbcdef8";

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String GATEWAY_API_SERVICE_PORT_STRING = "8194";
    private static final String GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING = "/gateway_API";
    private static final String GATEWAY_API_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + GATEWAY_API_SERVICE_PORT_STRING
            + GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING;

    private static final String READ_ALL_1_GATEWAY_API_GET_URI_STRING = "/ling_var_and_employee/read-all";
    private static final String UPDATE_ALL_1_GATEWAY_API_PUT_URI_STRING = "/ling_var_and_employee/update-ling_var_and_employee";
    private static final String UPDATE_ALL_2_GATEWAY_API_PUT_URI_STRING = "/biz_proc_desc/update-/biz_proc_desc_and_employee";

    @Before
    public void beforeTestSettingUp() throws Exception {
        logger.info("beforeTestSettingUp() - START");
        boolean connectionIsASuccess;
        //проверка, что ключевой сервис 3 (с информацией по сотрудникам) запущен:
        URL url = new URL("http://localhost:8193");
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            //int stubbedConnectionResponse = connection.getResponseCode();
            connectionIsASuccess = true;
        } catch (Exception e) {
            connectionIsASuccess = false;
        }
        Assume.assumeTrue(connectionIsASuccess);
    }

    //*****************************API_GATEWAY_[MULTI_API]_TESTS**************************************************************************
    @Test
    public void testFindAllLingVarWithEmployeeData() throws Exception {
        logger.info("testFindAllLingVarWithEmployeeData() - START");

        mvc.perform(get(GATEWAY_API_ABS_URI_COMMON_STRING + READ_ALL_1_GATEWAY_API_GET_URI_STRING
                //"http:localhost:8194/gateway_API/ling_var_and_employee/read-all"
        ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void testUpdateEmployeeWithLingVarData() throws Exception {
        logger.info("testUpdateEmployeeWithLingVarData() - START");

        LingVarWithEmployeeInfo lingVarWithEmployeeInfo = new LingVarWithEmployeeInfo();
        //N.B.: employeeId (в отличие от employeeUuid) никак не будет затронут в процессе обновления, поэтому можем передать сюда null:
        lingVarWithEmployeeInfo.setEmployeeId(null);
        lingVarWithEmployeeInfo.setEmployeeName("Employee_0_0_2");
        lingVarWithEmployeeInfo.setEmployeeEmail("myEmail_2_2@example.com");
        lingVarWithEmployeeInfo.setEmployeeLogin("MyName_2_2");
        lingVarWithEmployeeInfo.setEmployeeUuid(employeeUuidStr);
        lingVarWithEmployeeInfo.setLingVarName("Tester_New_0_0_2");
        lingVarWithEmployeeInfo.setLingVarTermLowVal(4);
        lingVarWithEmployeeInfo.setLingVarTermMedVal(5);
        lingVarWithEmployeeInfo.setLingVarTermHighVal(6);

        mvc.perform(put(GATEWAY_API_ABS_URI_COMMON_STRING + UPDATE_ALL_1_GATEWAY_API_PUT_URI_STRING)
                //"http:localhost:8194/gateway_API/ling_var_and_employee/update-ling_var_and_employee"
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(lingVarWithEmployeeInfo)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEmployeeWithBizDescData() throws Exception {
        logger.info("testUpdateEmployeeWithBizDescData() - START");

        BizDescWithEmployeeInfo bizDescWithEmployeeInfo = new BizDescWithEmployeeInfo();
        //N.B.: employeeId (в отличие от employeeUuid) никак не будет затронут в процессе обновления, поэтому можем передать сюда null:
        bizDescWithEmployeeInfo.setEmployeeId(null);
        bizDescWithEmployeeInfo.setEmployeeName("Employee_0_0_2");
        bizDescWithEmployeeInfo.setEmployeeEmail("myEmail_2_2@example.com");
        bizDescWithEmployeeInfo.setEmployeeLogin("MyName_2_2");
        bizDescWithEmployeeInfo.setEmployeeUuid(employeeUuidStr);
        bizDescWithEmployeeInfo.setBizProcName("BizProc_0_0_1");
        bizDescWithEmployeeInfo.setBizProcDescStr("This is new business process description # 0_0_0");

        mvc.perform(put(GATEWAY_API_ABS_URI_COMMON_STRING + UPDATE_ALL_2_GATEWAY_API_PUT_URI_STRING)
                //"http:localhost:8194/gateway_API/ling_var_and_employee/update-ling_var_and_employee"
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(bizDescWithEmployeeInfo)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
