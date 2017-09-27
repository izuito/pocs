package io.spring.wso2.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Application.GetAll;
import io.swagger.client.store.model.Application2;
import io.swagger.client.store.model.ApplicationList;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

	private final RestTemplate rt;
	private final WSO2Properties w;

	public ApplicationController(RestTemplate rt, WSO2Properties w) {
		this.rt = rt;
		this.w = w;
	}

	@GetMapping(value = "/{applicationId}")
	public @ResponseBody ResponseEntity<Application2> get(@PathVariable("applicationId") String applicationId) {
		GetAll ga = w.getApplication().getGetAll();
		String url = ga.getUrl() + "/" + applicationId;
		HttpEntity<Object> he = getHttpEntity(ga.getScope());
		return rt.exchange(url, HttpMethod.GET, he, Application2.class);
	}

	@GetMapping
	public @ResponseBody ResponseEntity<ApplicationList> getAll() {
		GetAll ga = w.getApplication().getGetAll();
		HttpEntity<Object> he = getHttpEntity(ga.getScope());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, ApplicationList.class);
	}

	private HttpEntity<Object> getHttpEntity(String scope) {
		String token = getAccessToken(scope);
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", "Bearer " + token);
		return new HttpEntity<>(mh);
	}

	private String getAccessToken(String scope) {
		String url = "http://localhost:8080/api/am/token/scope/" + scope;
		ResponseEntity<TokenResponse> retr = rt.postForEntity(url, HttpEntity.EMPTY, TokenResponse.class);
		return retr.getBody().getAccessToken();
	}

}
