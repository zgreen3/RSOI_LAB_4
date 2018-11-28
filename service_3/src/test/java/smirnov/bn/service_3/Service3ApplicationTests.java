package smirnov.bn.service_3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import java.util.UUID;

import smirnov.bn.service_3.model.EmployeeInfo;


//https://stackoverflow.com/questions/47446529/what-is-the-difference-between-springjunit4classrunner-and-springrunner (:)
@RunWith(SpringRunner.class) //@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class Service3ApplicationTests {

    @Autowired
    private MockMvc mvc;
    private static final Logger logger = LoggerFactory.getLogger(Service3ApplicationTests.class);
    private Gson gson = new GsonBuilder().create();

    private String employeeUuidStr;

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
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

    @Before
    public void beforeTestSettingUp() throws Exception {
        logger.info("beforeTestSettingUp() - START");

        //create-employee (:)
        EmployeeInfo employeeInfoObj = new EmployeeInfo();
        employeeInfoObj.setEmployeeId(0);
        employeeInfoObj.setEmployeeName("Employee_0");
        employeeInfoObj.setEmployeeLogin("MyName_0");
        employeeInfoObj.setEmployeeEmail("myEmail_0@example.com");
        //employeeInfoObj.setEmployeeUuid(UUID.fromString(employeeUuidStr));
        String employeeInfoJsonObj = gson.toJson(employeeInfoObj);
        MvcResult result_emp =
                mvc.perform(post(CREATE_EMP_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content(//"{\n" +
                                //"\t\"employeeId\" : \"\",\n" +
                                //"\t\"employeeName\" : \"Employee_0\",\n" +
                                //"\t\"employeeEmail\" : \"myEmail_0@example.com\",\n" +
                                //"\t\"employeeLogin\" : \"MyName_0\",\n" +
                                //"\t\"employeeUuid\" : \"\"\n" +
                                //"}"
                                employeeInfoJsonObj
                        )).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        employeeUuidStr = result_emp.getResponse().getContentAsString();
    }

    @After
    public void afterTestCompletion() throws Exception {
        logger.info("afterTestCompletion() - START");
        mvc.perform(delete(DELETE_EMP_DELETE_URI_TMPLT + employeeUuidStr).
                param("employeeUuid", employeeUuidStr)).andDo(print())
                .andExpect(status().isOk());
    }

    //https://stackoverflow.com/questions/18336277/how-to-check-string-in-response-body-with-mockmvc
    //+ https://stackoverflow.com/questions/30482934/how-to-check-json-in-response-body-with-mockmvc (:)
    @Test
    public void testFindEmployeeByUuid() throws Exception {
        logger.info("testFindEmployeeByUuid() - START");
        mvc.perform(get(READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuidStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeUuid", is(employeeUuidStr)));
    }

    @Test
    public void testFindAllEmployees() throws Exception {
        logger.info("testFindAllEmployees() - START");
        mvc.perform(get(READ_ALL_EMP_GET_URI_TMPLT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void testFindAllEmployeesPaginated() throws Exception {
        logger.info("testFindAllEmployeesPaginated() - START");
        mvc.perform(get(READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?page=0&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?page=1&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?page=100&sizeLimit=100").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        logger.info("testFindAllEmployeesPaginated() - END");
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        logger.info("testUpdateEmployee() - START");
        EmployeeInfo employeeInfoObj = new EmployeeInfo();
        //employeeInfoObj.setEmployeeId(0);
        employeeInfoObj.setEmployeeName("Employee_0_0_1");
        employeeInfoObj.setEmployeeLogin("MyName_0_0_1");
        employeeInfoObj.setEmployeeEmail("myEmail_0_0_1@example.com");
        employeeInfoObj.setEmployeeUuid(UUID.fromString(employeeUuidStr));
        String employeeInfoJsonObj = gson.toJson(employeeInfoObj);
        mvc.perform(put(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT).contentType(MediaType.APPLICATION_JSON)
                .content(//"{\n" +
                        //"\t\"employeeId\" : \"\",\n" +
                        //"\t\"employeeName\" : \"Employee_0_0_1\",\n" +
                        //"\t\"employeeEmail\" : \"myEmail_0_0_1@example.com\",\n" +
                        //"\t\"employeeLogin\" : \"MyName_0_0_1\",\n" +
                        //"\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                        //"}"
                        employeeInfoJsonObj
                )).andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get(READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuidStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeUuid", is(employeeUuidStr)))
                .andExpect(jsonPath("$.employeeName", is("Employee_0_0_1")));
        logger.info("testUpdateEmployee() - END");
    }
}
