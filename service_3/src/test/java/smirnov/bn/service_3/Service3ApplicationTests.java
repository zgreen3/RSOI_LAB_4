package smirnov.bn.service_3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

//https://stackoverflow.com/questions/47446529/what-is-the-difference-between-springjunit4classrunner-and-springrunner (:)
@RunWith(SpringRunner.class) //@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class Service3ApplicationTests {

    @Autowired
    private MockMvc mvc;
    private String employeeUuidStr;

    @Before
    public void beforeTest() throws Exception {
        //create-employee (:)
        MvcResult result =
                mvc.perform(post("http://localhost:8193/employees/create-employee").contentType(MediaType.APPLICATION_JSON).
                        content("{\n" +
                                "\t\"employeeId\" : \"\",\n" +
                                "\t\"employeeName\" : \"Employee_0\",\n" +
                                "\t\"employeeEmail\" : \"myEmail_0@example.com\",\n" +
                                "\t\"employeeLogin\" : \"MyName_0\",\n" +
                                "\t\"employeeUuid\" : \"\"\n" +
                                //"\t\"employeeUuid\" : \"1c2106bc-6cab-4452-8431-b102ca03fc50\"\n" +
                                "}")).andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        employeeUuidStr = result.getResponse().getContentAsString();
    }

    @After
    public void AfterTest() throws Exception {
        mvc.perform(delete("http://localhost:8193/employees/delete-" + employeeUuidStr).
                param("employeeUuid", employeeUuidStr)).andDo(print())
                .andExpect(status().isOk());
    }

    ///*
    @Test
    //@WithMockUser(roles = "postgre_srvc_3_usr")
    public void testFindAllEmployees() throws Exception {
        mvc.perform(get("/employees/read-all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray())
        ;//.andReturn();
        //.andExpect(content().string(containsString("[]")));
    }
    //*/

    //https://stackoverflow.com/questions/18336277/how-to-check-string-in-response-body-with-mockmvc
    //+ https://stackoverflow.com/questions/30482934/how-to-check-json-in-response-body-with-mockmvc (:)
    @Test
    public void testFindEmployeeByUuid() throws Exception {
        mvc.perform(get("http://localhost:8193/employees/read-" + employeeUuidStr).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeUuid", is(employeeUuidStr)));
    }
}
