package io.spring.wso2.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Create;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Delete;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Get;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.GetAll;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Update;
import io.spring.wso2.properties.WSO2Properties.Token;
import io.swagger.client.publisher.model.Tier;
import io.swagger.client.publisher.model.TierList;

@RestController
@RequestMapping("/throttling/tier")
@EnableConfigurationProperties(WSO2Properties.class)
public class ThrottlingTierController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThrottlingTierController.class);

	private final RestTemplate rt;

	private final WSO2Properties w;

	public ThrottlingTierController(RestTemplate rt, WSO2Properties w) {
		this.rt = rt;
		this.w = w;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Tier> createTier(@RequestBody Tier tier) {
		Create create = getCreate();
		
		MultiValueMap<String, String> headers = headers(create.getAuthorization(), create.getContentType());
		HttpEntity<Tier> he = new HttpEntity<Tier>(tier, headers);
		ResponseEntity<Tier> ret = rt.exchange(create.getUrl(), HttpMethod.POST, he, Tier.class);

		LOGGER.info("*** Create: {}", ret);

		return new ResponseEntity<>(ret.getBody(), ret.getStatusCode());
	}

	@PutMapping(value = "/{tierName}")
	public @ResponseBody ResponseEntity<Tier> updateTier(@PathVariable("tierName") String tierName,
			@RequestBody Tier tier) {
		Update update = getUpdate();

		MultiValueMap<String, String> headers = headers(update.getAuthorization(), update.getContentType());
		HttpEntity<Tier> he = new HttpEntity<Tier>(tier, headers);
		String url = update.getUrl() + "/" + tierName;
		ResponseEntity<Tier> res = rt.exchange(url, HttpMethod.PUT, he, Tier.class);

		LOGGER.info("*** Update: {}", res);

		return new ResponseEntity<>(res.getBody(), res.getStatusCode());
	}

	@DeleteMapping(value = "/{tierName}")
	public ResponseEntity<Void> deleteTier(@PathVariable("tierName") String tierName) {
		Delete delete = getDelete();

		MultiValueMap<String, String> headers = headers(delete.getAuthorization());
		HttpEntity<Tier> he = new HttpEntity<Tier>(headers);
		String url = delete.getUrl() + "/" + tierName;
		ResponseEntity<Tier> res = rt.exchange(url, HttpMethod.DELETE, he, Tier.class);

		LOGGER.info("*** Delete: {}", res);

		return new ResponseEntity<>(res.getStatusCode());
	}

	@GetMapping(value = "/{tierName}")
	public ResponseEntity<Tier> getTier(@PathVariable("tierName") String tierName) {
		Get get = getGet();

		MultiValueMap<String, String> headers = headers(get.getAuthorization());
		HttpEntity<Tier> he = new HttpEntity<Tier>(headers);
		String url = get.getUrl() + "/" + tierName;
		ResponseEntity<Tier> res = rt.exchange(url, HttpMethod.GET, he, Tier.class);

		LOGGER.info("** Get: {}", res);

		return new ResponseEntity<>(res.getBody(), res.getStatusCode());
	}

	@GetMapping
	public @ResponseBody ResponseEntity<TierList> getAllTiers() {
		GetAll getAll = getGetAll();

		MultiValueMap<String, String> headers = headers(getAll.getAuthorization());
		HttpEntity<Tier> he = new HttpEntity<Tier>(headers);
		String url = getAll.getUrl();
		ResponseEntity<TierList> res = rt.exchange(url, HttpMethod.GET, he, TierList.class);

		LOGGER.info("*** Get All: {}", res);

		return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	public @ResponseBody ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		Register r = w.getRegister();
		String authorization = "Basic " + r.authorization();
		MultiValueMap<String, String> mh = headers(authorization, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<RegisterRequest> he = new HttpEntity<>(registerRequest, mh);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	@PostMapping(value = "/token/authorization/{authorization}/scope/{scope}")
	public ResponseEntity<TokenResponse> token(@PathVariable("authorization") String authorization,
			@PathVariable("scope") String scope) {
		Token t = w.getToken();
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(t.getUrl())
				.queryParam("grant_type", t.getGrantType())
				.queryParam("username", t.getUsername())
				.queryParam("password", t.getPassword())
				.queryParam("scope", scope);
		MultiValueMap<String, String> mh = headers("Basic " + authorization);
		return rt.exchange(uri.toUriString(), HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
	}

	@ExceptionHandler(value = { HttpClientErrorException.class })
	public ResponseEntity<?> error(HttpServletRequest req, HttpClientErrorException h) {
		return new ResponseEntity<>(h.getResponseBodyAsString(), h.getStatusCode());
	}

	public Create getCreate() {
		ThrottlingTier tt = w.getThrottlingTier();
		Create create = tt.getCreate();
		TokenResponse token = getTokenByScope(create.getScope());
		create.setAuthorization("Basic " + token.authorization());
		return create;
	}

	public Update getUpdate() {
		ThrottlingTier tt = w.getThrottlingTier();
		Update update = tt.getUpdate();
		TokenResponse token = getTokenByScope(update.getScope());
		update.setAuthorization(token.authorization());
		return update;
	}

	public Delete getDelete() {
		ThrottlingTier tt = w.getThrottlingTier();
		Delete update = tt.getDelete();
		TokenResponse token = getTokenByScope(update.getScope());
		update.setAuthorization(token.authorization());
		return update;
	}

	public Get getGet() {
		ThrottlingTier tt = w.getThrottlingTier();
		Get get = tt.getGet();
		TokenResponse token = getTokenByScope(get.getScope());
		get.setAuthorization(token.authorization());
		return get;
	}

	public GetAll getGetAll() {
		ThrottlingTier tt = w.getThrottlingTier();
		GetAll getAll = tt.getGetAll();
		TokenResponse token = getTokenByScope(getAll.getScope());
		getAll.setAuthorization(token.authorization());
		return getAll;
	}

	public TokenResponse getTokenByScope(String scope) {
		Register r = w.getRegister();
		ResponseEntity<RegisterResponse> re = executeRegister(r);
		RegisterResponse rr = re.getBody();
		ResponseEntity<TokenResponse> res = executeToken(rr.authorization(), scope);
		return res.getBody();
	}

	public ResponseEntity<RegisterResponse> executeRegister(Register r) {
		RegisterRequest rr = toRegisterRequest(r);
		String authorization = "Basic " + r.authorization();
		MultiValueMap<String, String> mh = headers(authorization, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<RegisterRequest> he = new HttpEntity<>(rr, mh);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	private ResponseEntity<TokenResponse> executeToken(String authorization, String scope) {
		Token t = w.getToken();
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(t.getUrl())
				.queryParam("grant_type", t.getGrantType()).queryParam("username", t.getUsername())
				.queryParam("password", t.getPassword()).queryParam("scope", scope);
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", "Basic " + authorization);
		return rt.exchange(uri.toUriString(), HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
	}

	public RegisterRequest toRegisterRequest(Register r) {
		RegisterRequest rr = new RegisterRequest();
		rr.setCallbackUrl(r.getCallbackUrl());
		rr.setClientName(r.getClientName());
		rr.setTokenScope(r.getTokenScope());
		rr.setOwner(r.getOwner());
		rr.setGrantType(r.getGrantType());
		rr.setSaasApp(r.isSaasApp());
		return rr;
	}

	public MultiValueMap<String, String> headers(String authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return headers;
	}

	public MultiValueMap<String, String> headers(String authorization) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		return headers;
	}

}
