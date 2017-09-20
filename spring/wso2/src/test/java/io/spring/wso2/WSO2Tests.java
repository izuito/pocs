package io.spring.wso2;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.properties.WSO2Properties;
import io.swagger.client.ApiClient;
import io.swagger.client.model.Tier;
import io.swagger.client.model.Tier.TierLevelEnum;
import io.swagger.client.model.Tier.TierPlanEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class WSO2Tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
public class WSO2Tests {

	private static final Logger LOGGER = LoggerFactory.getLogger(WSO2Tests.class);

	@Autowired
	private RestTemplate rt;

	@Autowired
	private WSO2Properties wp;

	@Autowired
	private ApiClient ap;
	
//	@Ignore
//	@Test
//	public void testRegister() throws Exception {
//		RegisterRequest rr = registerRequest();
//		rr.setUrl(wp.getUrlRegister());
//		rr.setAuthorization("YWRtaW46YWRtaW4=");
//
//		ResponseEntity<RegisterResponse> res = executeRegister(rr);
//
//		RegisterResponse body = res.getBody();
//
//		LOGGER.info("*** {}", body);
//
//		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
//		Assert.assertNotNull(body.getClientId());
//		Assert.assertNotNull(body.getClientSecret());
//	}
//
//	@Ignore
//	@Test
//	public void testGetToken() throws Exception {
//		RegisterRequest rr = registerRequest();
//		rr.setUrl(wp.getUrlRegister());
//		rr.setAuthorization("YWRtaW46YWRtaW4=");
//
//		ResponseEntity<RegisterResponse> re = executeRegister(rr);
//
//		ResponseEntity<TokenResponse> res = executeToken(re.getBody().authorization(), "password", "admin", "admin",
//				"apim:tier_view");
//
//		TokenResponse token = res.getBody();
//
//		LOGGER.info("*** {}", token);
//
//		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
//		Assert.assertNotNull("TokenType is NULL", token.getTokenType());
//		Assert.assertNotNull("AccessToken is NULL", token.getAccessToken());
//	}
//
//	@Ignore
//	@Test
//	public void testCreateTier() throws Exception {
//		Tier tier = tier();
//		
//		Apim apim = wp.getApim();
//
//		TokenResponse token = getTokenByScope(apim.getTierManage());
//
//		Publisher publisher = wp.getPublisher();
//		Add add = publisher.getAdd();
//
//		MultiValueMap<String, String> headers = headers(token.authorization(), add.getContentType());
//
//		HttpEntity<Tier> he = new HttpEntity<Tier>(tier, headers);
//
//		LOGGER.info("*** {}", he);
//
//		String url = publisher.getUrl() + "/" + add.getTierLevel();
//
//		ResponseEntity<Tier> res = rt.exchange(url, HttpMethod.POST, he, Tier.class);
//
//		LOGGER.info("*** {}", res);
//
//		Assert.assertEquals(HttpStatus.CREATED, res.getStatusCode());
//	}
//
//	@Ignore
//	@Test
//	public void testUpdateTier() throws Exception {
//		Apim apim = wp.getApim();
//
//		TokenResponse body = getTokenByScope(apim.getTierManage());
//
//		Tier tier = tier().requestCount(10L);
//
//		Publisher publisher = wp.getPublisher();
//		Add add = publisher.getAdd();
//
//		HttpEntity<Tier> he = getHttpEntity(body.authorization(), add.getContentType(), tier);
//
//		String url = publisher.getUrl() + "/" + add.getTierLevel() + "/" + tier.getName();
//
//		ResponseEntity<Tier> res = rt.exchange(url, HttpMethod.PUT, he, Tier.class);
//
//		LOGGER.info("*** {}", res);
//
//		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
//	}
//
//	@Ignore
//	@Test
//	public void testDeleteTier() throws Exception {
//		Apim apim = wp.getApim();
//
//		TokenResponse token = getTokenByScope(apim.getTierManage());
//
//		HttpEntity<Tier> he = getHttpEntity(token.authorization());
//
//		Publisher pub = wp.getPublisher();
//		Add add = pub.getAdd();
//		String url = pub.getUrl() + "/" + add.getTierLevel() + "/TTier";
//
//		ResponseEntity<Void> res = rt.exchange(url, HttpMethod.DELETE, he, Void.class);
//
//		LOGGER.info("*** {}", res);
//
//		Assert.assertEquals(HttpStatus.OK, res.getStatusCode());
//	}
//
//	@Ignore
//	@Test
//	public void testGetDetailsOfATier() throws Exception {
//		Apim apim = wp.getApim();
//
//		TokenResponse token = getTokenByScope(apim.getTierView());
//
//		Publisher publisher = wp.getPublisher();
//		Add add = publisher.getAdd();
//
//		String url = publisher.getUrl();
//		String tierLevel = add.getTierLevel();
//		String tierName = "TTier";
//
//		url = url + "/" + tierLevel + "/" + tierName;
//
//		HttpEntity<Tier> he = getHttpEntity(token.authorization());
//
//		LOGGER.info("*** {}", he);
//
//		ResponseEntity<Tier> re = rt.exchange(url, HttpMethod.GET, he, Tier.class);
//
//		LOGGER.info("*** {}", re);
//
//		Assert.assertEquals(HttpStatus.OK, re.getStatusCode());
//	}
//
//	@Ignore
//	@Test
//	public void testGetAllTiers() throws Exception {
//		Apim apim = wp.getApim();
//
//		TokenResponse token = getTokenByScope(apim.getTierView());
//		
//		Publisher publisher = wp.getPublisher();
//		Add add = publisher.getAdd();
//
//		String url = publisher.getUrl() + "/" + add.getTierLevel();
//
//		HttpEntity<Tier> he = getHttpEntity(token.authorization());
//		
//		ResponseEntity<TierList> re = rt.exchange(url, HttpMethod.GET, he, TierList.class);
//
//		TierList tiers = re.getBody();
//
//		LOGGER.info("*** {}", tiers);
//
//		Assert.assertNotNull(tiers);
//	}
//
//	@Test
//	public void testName() throws Exception {
//		
//		LOGGER.info("{}", wp.getApim());
//		
//	}
	
//	private TokenResponse getTokenByScope(String scope) {
//		ResponseEntity<RegisterResponse> re = executeRegister(registerRequest());
//		RegisterResponse rr = re.getBody();
//		ResponseEntity<TokenResponse> res = executeToken(rr.authorization(), "password", "admin", "admin", scope);
//		return res.getBody();
//	}

//	private ResponseEntity<RegisterResponse> executeRegister(RegisterRequest rr) {
//		MultiValueMap<String, String> mh = headers(rr.getAuthorization());
//		HttpEntity<RegisterRequest> he = new HttpEntity<>(rr, mh);
//		return rt.exchange(rr.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
//	}

//	private ResponseEntity<TokenResponse> executeToken(String authorization, String granttype, String username, String password,
//			String scope) {
//		String url = url(wp.getToken(), granttype, username, password, scope);
//		MultiValueMap<String, String> mh = headers(authorization);
//		return rt.exchange(url, HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
//	}

	private MultiValueMap<String, String> headers(String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + token);
		return headers;
	}

	private MultiValueMap<String, String> headers(String authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return headers;
	}

	private String url(String url, String granttype, String username, String password, String scope) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("grant_type", granttype)
				.queryParam("username", username).queryParam("password", password).queryParam("scope", scope);
		return builder.toUriString();
	}

//	private RegisterRequest registerRequest() {
//		RegisterRequest rr = new RegisterRequest();
//		rr.setCallbackUrl("www.google.lk");
//		rr.setClientName("rest_api_admin");
//		rr.setTokenScope("Production");
//		rr.setOwner("admin");
//		rr.setGrantType("password token_refresh");
//		rr.setSaasApp(true);
//		rr.setUrl(wp.getUrlRegister());
//		rr.setAuthorization("YWRtaW46YWRtaW4=");
//		return rr;
//	}

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

	private HttpEntity<Tier> getHttpEntity(String authorization, String contentType, Tier tier) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return new HttpEntity<>(tier, headers);
	}

	private HttpEntity<Tier> getHttpEntity(String authorization) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		return new HttpEntity<>(headers);
	}

	private String url(String... values) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			s.append(value);
			if (i < values.length) {
				s.append('/');
			}
		}
		return s.toString();
	}

}
