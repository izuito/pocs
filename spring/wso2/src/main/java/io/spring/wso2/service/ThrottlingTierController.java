package io.spring.wso2.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Create;
import io.spring.wso2.properties.WSO2Properties.Token;
import io.swagger.client.ApiException;
import io.swagger.client.model.Tier;

@RestController
@RequestMapping("/throttling/tier")
public class ThrottlingTierController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WSO2Tests.class);
	
	private final RestTemplate rt;

	private final WSO2Properties w;

	public ThrottlingTierController(RestTemplate rt, WSO2Properties w) {
		this.rt = rt;
		this.w = w;
	}

	@PostMapping
	public ResponseEntity<Tier> createTier(Tier tier) {
		Create create = getCreate();

		MultiValueMap<String, String> headers = headers(create.getAuthorization(), create.getContentType());

		HttpEntity<Tier> he = new HttpEntity<Tier>(tier, headers);
		
		String url = create.getUrl();
		
		ResponseEntity<Tier> res = rt.exchange(url , HttpMethod.POST, he, Tier.class);

		LOGGER.info("*** {}", res);		
		
		return new ResponseEntity<>(res.getBody(), res.getStatusCode());
	}

	private Create getCreate() {
		ThrottlingTier tt = w.getThrottlingTier();
		Create create = tt.getCreate();
		TokenResponse token = getTokenByScope(create.getScope());
		create.setAuthorization(token.authorization());
		return create;
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
	
	@ExceptionHandler(value = { ApiException.class })
	public ResponseEntity<Exception> error(HttpServletRequest req, ApiException ex) {
		return new ResponseEntity<>(ex, HttpStatus.valueOf(ex.getCode()));
	}
	
	private TokenResponse getTokenByScope(String scope) {
		Register r = w.getRegister();
		ResponseEntity<RegisterResponse> re = executeRegister(r);
		RegisterResponse rr = re.getBody();
		ResponseEntity<TokenResponse> res = executeToken(rr.authorization(), scope);
		return res.getBody();
	}
	
	private ResponseEntity<RegisterResponse> executeRegister(Register r) {
		RegisterRequest rr = toRegisterRequest(r);
		String authorization = "Basic " + r.authorization();
		MultiValueMap<String, String> mh = headers(authorization, MediaType.APPLICATION_JSON_VALUE);
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
		mh.add("Authorization", "Basic " + authorization);	
		return rt.exchange(uri.toUriString(), HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
	}	

	private RegisterRequest toRegisterRequest(Register r) {
		RegisterRequest rr = new RegisterRequest();
		rr.setCallbackUrl(r.getCallbackUrl());
		rr.setClientName(r.getClientName());
		rr.setTokenScope(r.getTokenScope());
		rr.setOwner(r.getOwner());
		rr.setGrantType(r.getGrantType());
		rr.setSaasApp(r.isSaasApp());
		return rr;
	}
	
	private MultiValueMap<String, String> headers(String authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return headers;
	}
	
}
