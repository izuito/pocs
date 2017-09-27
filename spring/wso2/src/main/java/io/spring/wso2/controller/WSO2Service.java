package io.spring.wso2.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;

@Service
public class WSO2Service {
	
	private RestTemplate rt;
	
	private WSO2Properties wp;

	public WSO2Service(RestTemplate rt, WSO2Properties wp) {
		this.rt = rt;
		this.wp = wp;
	}
	
	public TokenResponse getTokenByScope(RegisterRequest req, String url, String scope) {
		ResponseEntity<RegisterResponse> re = executeRegister(req, url, "YWRtaW46YWRtaW4=");
		RegisterResponse rr = re.getBody();
		ResponseEntity<TokenResponse> res = executeToken(rr.getAuthorization(), "", "password", "admin", "admin", scope);
		return res.getBody();
	}

	public ResponseEntity<RegisterResponse> executeRegister(RegisterRequest rr, String url, String authorization) {
		MultiValueMap<String, String> mh = headers(authorization);
		HttpEntity<RegisterRequest> he = new HttpEntity<>(rr, mh);
		return rt.exchange(url, HttpMethod.POST, he, RegisterResponse.class);
	}	
	
	public ResponseEntity<TokenResponse> executeToken(String url, String authorization, String granttype, String username, String password,
			String scope) {
		String uri = url(url, granttype, username, password, scope);
		MultiValueMap<String, String> mh = headers(authorization);
		return rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
	}
	
	private MultiValueMap<String, String> headers(String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + token);
		return headers;
	}
	
	private String url(String url, String granttype, String username, String password, String scope) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
				.queryParam("grant_type", granttype)
				.queryParam("username", username)
				.queryParam("password", password)
				.queryParam("scope", scope);
		return builder.toUriString();
	}
	
}
