package smirnov.bn.service_1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
//import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import smirnov.bn.service_1.model.LingVarInfo;
//import smirnov.bn.service_1.web.LingVarController;

/*
class EmployeeInfo {

    String employeeId;

    String employeeName;

    String employeeEmail;

    String employeeLogin;

    UUID employeeUuid;

    public EmployeeInfo(String employeeId, String employeeName, String employeeEmail, String employeeLogin, UUID employeeUuid) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeLogin = employeeLogin;
        this.employeeUuid = employeeUuid;
    }
}
//*/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//https://stackoverflow.com/questions/29669393/override-default-spring-boot-application-properties-settings-in-junit-test (:)
@TestPropertySource(locations = "classpath:test.properties")
public class Service1ApplicationTests {

    @Autowired
    private MockMvc mvc;
    private static final Logger logger = LoggerFactory.getLogger(Service1ApplicationTests.class);
    private Gson gson = new GsonBuilder().create();

    private String lingVarIdStr;
    private String employeeUuidStr = "d1833bb4-e453-4333-b784-8262fdbcdef8";

    //private static final String CREATE_EMP_POST_URI_TMPLT = "http://localhost:8193/employees/create-employee";
    //private static final String DELETE_EMP_DELETE_URI_TMPLT = "http://localhost:8193/employees/delete-";

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict/";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;

    private static final String CREATE_LNG_VR_POST_URI_STRING = "create-ling_var";
    private static final String READ_BY_ID_LNG_VR_GET_URI_STRING = "read-";
    private static final String READ_ALL_LNG_VR_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_STRING = "update-ling_var";
    private static final String DELETE_LNG_VR_DELETE_URI_STRING = "delete-";

    private static final String CREATE_LNG_VR_POST_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + CREATE_LNG_VR_POST_URI_STRING;
    private static final String READ_BY_ID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING;
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING;
    private static final String DELETE_LNG_VR_DELETE_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + DELETE_LNG_VR_DELETE_URI_STRING;

    @Before
    public void beforeTestSettingUp() throws Exception {
        logger.info("beforeTestSettingUp() - START");
        //create-employee (:)
        /*
        EmployeeInfo empInfoObj = new EmployeeInfo("0", "Employee_0", "emp_mail_0@example.com", "My_login_0", UUID.fromString("d1833bb4-e453-4333-b784-8262fdbcdef8"));
        String empInfObjJsonStr = gson.toJson(empInfoObj);
        MvcResult result_emp =
                mvc.perform(post(CREATE_EMP_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content(//"{\n" +
                                //"\t\"employeeId\" : \"0\",\n" +
                                //"\t\"employeeName\" : \"Employee_0\",\n" +
                                //"\t\"employeeEmail\" : \"myEmail_0@example.com\",\n" +
                                //"\t\"employeeLogin\" : \"MyName_0\"\n" +
                                //"\t\"employeeUuid\" : \"0\"\n" +
                                //"}"
                                empInfObjJsonStr
                        )).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        logger.info("got result_emp, success");
        employeeUuidStr = result_emp.getResponse().getContentAsString();
        logger.info("got employeeUuidStr, success");
        //*/

        //create-lingVar [Linguistic Variable] (:)
        LingVarInfo lngVarInfoObj = new LingVarInfo();
        lngVarInfoObj.setLingVarId(0);
        lngVarInfoObj.setLingVarName("Tester_0");
        lngVarInfoObj.setLingVarTermLowVal(0);
        lngVarInfoObj.setLingVarTermMedVal(1);
        lngVarInfoObj.setLingVarTermHighVal(2);
        lngVarInfoObj.setEmployeeUuid(employeeUuidStr);
        String lngVarInfoJsonObj = gson.toJson(lngVarInfoObj);
        MvcResult result_lngvr =
                mvc.perform(post(CREATE_LNG_VR_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content(//"{\n" +
                                //"\t\"lingVarId\" : \"0\",\n" +
                                //"\t\"lingVarName\" : \"Tester_0\",\n" +
                                //"\t\"lingVarTermLowVal\" : \"0\",\n" +
                                //"\t\"lingVarTermMedVal\" : \"1\",\n" +
                                //"\t\"lingVarTermHighVal\" : \"2\",\n" +
                                //"\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                                //"}"
                                lngVarInfoJsonObj
                        )).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        lingVarIdStr = result_lngvr.getResponse().getContentAsString();
    }

    @After
    public void afterTestCompletion() throws Exception {
        logger.info("afterTestCompletion() - START");
        mvc.perform(delete(DELETE_LNG_VR_DELETE_URI_TMPLT + lingVarIdStr).
                param("lingVarId", lingVarIdStr)).andDo(print())
                .andExpect(status().isOk());

        /*
        mvc.perform(delete(DELETE_EMP_DELETE_URI_TMPLT + employeeUuidStr).
                param("employeeUuid", employeeUuidStr)).andDo(print())
                .andExpect(status().isOk());
        //*/
    }

    @Test
    public void testFindLingVarById() throws Exception {
        logger.info("testFindLingVarById() - START");
        mvc.perform(get(READ_BY_ID_LNG_VR_GET_URI_TMPLT + lingVarIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lingVarId", is(Integer.parseInt(lingVarIdStr))));
    }

    @Test
    public void testFindAllLingVars() throws Exception {
        logger.info("testFindAllLingVars() - START");
        mvc.perform(get(READ_ALL_LNG_VR_GET_URI_TMPLT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void testFindAllLingVarPaginated() throws Exception {
        logger.info("testFindAllLingVarPaginated() - START");
        mvc.perform(get(READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?page=0&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?page=1&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?page=100&sizeLimit=100").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        logger.info("testFindAllLingVarPaginated() - END");
    }

    @Test
    public void testUpdateLingVar() throws Exception {
        logger.info("testUpdateLingVar() - START");
        LingVarInfo lngVarInfoObj = new LingVarInfo();
        lngVarInfoObj.setLingVarId(Integer.valueOf(lingVarIdStr));
        lngVarInfoObj.setLingVarName("Tester_0_0_1");
        lngVarInfoObj.setLingVarTermLowVal(1);
        lngVarInfoObj.setLingVarTermMedVal(2);
        lngVarInfoObj.setLingVarTermHighVal(3);
        lngVarInfoObj.setEmployeeUuid(employeeUuidStr);
        String lngVarInfoJsonObj = gson.toJson(lngVarInfoObj);
        mvc.perform(put(UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT).contentType(MediaType.APPLICATION_JSON)
                .content(//"{\n" +
                        //"\t\"lingVarId\" : \"" + lingVarIdStr + "\",\n" +
                        //"\t\"lingVarName\" : \"Tester_0_0_1\",\n" +
                        //"\t\"lingVarTermLowVal\" : \"1\",\n" +
                        //"\t\"lingVarTermMedVal\" : \"2\",\n" +
                        //"\t\"lingVarTermHighVal\" : \"3\",\n" +
                        //"\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                        //"}"
                        lngVarInfoJsonObj
                )).andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get(READ_BY_ID_LNG_VR_GET_URI_TMPLT + lingVarIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lingVarId", is(Integer.parseInt(lingVarIdStr))))
                .andExpect(jsonPath("$.lingVarName", is("Tester_0_0_1")));
        logger.info("testUpdateLingVar() - END");
    }
}
