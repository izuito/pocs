package io.spring.wso2am.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.dto.TokenResponse;
import io.spring.wso2am.properties.ApisProperties;
import io.spring.wso2am.properties.ApisProperties.Create;
import io.spring.wso2am.properties.ApisProperties.Delete;
import io.spring.wso2am.properties.ApisProperties.GetAll;
import io.spring.wso2am.properties.ApisProperties.Update;
import io.spring.wso2am.utils.HttpEntityUtils;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIList;

@RestController
@RequestMapping("/apis")
@EnableConfigurationProperties(value = { ApisProperties.class })
public class ApisController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

	private final RestTemplate rt;
	private final ApimController ac;
	private final ApisProperties ap;
	private final HttpEntityUtils heu;

	public ApisController(RestTemplate rt, ApimController ac, ApisProperties ap, HttpEntityUtils heu) {
		this.rt = rt;
		this.ac = ac;
		this.ap = ap;
		this.heu = heu;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<API> create(@RequestBody API api) {
		Create c = ap.getCreate();
		c.setAuthorization(getAuthorization(c.getScope()));
		HttpEntity<API> he = heu.toHttpEntity(api, c.getAuthorization(), c.getContenttype());
		return rt.exchange(c.getUrl(), HttpMethod.POST, he, API.class);
	}

	@PutMapping(value = { "/{apiId}" }, consumes = { "application/json" })
	public @ResponseBody ResponseEntity<API> update(@RequestParam("apiId") String apiId, @RequestBody API api) {
		Update u = ap.getUpdate();
		u.setUrl(u.getUrl() + "/" + apiId);
		u.setAuthorization(getAuthorization(u.getScope()));
		HttpEntity<API> he = heu.toHttpEntity(api, u.getAuthorization(), u.getContenttype());
		return rt.exchange(u.getUrl(), HttpMethod.PUT, he, API.class);
	}
	
	@DeleteMapping(value = { "/{apiId}" })
	public @ResponseBody ResponseEntity<Void> delete(@RequestParam("apiId") String apiId) {
		Delete d = ap.getDelete();
		d.setUrl(d.getUrl() + "/" + apiId);
		d.setAuthorization(getAuthorization(d.getScope()));
		HttpEntity<API> he = heu.toHttpEntity(d.getAuthorization());
		return rt.exchange(d.getUrl(), HttpMethod.DELETE, he, Void.class);
	}
	
	@GetMapping
	public @ResponseBody ResponseEntity<APIList> get() {
		GetAll ga = ap.getGetAll();
		ga.setAuthorization(getAuthorization(ga.getScope()));
		HttpEntity<Object> he = heu.toHttpEntity(ga.getAuthorization());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, APIList.class);
	}

	private String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = ac.token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}

}
