package io.spring.wso2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.properties.WSO2Properties.Token;

@RestController
@RequestMapping("/apim")
@EnableConfigurationProperties(WSO2Properties.class)
public class ApiManagerController {

	private final RestTemplate rt;
	private final WSO2Properties wp;
	private final ModelMapper mm;

	public ApiManagerController(RestTemplate rt, WSO2Properties wp, ModelMapper mm) {
		this.rt = rt;
		this.wp = wp;
		this.mm = mm;
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register() {
		Register r = wp.getRegister();
		HttpEntity<RegisterRequest> he = getHttpEntity(r);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	@PostMapping(params = { "authorization", "scope" }, value = { "/token" })
	public ResponseEntity<TokenResponse> token(@RequestParam("authorization") String authorization,
			@RequestParam("scope") String scope) {
		Token token = wp.getToken();
		token.setAuthorization(authorization);
		token.setScope(scope);
		HttpEntity<Void> he = getHttpEntity(token);
		return rt.exchange(token.uri(), HttpMethod.POST, he, TokenResponse.class);
	}

	@PostMapping(params = { "scope" }, value = { "/token" })
	public ResponseEntity<TokenResponse> token(@RequestParam("scope") String scope) {
		ResponseEntity<RegisterResponse> rerr = register();
		RegisterResponse rr = rerr.getBody();
		return token(rr.getAuthorization(), scope);
	}

	@PostMapping(value = { "/token/scope/api_view" })
	public ResponseEntity<TokenResponse> apiview() {
		return token("apim:api_view");
	}

	@PostMapping(value = { "/token/scope/api_create" })
	public ResponseEntity<TokenResponse> apicreate() {
		return token("apim:api_create");
	}
	
	@PostMapping(value = { "/token/scope/api_publish" })
	public ResponseEntity<TokenResponse> apipublish() {
		return token("apim:api_publish");
	}	
	
	@PostMapping(value = { "/token/scope/tier_view" })
	public ResponseEntity<TokenResponse> tierview() {
		return token("apim:tier_view");
	}
	
	@PostMapping(value = { "/token/scope/tier_manage" })
	public ResponseEntity<TokenResponse> tiermanage() {
		return token("apim:tier_manage");
	}

	private HttpEntity<RegisterRequest> getHttpEntity(Register r) {
		RegisterRequest rr = mm.map(r, RegisterRequest.class);
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", "Basic " + r.getAuthorization());
		h.add("Content-Type", r.getContentType());
		return new HttpEntity<>(rr, h);
	}

	private HttpEntity<Void> getHttpEntity(Token token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + token.getAuthorization());
		return new HttpEntity<>(headers);
	}

}
