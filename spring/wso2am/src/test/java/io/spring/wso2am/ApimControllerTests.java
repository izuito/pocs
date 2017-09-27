package io.spring.wso2am;

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

import io.spring.wso2am.dto.RegisterResponse;
import io.spring.wso2am.dto.TokenResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApimControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApimControllerTests.class);
	
	@Autowired
	private RestTemplate rt;

	@Test
	public void testRegister() throws Exception {
		ResponseEntity<RegisterResponse> rerr = register();
		LOGGER.info("Register >>> \n{}", rerr);
		Assert.assertEquals(HttpStatus.OK, rerr.getStatusCode());
	}
	
	@Test
	public void testToken() throws Exception {
		String scope = "apim:api_view";
		ResponseEntity<TokenResponse> retr = token(scope);
		LOGGER.info("Token >>> \n{}", retr);
		Assert.assertEquals(HttpStatus.OK, retr.getStatusCode());
	}

	private ResponseEntity<RegisterResponse> register() {
		String url = "http://localhost:8080/apim/register";
		return rt.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, RegisterResponse.class);
	}
	
	private ResponseEntity<TokenResponse> token(String scope) {
		String url = "http://localhost:8080/apim/token?scope="+scope;
		return rt.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, TokenResponse.class);
	}
	
}
