package io.spring.wso2am.controller;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.dto.TokenResponse;
import io.spring.wso2am.properties.SubscriptionsProperties;
import io.spring.wso2am.properties.SubscriptionsProperties.GetAll;
import io.spring.wso2am.utils.HttpEntityUtils;
import io.swagger.client.publisher.model.SubscriptionList;

@RestController
@RequestMapping("/subscriptions")
@EnableConfigurationProperties(value = { SubscriptionsProperties.class })
public class SubscriptionsController {

	private final RestTemplate rt;
	private final ApimController ac;
	private final SubscriptionsProperties sp;
	private final HttpEntityUtils heu;
	private final ModelMapper mm;

	public SubscriptionsController(RestTemplate rt, ApimController ac, SubscriptionsProperties sp, HttpEntityUtils heu, ModelMapper mm) {
		this.rt = rt;
		this.ac = ac;
		this.sp = sp;
		this.heu = heu;
		this.mm = mm;
	}
	
	@GetMapping
	public ResponseEntity<SubscriptionList> get() {
		GetAll ga = sp.getGetAll();
		ga.setAuthorization(getAuthorization(ga.getScope()));
		HttpEntity<Object> he = heu.toHttpEntity(ga.getAuthorization());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, SubscriptionList.class);
	}
	
	private String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = ac.token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}
	
}
