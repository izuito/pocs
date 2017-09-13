package io.spring.wso2;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2.Connecting.RegisterRequest;
import io.spring.wso2.Connecting.Token;
import io.spring.wso2.service.WSO2Service;
import io.swagger.client.model.Tier;
import io.swagger.client.model.TierList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
public class ThrottlingTierTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(Connecting.class);

	@Autowired
	private RestTemplate rt;
	
	@Autowired
	private WSO2Service service;
	
	@Test
	public void validToken() throws Exception {

		RegisterRequest body = getRegisterRequest();

		ResponseEntity<Token> re = service.token(body, "apim:tier_view");

		Token token = re.getBody();

		LOGGER.info(">>> {}", token);

		Assert.assertEquals(200, re.getStatusCode().value());

	}	
	
	@Test
	public void testGetAvailableTiers() throws Exception {
		URI url = new URI("https://127.0.0.1:9443/api/am/store/v0.11/tiers/api");
		ResponseEntity<TierList> re = rt.getForEntity(url, TierList.class);
		LOGGER.info(">>> {}", re.getBody());

		Assert.assertEquals(200, re.getStatusCode().value());
	}

	@Test
	public void testGetAvailableTiers406() throws Exception {
		try {
			URI url = new URI("https://127.0.0.1:9443/api/am/store/v0.11/tiers/api");
			RequestEntity<Void> req = RequestEntity.get(url).accept(org.springframework.http.MediaType.APPLICATION_XML).build();
			ResponseEntity<TierList> res = rt.exchange(req, TierList.class);
			LOGGER.info(">>> {}", res.getBody());
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(406, e.getRawStatusCode());
		}
	}
	
	@Test
	public void testGetDetailsOfATier() throws Exception {
		String tier = "Bronze";
		URI url = new URI("https://127.0.0.1:9443/api/am/store/v0.11/tiers/api/" + tier);
		ResponseEntity<Tier> re = rt.getForEntity(url, Tier.class);
		LOGGER.info(">>> {}", re.getBody());

		Assert.assertEquals(200, re.getStatusCode().value());
	}	
	
//	@Ignore
	@Test
	public void testGetAllTiers() throws Exception {
		URI url = new URI("https://127.0.0.1:9443/api/am/publisher/v0.11/tiers/api");
		ResponseEntity<TierList> re = rt.getForEntity(url, TierList.class);
		LOGGER.info(">>> {}", re.getBody());

		Assert.assertEquals(200, re.getStatusCode().value());
	}
	
	private RegisterRequest getRegisterRequest() {
		RegisterRequest body = new RegisterRequest();
		body.setCallbackUrl("www.google.lk");
		body.setClientName("rest_api_admin");
		body.setTokenScope("Production");
		body.setOwner("admin");
		body.setGrantType("password refresh_token");
		body.setSaasApp(true);
		return body;
	}
	
}
