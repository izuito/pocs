package io.spring.wso2am.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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

import io.spring.wso2am.controller.ApisController;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.API.TypeEnum;
import io.swagger.client.publisher.model.API.VisibilityEnum;
import io.swagger.client.publisher.model.APIInfo;
import io.swagger.client.publisher.model.APIList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApisControllerTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApisControllerTests.class);

	@Autowired
	private ApisController ac;

	// @Ignore
	@Test
	public void test1Create() throws Exception {
		API api = api();
		ResponseEntity<API> rea = ac.create(api);
		LOGGER.info("Create: \n{}", rea);
		Assert.assertEquals(HttpStatus.CREATED, rea.getStatusCode());
	}

	// @Ignore
	@Test
	public void test2Update() throws Exception {
		String apiId = getApiId();
		API api = api();
		api.description("API TEST APPLICATION");
		ResponseEntity<API> rea = ac.update(apiId, api);
		LOGGER.info("Update: \n{}", rea);
		Assert.assertEquals(HttpStatus.OK, rea.getStatusCode());
	}

	// @Ignore
	@Test
	public void test3ChangeStatus() throws Exception {
		String apiId = getApiId();
		ResponseEntity<Void> rev = ac.published(apiId);
		LOGGER.info("Publish: \n{}", rev);
		Assert.assertEquals(HttpStatus.OK, rev.getStatusCode());
	}

	@Test
	public void test5GetAll() throws Exception {
		ResponseEntity<APIList> real = ac.get();
		LOGGER.info("GetAll: \n{}", real);
		Assert.assertEquals(HttpStatus.OK, real.getStatusCode());
	}

	 @Ignore
	@Test
	public void test6Delete() throws Exception {
		String apiId = getApiId();
		ResponseEntity<Void> rev = ac.delete(apiId);
		LOGGER.info("Delete: \n{}", rev);
		Assert.assertEquals(HttpStatus.OK, rev.getStatusCode());
	}

	private String getApiId() {
		String name = "api-test-app";
		ResponseEntity<APIInfo> reai = ac.get(name);
		APIInfo ai = reai.getBody();
		String apiId = ai.getId();
		return apiId;
	}

	private API api() {
		API a = new API();
		a.name("api-test-app");
		a.context("/apitestapp");
		a.version("1.0");
		a.provider("admin");
		a.apiDefinition(
				"{\"swagger\":\"2.0\",\"paths\":{\"/*\":{\"get\":{\"responses\":{\"200\":{\"description\":\"\"}},\"x-auth-type\":\"Application & Application User\",\"x-throttling-tier\":\"Unlimited\"}}},\"info\":{\"title\":\"apitestapp\",\"version\":\"1\"},\"securityDefinitions\":{\"default\":{\"type\":\"oauth2\",\"authorizationUrl\":\"https://10.200.47.11:8243/authorize\",\"flow\":\"implicit\",\"scopes\":{}}},\"basePath\":\"/apitestapp/1\",\"host\":\"10.200.47.11:8243\",\"schemes\":[\"https\",\"http\"]}");
		a.isDefaultVersion(Boolean.TRUE);
		a.type(TypeEnum.HTTP);
		List<String> transport = new ArrayList<>();
		transport.add("http");
		transport.add("https");
		a.transport(transport);
		ArrayList<String> tiers = new ArrayList<>();
		tiers.add("Unlimited");
		a.tiers(tiers);
		a.visibility(VisibilityEnum.PUBLIC);
		a.endpointConfig(
				"{\"production_endpoints\":{\"url\":\"http://www.google.com.br\",\"config\":null},\"implementation_status\":\"managed\",\"endpoint_type\":\"http\"}");
		a.status("PUBLISHED");
		return a;
	}

}
