package io.spring.wso2.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.Connecting.RegisterRequest;
import io.spring.wso2.Connecting.RegisterResponse;
import io.spring.wso2.Connecting.Token;
import io.spring.wso2.config.WSO2Properties;

@Service
public class WSO2Service {
	
	private RestTemplate rt;
	
	private WSO2Properties wp;

	public WSO2Service(RestTemplate rt, WSO2Properties wp) {
		this.rt = rt;
		this.wp = wp;
	}

	public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
		String encode = encode("admin", "admin");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + encode);
		headers.add("Content-Type", "application/json");
		HttpEntity<RegisterRequest> http = new HttpEntity<>(request, headers);
		String url = wp.getRegister();
		return rt.exchange(url, HttpMethod.POST, http, RegisterResponse.class);
	}
	
	public ResponseEntity<Token> token(RegisterRequest request, String scope) {
		ResponseEntity<RegisterResponse> re = register(request);
		RegisterResponse rr = re.getBody();
		String url = wp.getToken();
		String token = rr.encodeClientIdAndClientSecret();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + token);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("grant_type", "password")
                .queryParam("username", "admin")
                .queryParam("password", "admin")
                .queryParam("scope", scope);
		url = builder.toUriString();
		HttpEntity<Token> he = new HttpEntity<>(headers);
		return rt.postForEntity(url, he, Token.class);	
	}
	
	private String encode(String key, String value) {
		String code = key + ":" + value;
		return Base64.getEncoder().encodeToString(code.getBytes());
	}
	
}
