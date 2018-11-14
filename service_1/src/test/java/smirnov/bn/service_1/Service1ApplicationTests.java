package smirnov.bn.service_1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smirnov.bn.service_1.model.LingVarInfo;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import smirnov.bn.service_1.entity.LingVar;
import smirnov.bn.service_1.repository.LingVarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class Service1ApplicationTests {

	@Autowired
	private MockMvc mvc;

	/*
    @Test
	public void contextLoads() {
	}
    //*/

    @Test
    public void testFindAllLingVars() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ling_var_dict/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray())
                ;//.andReturn();
                //.andExpect(content().string(containsString("[]")));
    }

    /*
    private LingVarInfo lingVarInfo;
    private LingVar lingVar;
    @MockBean
    private LingVarRepository lingVarRepository;

    @Before
    private void setupLingVarInfo() {
        lingVarInfo = new LingVarInfo();
        lingVarInfo.setLingVarName(lingVar.getLingVarName());
        lingVarInfo.setLingVarTermLowVal(lingVar.getLingVarTermLowVal());
        lingVarInfo.setLingVarTermMedVal(lingVar.getLingVarTermMedVal());
        lingVarInfo.setLingVarTermHighVal(lingVar.getLingVarTermHighVal());
    }

    @Test
    public void lingVarByIdReturnsCorrectResponse() throws Exception {
        given(lingVarRepository.findAll()).willReturn(Arrays.asList(person));
        final ResultActions result = mockMvc.perform(get(BASE_PATH));
        result.andExpect(status().isOk());
        result
                .andExpect(jsonPath("links[0].rel", is("self")))
                .andExpect(jsonPath("links[0].href", is(BASE_PATH)))
                .andExpect(jsonPath("content[0].person.id", is(person.getId().intValue())))
                .andExpect(jsonPath("content[0].person.id", is(person.getId().intValue())))
                .andExpect(jsonPath("content[0].person.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("content[0].person.secondName", is(person.getSecondName())))
                .andExpect(
                        jsonPath(
                                "content[0].person.dateOfBirth", is(person.getDateOfBirth().format(formatter))))
                .andExpect(jsonPath("content[0].person.profession", is(person.getProfession())))
                .andExpect(jsonPath("content[0].person.salary", is(person.getSalary())))
                .andExpect(jsonPath("content[0].links[0].rel", is("people")))
                .andExpect(jsonPath("content[0].links[0].href", is(BASE_PATH)))
                .andExpect(jsonPath("content[0].links[1].rel", is("memberships")))
                .andExpect(
                        jsonPath("content[0].links[1].href", is(BASE_PATH + "/" + ID + MEMBERSHIPS_PATH)))
                .andExpect(jsonPath("content[0].links[2].rel", is("self")))
                .andExpect(jsonPath("content[0].links[2].href", is(BASE_PATH + "/" + ID)));
    }
    //*/

}
