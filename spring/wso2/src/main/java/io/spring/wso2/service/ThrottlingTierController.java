package io.spring.wso2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.WSO2Tests;
import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Apim;
import io.swagger.client.model.Tier;

@RestController
@RequestMapping("/throttling/tier")
public class ThrottlingTierController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WSO2Tests.class);
	
	private final RestTemplate rt;

	private final WSO2Properties wp;

	public ThrottlingTierController(RestTemplate rt, WSO2Properties wp) {
		this.rt = rt;
		this.wp = wp;
	}

	@PostMapping
	public ResponseEntity<Tier> createTier(Tier tier) {
		Apim apim = wp.getApim();

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
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Tier> updateTier(Tier tier) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{tierName}")
	public ResponseEntity<Void> deleteTier(@PathVariable("tierName") String tierName) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/{tierName}")
	public ResponseEntity<Tier> get(@PathVariable("tierName") String tierName) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Tier> getAll() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
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
	
	private HttpEntity<Tier> getHttpEntity(String authorization, String contentType, Tier tier) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return new HttpEntity<>(tier, headers);
	}
	
	private RegisterRequest registerRequest() {
		RegisterRequest rr = new RegisterRequest();
		rr.setCallbackUrl("www.google.lk");
		rr.setClientName("rest_api_admin");
		rr.setTokenScope("Production");
		rr.setOwner("admin");
		rr.setGrantType("password token_refresh");
		rr.setSaasApp(true);
//		rr.setUrl(wp.getUrlRegister());
//		rr.setAuthorization("YWRtaW46YWRtaW4=");
		return rr;
	}

}
