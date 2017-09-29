package io.spring.wso2am.controller;

import java.util.Base64;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.dto.RegisterRequest;
import io.spring.wso2am.dto.RegisterResponse;
import io.spring.wso2am.dto.TokenResponse;
import io.spring.wso2am.properties.ApimProperties;
import io.spring.wso2am.properties.ApimProperties.Register;
import io.spring.wso2am.properties.ApimProperties.Register.Parameter;
import io.spring.wso2am.properties.ApimProperties.Token;
import io.spring.wso2am.utils.HttpEntityUtils;

@RestController
@RequestMapping(value = { "/apim" })
@EnableConfigurationProperties(value = { ApimProperties.class })
public class ApimController {

	private final RestTemplate rt;
	private final ApimProperties ap;
	private final HttpEntityUtils heu;

	public ApimController(RestTemplate rt, ApimProperties ap, HttpEntityUtils heu) {
		this.rt = rt;
		this.ap = ap;
		this.heu = heu;
	}

	@PostMapping(value = { "/register" })
	public ResponseEntity<RegisterResponse> register() {
		Register r = ap.getRegister();
		Parameter p = r.getParameter();
		HttpEntity<RegisterRequest> he = heu.toHttpEntity(r, p.getAuthorization(), p.getContenttype());
		return rt.exchange(p.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	@PostMapping(value = { "/token" }, params = { "scope" })
	public ResponseEntity<TokenResponse> token(@RequestParam("scope") String scope) {
		ResponseEntity<RegisterResponse> r = register();
		RegisterResponse rr = r.getBody();
		String authorization = getAuthorizationEncoded(rr);
		return token(authorization, scope);
	}
	
	public String getAuthorizationEncoded(RegisterResponse rr) {
		String key = rr.getClientId() + ":" + rr.getClientSecret();
		return Base64.getEncoder().encodeToString(key.getBytes());
	}

	private ResponseEntity<TokenResponse> token(String authorization, String scope) {
		Token t = ap.getToken();
		t.setAuthorizationEncoded(authorization);
		t.setScope(scope);
		HttpEntity<Void> he = heu.toHttpEntity(t.getAuthorization());
		return rt.exchange(t.uri(), HttpMethod.POST, he, TokenResponse.class);
	}
	
	public String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}

}
