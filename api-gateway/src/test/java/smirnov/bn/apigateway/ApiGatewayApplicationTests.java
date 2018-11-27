package smirnov.bn.apigateway;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)//(SpringRunner.class)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ApiGatewayApplicationTests {

    @Autowired
    private MockMvc mvc;

    private String lingVarIdStr;
    private String bizProcIdStr;
    private String employeeUuidStr;

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String GATEWAY_API_SERVICE_PORT_STRING = "8194";
    private static final String GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING = "/gateway_API";

    private static final String SERVICE_1_PORT_STRING = GATEWAY_API_SERVICE_PORT_STRING; //"8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING + "/ling_var_dict";
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

    private static final String SERVICE_2_PORT_STRING = GATEWAY_API_SERVICE_PORT_STRING; //"8192";
    private static final String SERVICE_2_URI_COMMON_DIR_STRING = GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING + "/biz_proc_desc/";
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

    private static final String SERVICE_3_PORT_STRING = GATEWAY_API_SERVICE_PORT_STRING; //"8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = GATEWAY_API_SERVICE_URI_COMMON_DIR_STRING + "/employees/";
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
        //create-employee (:)
        MvcResult result_emp =
                mvc.perform(post(CREATE_EMP_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content("{\n" +
                                "\t\"employeeId\" : \"\",\n" +
                                "\t\"employeeName\" : \"Employee_0\",\n" +
                                "\t\"employeeEmail\" : \"myEmail_0@example.com\",\n" +
                                "\t\"employeeLogin\" : \"MyName_0\",\n" +
                                "\t\"employeeUuid\" : \"\"\n" +
                                "}")).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        employeeUuidStr = result_emp.getResponse().getContentAsString();

        //create-lingVar [Linguistic Variable] (:)
        MvcResult result_lngvr =
                mvc.perform(post(CREATE_LNG_VR_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content("{\n" +
                                "\t\"lingVarId\" : \"\",\n" +
                                "\t\"lingVarName\" : \"Tester_0\",\n" +
                                "\t\"lingVarTermLowVal\" : \"0\",\n" +
                                "\t\"lingVarTermMedVal\" : \"1\",\n" +
                                "\t\"lingVarTermHighVal\" : \"2\"\n" +
                                "\t\"employeeUuid\" : \"" + employeeUuidStr + "\",\n" +
                                "}")).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        lingVarIdStr = result_lngvr.getResponse().getContentAsString();

        //create-biz_proc_desc [Business Process Description] (:)
        MvcResult result_bpDsc =
                mvc.perform(post(CREATE_BP_DSC_POST_URI_TMPLT).contentType(MediaType.APPLICATION_JSON).
                        content("{\n" +
                                "\t\"bizProcId\" : \"\",\n" +
                                "\t\"bizProcName\" : \"BizProc_0\",\n" +
                                "\t\"bizProcDescStr\" : \"This is new business process description # 0\",\n" +
                                "\t\"employeeUuid\" : \"" + employeeUuidStr + "\",\n" +
                                "}")).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        bizProcIdStr = result_bpDsc.getResponse().getContentAsString();
    }

    @After
    public void afterTestCompletion() throws Exception {
        mvc.perform(delete(DELETE_LNG_VR_DELETE_URI_TMPLT + lingVarIdStr).
                param("lingVarId", lingVarIdStr)).andDo(print())
                .andExpect(status().isOk());

        mvc.perform(delete(DELETE_BP_DSC_DELETE_URI_TMPLT + bizProcIdStr).
                param("bizProcId", bizProcIdStr)).andDo(print())
                .andExpect(status().isOk());

        mvc.perform(delete(DELETE_EMP_DELETE_URI_TMPLT + employeeUuidStr).
                param("employeeUuid", employeeUuidStr)).andDo(print())
                .andExpect(status().isOk());
    }

    //*****************************API_GATEWAY_TESTS**************************************************************************

    //*****************************SERVICE_1_TESTS**************************************************************************
    @Test
    public void testFindLingVarById() throws Exception {
        mvc.perform(get(READ_BY_ID_LNG_VR_GET_URI_TMPLT + lingVarIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lingVarId", is(lingVarIdStr)));
    }
    /*
    @Test
    public void testFindAllLingVars() throws Exception {
        mvc.perform(get(READ_ALL_LNG_VR_GET_URI_TMPLT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void testFindAllLingVarPaginated() throws Exception {
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
    }

    @Test
    public void testUpdateLingVar() throws Exception {
        mvc.perform(put(UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"lingVarId\" : \"" + lingVarIdStr + "\"\n" +
                        "\t\"lingVarName\" : \"Tester_0_0_1\",\n" +
                        "\t\"lingVarTermLowVal\" : \"1\",\n" +
                        "\t\"lingVarTermMedVal\" : \"2\",\n" +
                        "\t\"lingVarTermHighVal\" : \"3\",\n" +
                        "\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get(READ_BY_ID_LNG_VR_GET_URI_TMPLT + lingVarIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lingVarId", is(lingVarIdStr)))
                .andExpect(jsonPath("$.lingVarName", is("Tester_0_0_1")));
    }

    //*****************************SERVICE_2_TESTS**************************************************************************
    @Test
    public void testFindBusinessProcDescById() throws Exception {
        mvc.perform(get(READ_BY_ID_BP_DSC_GET_URI_TMPLT + bizProcIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bizProcId", is(bizProcIdStr)));
    }

    @Test
    public void testFindAllBusinessProcDescs() throws Exception {
        mvc.perform(get(READ_ALL_BP_DSC_GET_URI_TMPLT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void findAllBusinessProcDescPaginated() throws Exception {
        mvc.perform(get(READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT + "?page=0&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT + "?page=1&sizeLimit=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());

        mvc.perform(get(READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT + "?page=100&sizeLimit=100").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBusinessProcDesc() throws Exception {
        mvc.perform(put(UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"bizProcId\" : \"" + bizProcIdStr + "\"\n" +
                        "\t\"bizProcName\" : \"BizProc_0_0_1\",\n" +
                        "\t\"bizProcDescStr\" : \"This is new business process description # 1\",\n" +
                        "\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get(READ_BY_ID_BP_DSC_GET_URI_TMPLT + bizProcIdStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bizProcId", is(bizProcIdStr)))
                .andExpect(jsonPath("$.bizProcName", is("BizProc_0_0_1")))
                .andExpect(content().string(containsString("description # 1")));
    }

    //*****************************SERVICE_3_TESTS**************************************************************************
//https://stackoverflow.com/questions/18336277/how-to-check-string-in-response-body-with-mockmvc
//+ https://stackoverflow.com/questions/30482934/how-to-check-json-in-response-body-with-mockmvc (:)
    @Test
    public void testFindEmployeeByUuid() throws Exception {
        mvc.perform(get(READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuidStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeUuid", is(employeeUuidStr)));
    }

    @Test
    public void testFindAllEmployees() throws Exception {
        mvc.perform(get(READ_ALL_EMP_GET_URI_TMPLT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray());
    }

    @Test
    public void testFindAllEmployeesPaginated() throws Exception {
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
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        mvc.perform(put(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"employeeId\" : \"\",\n" +
                        "\t\"employeeName\" : \"Employee_0_0_1\",\n" +
                        "\t\"employeeEmail\" : \"myEmail_0_0_1@example.com\",\n" +
                        "\t\"employeeLogin\" : \"MyName_0_0_1\",\n" +
                        "\t\"employeeUuid\" : \"" + employeeUuidStr + "\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get(READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuidStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeUuid", is(employeeUuidStr)))
                .andExpect(jsonPath("$.employeeName", is("Employee_0_0_1")));
    }
    //*/
}
