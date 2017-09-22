package io.spring.wso2.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import io.spring.wso2.model.RegisterRequest;
import io.spring.wso2.model.RegisterResponse;
import io.spring.wso2.model.TokenResponse;
import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Api.Create;
import io.spring.wso2.properties.WSO2Properties.Api.Get;
import io.spring.wso2.properties.WSO2Properties.Register;
import io.spring.wso2.service.WSO2AccessService;
import io.swagger.client.publisher.ApiException;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIList;

@RestController
@RequestMapping("/apis")
@EnableConfigurationProperties(WSO2Properties.class)
public class ApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	private final RestTemplate rt;
	private final WSO2Properties w;
	private final WSO2AccessService was;
	private final ObjectMapper om;

	public ApiController(RestTemplate rt, WSO2Properties w, WSO2AccessService was, ObjectMapper om) {
		this.rt = rt;
		this.w = w;
		this.was = was;
		this.om = om;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<API> createApi(@RequestBody API api) {
		Create create = w.getApi().getCreate();
		HttpEntity<API> he = getHttpEntity(create);
		return rt.exchange(create.getUrl(), HttpMethod.POST, he, API.class);
	}

	@GetMapping("/{apiId}")
	public @ResponseBody ResponseEntity<Object> getApi(@PathVariable("apiId") String apiId)
			throws ApiException, IOException {
		ResponseEntity<APIList> re = getSearchApis();
		APIList body = re.getBody();
		String json = om.writeValueAsString(body);
		Filter filters = Filter.filter(Criteria.where("id").eq(apiId));
		String jsonPath = "$.list[?]";
		Object o = JsonPath.read(json, jsonPath, filters);
		return new ResponseEntity<>(o, HttpStatus.OK);
	}

	@GetMapping
	public @ResponseBody ResponseEntity<APIList> getSearchApis() {
		ResponseEntity<RegisterResponse> rerr = was.register();
		RegisterResponse rr = rerr.getBody();
		Get get = w.getApi().getGet();
		ResponseEntity<TokenResponse> retr = was.token(rr.authorization(), get.getScope());
		TokenResponse tr = retr.getBody();
		get.setAuthorization(tr.authorization());
		HttpEntity<API> he = getHttpEntity(get);
		return rt.exchange(get.getUrl(), HttpMethod.GET, he, APIList.class);
	}

	private HttpEntity<API> getHttpEntity(Create create) {
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", "Bearer " + create.getAuthorization());
		return new HttpEntity<API>(mh);
	}
	
	private HttpEntity<API> getHttpEntity(Get get) {
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", "Basic " + get.getAuthorization());
		mh.add("Content-Type", get.getContentType());
		return new HttpEntity<API>(mh);
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
