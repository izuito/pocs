package io.spring.wso2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApiManagerController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiManagerControllerTests {

	@Autowired
	private MockMvc mock;
	
	@Test
	public void testToken() throws Exception {
		String scope = "apim:subscribe";
		
		MockHttpServletRequestBuilder msrb = post("/api/am/token/scope/" + scope);

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();		
	}
	
}
