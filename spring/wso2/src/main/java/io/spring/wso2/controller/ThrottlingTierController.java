package io.spring.wso2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Create;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Delete;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Get;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.GetAll;
import io.spring.wso2.properties.WSO2Properties.ThrottlingTier.Update;
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

	@PostMapping(consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Tier> create(@RequestBody Tier tier) {
		Create create = getCreate();
		HttpEntity<Tier> he = getHttpEntity(tier, create.getAuthorization(), create.getContentType());
		return rt.exchange(create.getUrl(), HttpMethod.POST, he, Tier.class);
	}

	@PutMapping(value = "/{tierName}")
	public @ResponseBody ResponseEntity<Tier> update(@PathVariable("tierName") String tierName,
			@RequestBody Tier tier) {
		Update update = getUpdate();
		update.setUrl(update.getUrl() + "/" + tierName);
		HttpEntity<Tier> he = getHttpEntity(tier, update.getAuthorization(), update.getContentType());
		return rt.exchange(update.getUrl(), HttpMethod.PUT, he, Tier.class);
	}

	@DeleteMapping(value = "/{tierName}")
	public ResponseEntity<Void> delete(@PathVariable("tierName") String tierName) {
		Delete d = getDelete();
		d.setUrl(d.getUrl() + "/" + tierName);
		HttpEntity<Tier> he = getHttpEntity(d.getAuthorization());
		return rt.exchange(d.getUrl(), HttpMethod.DELETE, he, Void.class);
	}

	@GetMapping(value = "/{tierName}")
	public ResponseEntity<Tier> get(@PathVariable("tierName") String tierName) {
		Get get = getGet();
		get.setUrl(get.getUrl() + "/" + tierName);
		HttpEntity<Tier> he = getHttpEntity(get.getAuthorization());
		return rt.exchange(get.getUrl(), HttpMethod.GET, he, Tier.class);
	}

	@GetMapping
	public @ResponseBody ResponseEntity<TierList> get() {
		GetAll ga = getGetAll();
		HttpEntity<Tier> he = getHttpEntity(ga.getAuthorization());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, TierList.class);
	}

	public TokenResponse getAccessToken(String scope) {
		String url = "http://localhost:8080/apim/token?scope=" + scope;
		ResponseEntity<TokenResponse> retr = rt.postForEntity(url, HttpEntity.EMPTY, TokenResponse.class);
		LOGGER.info(">>> AccessToken: {}", retr);
		return retr.getBody();
	}

	public Create getCreate() {
		ThrottlingTier tt = w.getThrottlingTier();
		Create create = tt.getCreate();
		TokenResponse token = getAccessToken(create.getScope());
		create.setAuthorization(token.authorization());
		return create;
	}

	public Update getUpdate() {
		ThrottlingTier tt = w.getThrottlingTier();
		Update update = tt.getUpdate();
		TokenResponse token = getAccessToken(update.getScope());
		update.setAuthorization(token.authorization());
		return update;
	}

	public Delete getDelete() {
		ThrottlingTier tt = w.getThrottlingTier();
		Delete update = tt.getDelete();
		TokenResponse token = getAccessToken(update.getScope());
		update.setAuthorization(token.authorization());
		return update;
	}

	public Get getGet() {
		ThrottlingTier tt = w.getThrottlingTier();
		Get get = tt.getGet();
		TokenResponse token = getAccessToken(get.getScope());
		get.setAuthorization(token.authorization());
		return get;
	}

	public GetAll getGetAll() {
		ThrottlingTier tt = w.getThrottlingTier();
		GetAll getAll = tt.getGetAll();
		TokenResponse token = getAccessToken(getAll.getScope());
		getAll.setAuthorization(token.authorization());
		return getAll;
	}

	private HttpEntity<Tier> getHttpEntity(Tier t, String authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return new HttpEntity<>(t, headers);
	}

	private <T> HttpEntity<T> getHttpEntity(String authorization) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		return new HttpEntity<>(headers);
	}

}
