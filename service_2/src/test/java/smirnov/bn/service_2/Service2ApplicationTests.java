package smirnov.bn.service_2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class Service2ApplicationTests {

	@Autowired
	private MockMvc mvc;

	/*
    @Test
	public void contextLoads() {
	}
    //*/

	@Test
	public void testFindAllLingVars() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/biz_proc_desc/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*]").isArray())
		;//.andReturn();
		//.andExpect(content().string(containsString("[]")));
	}

}
