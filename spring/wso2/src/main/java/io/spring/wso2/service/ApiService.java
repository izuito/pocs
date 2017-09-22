package io.spring.wso2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Api.Get;
import io.swagger.client.publisher.model.API;
import io.swagger.client.publisher.model.APIList;

@Service
public class ApiService {
	
	private final RestTemplate rt;
	private final WSO2Properties wp;
	private final ObjectMapper om;
	
	public ApiService(RestTemplate rt, WSO2Properties wp, ObjectMapper om) {
		this.rt = rt;
		this.wp = wp;
		this.om = om;
	}
	
	public ResponseEntity<Object> getApi(String apiId, String authorization, String contentType) throws JsonProcessingException {
		ResponseEntity<APIList> re = getSearchApis(authorization, contentType);
		String json = om.writeValueAsString(re.getBody());
		Filter filters = Filter.filter(Criteria.where("id").eq(apiId));
		String jsonPath = "$.list[?]";
		Object o = JsonPath.read(json, jsonPath , filters);
		return new ResponseEntity<>(o, HttpStatus.OK);
	}
	
	public ResponseEntity<APIList> getSearchApis(String authorization, String contentType) {
		Get get = wp.getApi().getGet();
		get.setAuthorization(authorization);
		get.setContentType(contentType);
		HttpEntity<API> he = getHttpEntity(get);
		return rt.exchange(get.getUrl(), HttpMethod.GET, he, APIList.class);
	}

	private HttpEntity<API> getHttpEntity(Get get) {
		MultiValueMap<String, String> mh = new LinkedMultiValueMap<>();
		mh.add("Authorization", "Basic " + get.getAuthorization());
		mh.add("Content-Type", get.getContentType());
		return new HttpEntity<API>(mh);
	}
	
}
