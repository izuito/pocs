package io.spring.wso2am.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import io.spring.wso2am.properties.ApimProperties.Token;

@RestController
@RequestMapping(value = { "/apim" })
@EnableConfigurationProperties(value = { ApimProperties.class })
public class ApimController {

	private final RestTemplate rt;
	private final ApimProperties ap;

	public ApimController(RestTemplate rt, ApimProperties ap) {
		this.rt = rt;
		this.ap = ap;
	}

	@PostMapping(value = { "/register" })
	public ResponseEntity<RegisterResponse> register() {
		Register r = ap.getRegister();
		HttpEntity<RegisterRequest> he = getHttpEntity(r);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	@PostMapping(value = { "/token" }, params = { "scope" })
	public ResponseEntity<TokenResponse> token(@RequestParam("scope") String scope) {
		Token t = ap.getToken();
		t.setScope(scope);
		HttpEntity<Void> he = getHttpEntity(t);
		return rt.exchange(t.uri(), HttpMethod.POST, he, TokenResponse.class);
	}

	private HttpEntity<RegisterRequest> getHttpEntity(Register r) {
		RegisterRequest rr = r;
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", r.getAuthorization());
		h.add("Content-Type", r.getContenttype());
		return new HttpEntity<>(rr, h);
	}

	private HttpEntity<Void> getHttpEntity(Token t) {
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", "Basic " + t.getAuthorization());
		return new HttpEntity<>(h);
	}

}
