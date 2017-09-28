package io.spring.wso2am.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.spring.wso2am.properties.ApisProperties.ChangeLifeCycle;
import io.spring.wso2am.properties.ApisProperties.Create;
import io.spring.wso2am.properties.ApisProperties.Delete;
import io.spring.wso2am.properties.ApisProperties.GetAll;
import io.spring.wso2am.properties.ApisProperties.Update;
import io.spring.wso2am.utils.HttpEntityUtils;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIInfo;
import io.swagger.client.publisher.model.APIInfoObjectWithBasicAPIDetails_;
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
	private final ModelMapper mm;

	public ApisController(RestTemplate rt, ApimController ac, ApisProperties ap, HttpEntityUtils heu, ModelMapper mm) {
		this.rt = rt;
		this.ac = ac;
		this.ap = ap;
		this.heu = heu;
		this.mm = mm;
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

	@GetMapping(value = { "/{name}" })
	public ResponseEntity<APIInfo> get(@PathVariable("name") String name) {
		ResponseEntity<APIList> real = get();
		APIList al = real.getBody();
		APIInfo api = new APIInfo();
		for (APIInfoObjectWithBasicAPIDetails_ ai : al.getList()) {
			if (name.equals(ai.getName())) {
				api = mm.map(ai, APIInfo.class);
				break;
			}
		}
		return new ResponseEntity<APIInfo>(api, HttpStatus.OK);
	}

	@PostMapping(value = { "/{apiId}/published" })
	public @ResponseBody ResponseEntity<Void> published(@RequestParam("apiId") String apiId) {
		ChangeLifeCycle c = ap.getChangeLifeCycle();
		c.setUrl(c.getUrl() + "?apiId=" + apiId + "&action=Publish");
		c.setAuthorization(getAuthorization(c.getScope()));
		HttpEntity<Void> he = heu.toHttpEntity(c.getAuthorization());
		return rt.exchange(c.getUrl(), HttpMethod.POST, he, Void.class);
	}

	private String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = ac.token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}

}
