package io.spring.wso2am.controller;

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

import io.swagger.client.publisher.model.SubscriptionList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubscriptionsControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsControllerTests.class);

	@Autowired
	private SubscriptionsController sc;
	
	@Test
	public void test5GetAll() throws Exception {
		ResponseEntity<SubscriptionList> resl = sc.get();
		LOGGER.info("Create: \n{}", resl);
		Assert.assertEquals(HttpStatus.OK, resl.getStatusCode());
	}
	
}
