package io.spring.wso2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Api;
import io.spring.wso2.properties.WSO2Properties.Api.Get;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.properties.WSO2Properties.Token;
import io.swagger.client.publisher.ApiClient;
import io.swagger.client.publisher.ApiException;
import io.swagger.client.publisher.api.APIIndividualApi;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIList;
import io.swagger.client.publisher.model.APIObject1;

@RestController
@RequestMapping("/apis")
@EnableConfigurationProperties(WSO2Properties.class)
public class ApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	private final RestTemplate rt;

	private final WSO2Properties w;

	@Autowired
	private APIIndividualApi aia;

	@Autowired
	private ApiClient ac;

	public ApiController(RestTemplate rt, WSO2Properties w) {
		this.rt = rt;
		this.w = w;
	}

	@GetMapping("/{apiId}")
	public @ResponseBody ResponseEntity<APIObject1> getApi(@PathVariable("apiId") String apiId) throws ApiException {
		Get get = getGet();

		ac.addDefaultHeader("Authorization", get.getAuthorization());

		 String url = get.getUrl() + "/" + apiId;
		
		 MultiValueMap<String, String> headers =
		 headers(get.getAuthorization());
		 HttpEntity<API> he = new HttpEntity<API>(headers);
		
		 ResponseEntity<String> re = rt.exchange(url, HttpMethod.GET, he, String.class);
		 
		 LOGGER.info("*** Result: {}", re);
		 
		APIObject1 api = null;
		String accept = MediaType.APPLICATION_JSON_VALUE;
		String ifNoneMatch = null;
		String ifModifiedSince = null;

		api = aia.apisApiIdGet(apiId, accept, ifNoneMatch, ifModifiedSince);

		return new ResponseEntity<>(api, HttpStatus.OK);
	}

	@GetMapping
	public @ResponseBody ResponseEntity<APIList> getAllApi() {
		Get get = getGet();

		MultiValueMap<String, String> headers = headers(get.getAuthorization(), MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<API> he = new HttpEntity<API>(headers);

		return rt.exchange(get.getUrl(), HttpMethod.GET, he, APIList.class);
	}

	@PostMapping(value = "/register")
	public @ResponseBody ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		Register r = w.getRegister();
		String authorization = "Basic " + r.authorization();
		MultiValueMap<String, String> mh = headers(authorization, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<RegisterRequest> he = new HttpEntity<>(registerRequest, mh);
		return rt.exchange(r.getUrl(), HttpMethod.POST, he, RegisterResponse.class);
	}

	@PostMapping(value = "/token/authorization/{authorization}/scope/{scope}")
	public ResponseEntity<TokenResponse> token(@PathVariable("authorization") String authorization,
			@PathVariable("scope") String scope) {
		Token t = w.getToken();
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(t.getUrl())
				.queryParam("grant_type", t.getGrantType()).queryParam("username", t.getUsername())
				.queryParam("password", t.getPassword()).queryParam("scope", scope);
		MultiValueMap<String, String> mh = headers("Basic " + authorization);
		return rt.exchange(uri.toUriString(), HttpMethod.POST, new HttpEntity<>(mh), TokenResponse.class);
	}

	public Get getGet() {
		Api api = w.getApi();
		Get get = api.getGet();

		ResponseEntity<RegisterResponse> rerr = register(toRegisterRequest(w.getRegister()));
		RegisterResponse rr = rerr.getBody();
		ResponseEntity<TokenResponse> retr = token(rr.authorization(), get.getScope());
		TokenResponse tr = retr.getBody();
		get.setAuthorization(tr.authorization());
		LOGGER.info("*** Authorization: {}", get.getAuthorization());
		return get;
	}

	public MultiValueMap<String, String> headers(String authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		headers.add("Content-Type", contentType);
		return headers;
	}

	public MultiValueMap<String, String> headers(String authorization) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", authorization);
		return headers;
	}

	public RegisterRequest toRegisterRequest(Register r) {
		RegisterRequest rr = new RegisterRequest();
		rr.setCallbackUrl(r.getCallbackUrl());
		rr.setClientName(r.getClientName());
		rr.setTokenScope(r.getTokenScope());
		rr.setOwner(r.getOwner());
		rr.setGrantType(r.getGrantType());
		rr.setSaasApp(r.isSaasApp());
		return rr;
	}

}
