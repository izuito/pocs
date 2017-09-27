package io.spring.wso2am;

import java.net.URI;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.controller.ApimController;
import io.spring.wso2am.dto.RegisterResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApimControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApimControllerTests.class);
	
	@Autowired
	private RestTemplate rt;

	@Autowired
	private ApimController ac;
	
	@Test
	public void testRegister() throws Exception {
		ResponseEntity<RegisterResponse> rerr = register();
		LOGGER.info("*** {}", rerr);
		Assert.assertEquals(HttpStatus.OK, rerr.getStatusCode());
	}
	
	private ResponseEntity<RegisterResponse> register() {
		String url = "http://localhost:8080/apim/register";
		return rt.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, RegisterResponse.class);
	}
	
}
