package io.spring.wso2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.wso2.controller.ThrottlingTierController;
import io.swagger.client.model.Tier;
import io.swagger.client.model.Tier.TierLevelEnum;
import io.swagger.client.model.Tier.TierPlanEnum;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ThrottlingTierController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThrottlingTierControllerTests {

	@Autowired
	private MockMvc mock;

	@Test
	public void test1CreateTier() throws Exception {
		MockHttpServletRequestBuilder msrb = post("/throttling/tier").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(tier()));
		mock.perform(msrb).andExpect(status().isCreated()).andDo(print()).andReturn();
	}

	@Test
	public void test2UpdateTier() throws Exception {
		Tier tier = tier().requestCount(10L);

		String tierName = "TTier";

		MockHttpServletRequestBuilder msrb = put("/throttling/tier/" + tierName).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(tier));

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void test3GetDetailsOfTier() throws Exception {
		String tierName = "TTier";

		MockHttpServletRequestBuilder msrb = get("/throttling/tier/" + tierName);

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void test4GetAllTiers() throws Exception {
		MockHttpServletRequestBuilder msrb = get("/throttling/tier");

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void test5DeleteTier() throws Exception {
		String tierName = "TTier";

		MockHttpServletRequestBuilder msrb = delete("/throttling/tier/" + tierName);

		mock.perform(msrb).andExpect(status().isOk()).andDo(print()).andReturn();
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

	private <T> String asJsonString(T t) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(t);
	}

}
