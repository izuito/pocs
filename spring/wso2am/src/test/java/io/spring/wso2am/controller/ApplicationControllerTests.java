package io.spring.wso2am.controller;

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

import io.spring.wso2am.controller.ApplicationController;
import io.swagger.client.store.model.Application;
import io.swagger.client.store.model.ApplicationInfoObjectWithBasicApplicationDetails;
import io.swagger.client.store.model.ApplicationList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationControllerTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationControllerTests.class);
	
	@Autowired
	private ApplicationController ac;
	
	@Ignore
	@Test
	public void test1Create() throws Exception {
		Application a = application();
		ResponseEntity<Application> rea = ac.create(a);
		LOGGER.info("Application: \n{}", rea);
		Assert.assertEquals(HttpStatus.CREATED, rea.getStatusCode());
	}
	
	@Test
	public void test2GetAll() throws Exception {
		ResponseEntity<ApplicationList> real = ac.get();
		LOGGER.info("Application: \n{}", real);
		Assert.assertEquals(HttpStatus.OK, real.getStatusCode());
	}
	
	@Test
	public void test2GetByName() throws Exception {
		String name = "app-test";
		ResponseEntity<ApplicationInfoObjectWithBasicApplicationDetails> read = ac.get(name);
		LOGGER.info("Application: \n{}", read);
		Assert.assertEquals(HttpStatus.OK, read.getStatusCode());
	}
	
	private Application application() {
		Application a = new Application();
		a.throttlingTier("Unlimited");
		a.description("App with TTier");
		a.name("appttier");
		a.callbackUrl("http://www.google.com.br");
		return a;
	}
	
}
