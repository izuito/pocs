package io.spring.wso2am.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.dto.TokenResponse;
import io.spring.wso2am.properties.ApplicationProperties;
import io.spring.wso2am.properties.ApplicationProperties.Create;
import io.spring.wso2am.properties.ApplicationProperties.GetAll;
import io.spring.wso2am.utils.HttpEntityUtils;
import io.swagger.client.store.model.Application;
import io.swagger.client.store.model.ApplicationInfoObjectWithBasicApplicationDetails;
import io.swagger.client.store.model.ApplicationList;

@RestController
@RequestMapping("/applications")
@EnableConfigurationProperties(value = { ApplicationProperties.class })
public class ApplicationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

	private final RestTemplate rt;
	private final ApimController ac;
	private final ApplicationProperties ap;
	private final HttpEntityUtils heu;

	public ApplicationController(RestTemplate rt, ApimController ac, ApplicationProperties ap, HttpEntityUtils heu) {
		this.rt = rt;
		this.ac = ac;
		this.ap = ap;
		this.heu = heu;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Application> create(@RequestBody Application a) {
		Create c = ap.getCreate();
		c.setAuthorization(getAuthorization(c.getScope()));
		LOGGER.info("Create: \n{}", c);
		HttpEntity<Application> he = heu.toHttpEntity(a, c.getAuthorization(), c.getContenttype());
		LOGGER.info("HttpEntity: \n{}", he);
		return rt.exchange(c.getUrl(), HttpMethod.POST, he, Application.class);
	}

	@GetMapping(params = { "name" })
	public ResponseEntity<ApplicationInfoObjectWithBasicApplicationDetails> get(@RequestParam("name") String name) {
		ResponseEntity<ApplicationList> real = get();
		LOGGER.info(">>> {}", real);
		ApplicationInfoObjectWithBasicApplicationDetails aiowbad = null;
		ApplicationList al = real.getBody();
		for(ApplicationInfoObjectWithBasicApplicationDetails a : al.getList()) {
			if(name.equals(a.getName())) {
				aiowbad = a;
				break;
			}
		}
		return new ResponseEntity<ApplicationInfoObjectWithBasicApplicationDetails>(aiowbad, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<ApplicationList> get() {
		GetAll ga = ap.getGetAll();
		ga.setAuthorization(getAuthorization(ga.getScope()));
		HttpEntity<Object> he = heu.toHttpEntity(ga.getAuthorization());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, ApplicationList.class);
	}

	private String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = ac.token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}

}
