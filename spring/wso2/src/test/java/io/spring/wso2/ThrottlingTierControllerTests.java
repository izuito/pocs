package io.spring.wso2;

import java.util.HashMap;
import java.util.Map;

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

import io.spring.wso2.controller.ThrottlingTierController;
import io.swagger.client.model.Tier;
import io.swagger.client.model.Tier.TierLevelEnum;
import io.swagger.client.model.Tier.TierPlanEnum;
import io.swagger.client.model.TierList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThrottlingTierControllerTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(Connecting.class);

	@Autowired
	private ThrottlingTierController c;

	@Test
	public void test1CreateTier() throws Exception {
		Tier tier = tier();
		ResponseEntity<Tier> res = c.createTier(tier);
		Assert.assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}
	
	@Test
	public void test2UpdateTier() throws Exception {
		Tier tier = tier();
		tier.requestCount(10L);
		String tierName = tier.getName();
		ResponseEntity<Tier> res = c.updateTier(tierName , tier);
		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	public void test3GetDetailsOfTier() throws Exception {
		String tierName = "TTier";
		ResponseEntity<Tier> res = c.getTier(tierName);
		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	public void test4GetAllTiers() throws Exception {
		ResponseEntity<TierList> res = c.getAllTiers();
		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
		TierList tiers = res.getBody();
		Assert.assertEquals(new Integer(5), tiers.getCount());
	}
	
	@Test
	public void test5DeleteTier() throws Exception {
		String tierName = "TTier";
		ResponseEntity<Void> res = c.deleteTier(tierName);
		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	private Tier tier() {
		Tier tier = new Tier();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("a", "1");
		tier.attributes(attributes);
		tier.setDescription("Allows 5 request(s) per minute.");
		tier.setName("TTier");
		tier.setRequestCount(5L);
		tier.setStopOnQuotaReach(true);
		tier.setTierLevel(TierLevelEnum.API);
		tier.setTierPlan(TierPlanEnum.FREE);
		tier.setTimeUnit("");
		tier.setUnitTime(60000L);
		return tier;
	}

}
