package io.spring.wso2;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2.controller.ApiController;
import io.spring.wso2.controller.ApiManagerController;
import io.spring.wso2.controller.ApplicationController;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.service.ApiService;
import io.spring.wso2.service.WSO2AccessService;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIInfoObjectWithBasicAPIDetails_;
import io.swagger.client.publisher.model.APIList;
import io.swagger.client.store.model.Application2;
import io.swagger.client.store.model.ApplicationInfoObjectWithBasicApplicationDetails;
import io.swagger.client.store.model.ApplicationList;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApiController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WSO2FlowTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(WSO2FlowTests.class);

	@Autowired
	private RestTemplate rt;

	@Autowired
	private WSO2Properties w;

	ResponseEntity<TokenResponse> retr;

	@Autowired
	private WSO2AccessService was;

	@Autowired
	private ApiService as;

	@Autowired
	private ApiManagerController amc;

	@Autowired
	private ApplicationController ac;

	@Test
	public void testApplicationList() throws Exception {
		ResponseEntity<ApplicationList> rea = applications();
		LOGGER.info("*** {}", rea);
		Assert.assertEquals(HttpStatus.OK, rea.getStatusCode());
		ApplicationList al = rea.getBody();
		for (ApplicationInfoObjectWithBasicApplicationDetails ad : al.getList()) {
			ResponseEntity<Application2> rea2 = applications(ad.getApplicationId());
			LOGGER.info("*** {}", rea2);
			Assert.assertEquals(HttpStatus.OK, rea2.getStatusCode());
		}
	}

	@Test
	public void testName() throws Exception {
		ResponseEntity<APIList> real = apis();

		Assert.assertEquals(HttpStatus.OK, real.getStatusCode());

		APIList al = real.getBody();

		for (APIInfoObjectWithBasicAPIDetails_ ao : al.getList()) {
			ResponseEntity<List> reao = apis(ao.getId());

			LOGGER.info("*** {}", reao);

			Assert.assertEquals(HttpStatus.OK, reao.getStatusCode());
		}
	}

	public String getAccessToken(String scope) {
		String url = "http://localhost:8080/api/am/token/scope/" + scope;
		retr = rt.postForEntity(url, HttpEntity.EMPTY, TokenResponse.class);
		return retr.getBody().getAccessToken();
	}

	public ResponseEntity<ApplicationList> applications() {
		String url = "http://127.0.0.1:8080/applications";
		return rt.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ApplicationList.class);
	}

	public ResponseEntity<Application2> applications(String applicationId) {
		String url = "http://127.0.0.1:8080/applications/" + applicationId;
		return rt.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Application2.class);
	}
	
	public ResponseEntity<APIList> apis() {
		String url = "http://127.0.0.1:8080/apis";
		return rt.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, APIList.class);
	}
	
	public ResponseEntity<List> apis(String id) {
		String url = "http://127.0.0.1:8080/apis/" + id;
		return rt.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, List.class);
	}

	public ResponseEntity<API> create() {
		String url = "http://127.0.0.1:8080/apis";
		return rt.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, API.class);
	}

	public API getAPI() {
		API api = new API();
		api.setName("SVA 1.0");
		api.setContext("/svaapi");
		api.setVersion("1.0");

		return api;
	}

}
