package io.spring.wso2am;

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

import io.spring.wso2am.controller.ThrottlingTierController;
import io.swagger.client.publisher.model.Tier;
import io.swagger.client.publisher.model.Tier.TierLevelEnum;
import io.swagger.client.publisher.model.Tier.TierPlanEnum;
import io.swagger.client.publisher.model.TierList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThrottlingTierControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThrottlingTierControllerTests.class);
	
	@Autowired
	private ThrottlingTierController ttc;
	
	@Test
	public void test1CreateTier() throws Exception {
		Tier t = tier();
		ResponseEntity<Tier> ret = ttc.create(t);
		LOGGER.info("{}", ret);
		Assert.assertEquals(HttpStatus.CREATED, ret.getStatusCode());
	}
	
	@Test
	public void test2Update() throws Exception {
		String tierName = "TTier";
		Tier t = tier();
		t.requestCount(60L);
		t.description("Allows 60 request(s) per minute.");
		ResponseEntity<Tier> ret = ttc.update(tierName , t);
		LOGGER.info("{}", ret);
		Assert.assertEquals(HttpStatus.OK, ret.getStatusCode());
	}
	
	@Test
	public void test3Get() throws Exception {
		String tierName = "TTier";
		ResponseEntity<Tier> ret = ttc.get(tierName);
		LOGGER.info("{}", ret);
		Assert.assertEquals(HttpStatus.OK, ret.getStatusCode());
	}
	
	@Test
	public void test4GetAll() throws Exception {
		ResponseEntity<TierList> retl = ttc.get();
		LOGGER.info("{}", retl);
		Assert.assertEquals(HttpStatus.OK, retl.getStatusCode());
	}
	
	@Ignore
	@Test
	public void test5Delete() throws Exception {
		String tierName = "TTier";
		ResponseEntity<Void> rev = ttc.delete(tierName);
		LOGGER.info("{}", rev);
		Assert.assertEquals(HttpStatus.OK, rev.getStatusCode());
	}
	
	private Tier tier() {
		Tier tier = new Tier();
//		Map<String, String> attributes = new HashMap<>();
//		attributes.put("a", "1");
//		tier.attributes(attributes);
//		tier.setDescription("Allows 5 request(s) per minute.");
		tier.setName("TTier");
		tier.setRequestCount(5L);
		tier.setStopOnQuotaReach(true);
		tier.setTierLevel(TierLevelEnum.API);
		tier.setTierPlan(TierPlanEnum.FREE);
//		tier.setTimeUnit("");
		tier.setUnitTime(60000L);
		return tier;
	}
	
}
