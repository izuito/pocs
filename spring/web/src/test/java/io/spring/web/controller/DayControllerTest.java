package io.spring.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DayController.class, secure = false)
public class DayControllerTest {

	@Autowired
	private MockMvc mock;

	@Test
	public void validaVersao() throws Exception {
		MockHttpServletRequestBuilder builder = get("/");
		MvcResult result = mock.perform(builder).andDo(print()).andReturn();
		String expected = "1.0";
		Object actual = result.getResponse().getContentAsString();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testName() throws Exception {
		MvcResult result = mock.perform(get("/day")).andDo(print()).andReturn();
		
		String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		
		String expected = "{\"date\":\""+date+"\"}";
		String actual = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(expected, actual, false);
	}
	
}
