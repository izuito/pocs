package io.spring.wso2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Create;
import io.spring.wso2.properties.WSO2Properties.Token;
import io.swagger.client.model.Tier;
import io.swagger.client.model.Tier.TierLevelEnum;
import io.swagger.client.model.Tier.TierPlanEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
public class ThrottlingTierTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(Connecting.class);
	
	@Autowired
	private RestTemplate rt;

	@Autowired
	private WSO2Properties w;

//	@Test
//	public void testName() throws Exception {
//		
//		ThrottlingTier tt = w.getThrottlingTier();
//
//		Create create = tt.getCreate();
//
//		LOGGER.info("{}", create.getUrl());
//
//	}

	@Test
	public void testCreateTier() throws Exception {
		ThrottlingTier tt = w.getThrottlingTier();
		
		Create create = tt.getCreate();

		TokenResponse token = getTokenByScope(create.getScope());
		
		create.setAuthorization(token.authorization());

		Tier tier = tier();

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", create.getAuthorization());
		headers.add("Content-Type", create.getContentType());

		HttpEntity<Tier> he = new HttpEntity<Tier>(tier, headers);
		
		String url = create.getUrl();
		
		ResponseEntity<Tier> res = rt.exchange(url , HttpMethod.POST, he, Tier.class);

		LOGGER.info("*** {}", res);

		Assert.assertEquals(HttpStatus.CREATED, res.getStatusCode());		
	}

	private TokenResponse getTokenByScope(String scope) {
		Register r = w.getRegister();
		ResponseEntity<RegisterResponse> re = executeRegister(r);
		RegisterResponse rr = re.getBody();
		ResponseEntity<TokenResponse> res = executeToken(rr.authorization(), scope);
		return res.getBody();
	}
	
	private ResponseEntity<RegisterResponse> executeRegister(Register r) {
		RegisterRequest rr = new RegisterRequest();
		rr.setCallbackUrl(r.getCallbackUrl());
		rr.setClientName(r.getClientName());
		rr.setTokenScope(r.getTokenScope());
		rr.setOwner(r.getOwner());
		rr.setGrantType(r.getGrantType());
		rr.setSaasApp(r.isSaasApp());
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", r.authorization());		
		HttpEntity<RegisterRequest> he = new HttpEntity<>(rr, mh);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}
	
	private ResponseEntity<TokenResponse> executeToken(String authorization, String scope) {
		Token t = w.getToken();
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(t.getUrl())
				.queryParam("grant_type", t.getGrantType())
				.queryParam("username", t.getUsername())
				.queryParam("password", t.getPassword())
				.queryParam("scope", scope);
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", authorization);	
		return rt.exchange(uri.toUriString(), HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
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
		// tier.setTimeUnit("");
		tier.setUnitTime(60000L);
		return tier;
	}
	
}
