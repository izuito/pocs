package io.spring.wso2;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.wso2.controller.ApiManagerController;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Api.Get;
import io.spring.wso2.service.ApiService;
import io.spring.wso2.service.WSO2AccessService;
import io.swagger.client.publisher.model.APIList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Wso2Tests {

	private static final Logger LOGGER = LoggerFactory.getLogger(Wso2Tests.class);

	@Autowired
	private WSO2Properties w;
	
	@Autowired
	private WSO2AccessService was;
	
	@Autowired
	private ApiService as;
	
	@Autowired
	private ApiManagerController amc;
	
	@Test
	public void test1Register() throws Exception {
//		ResponseEntity<RegisterResponse> rerr = was.register();
		
		ResponseEntity<RegisterResponse> rerr = amc.register();
		
		LOGGER.info("*** {}", rerr);
		
		Assert.assertEquals(HttpStatus.OK, rerr.getStatusCode());
	}
	
	@Test
	public void test2Token() throws Exception {
		Get get = w.getApi().getGet();
		
//		ResponseEntity<RegisterResponse> rerr = was.register();
//		RegisterResponse rr = rerr.getBody();
//		ResponseEntity<TokenResponse> retr = was.token(rr.authorization(), get.getScope());
//		TokenResponse tr = retr.getBody();
		
		ResponseEntity<TokenResponse> retr = amc.token(get.getScope());
		
		LOGGER.info("*** {}", retr);
		
		Assert.assertEquals(HttpStatus.OK, retr.getStatusCode());
	}
	
	@Test
	public void test3SearchApis() throws Exception {
		Get get = w.getApi().getGet();
		
//		ResponseEntity<RegisterResponse> rerr = was.register();
//		RegisterResponse rr = rerr.getBody();
//		ResponseEntity<TokenResponse> retr = was.token(rr.authorization(), get.getScope());
		
		ResponseEntity<TokenResponse> retr = amc.token(get.getScope());		
		TokenResponse tr = retr.getBody();
		
		ResponseEntity<APIList> real = as.getSearchApis(tr.authorization(), get.getScope());
		
		LOGGER.info("*** {}", real);
		
		Assert.assertEquals(HttpStatus.OK, real.getStatusCode());
	}
	
	@Test
	public void test4GetApi() throws Exception {
		Get get = w.getApi().getGet();
		
//		ResponseEntity<RegisterResponse> rerr = was.register();
//		RegisterResponse rr = rerr.getBody();
//		ResponseEntity<TokenResponse> retr = was.token(rr.authorization(), get.getScope());
//		TokenResponse tr = retr.getBody();
		
		ResponseEntity<TokenResponse> retr = amc.token(get.getScope());		
		TokenResponse tr = retr.getBody();		
		
		String apiId = "bbee4f64-ec81-452d-87b7-cbc030d6a3c8";
		ResponseEntity<Object> reo = as.getApi(apiId, tr.authorization(), get.getContentType());
		
		LOGGER.info("*** {}", reo);
		
		Assert.assertEquals(HttpStatus.OK, reo.getStatusCode());
	}

}
