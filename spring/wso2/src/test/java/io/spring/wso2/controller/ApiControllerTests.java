package io.spring.wso2.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApiController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiControllerTests {

	@Autowired
	private MockMvc mock;
	
	@Test
	public void testGet() throws Exception {
		String apiId = "bbee4f64-ec81-452d-87b7-cbc030d6a3c8";
		
		MockHttpServletRequestBuilder msrb = get("/apis/" + apiId);

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();		
	}
	
//	@Ignore
	@Test
	public void test1GetAll() throws Exception {
		MockHttpServletRequestBuilder msrb = get("/apis");

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
}
