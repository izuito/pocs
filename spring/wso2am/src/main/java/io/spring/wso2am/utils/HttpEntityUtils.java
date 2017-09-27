package io.spring.wso2am.utils;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class HttpEntityUtils {

	private MultiValueMap<String, String> toMap(String authorization, String contenttype) {
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", authorization);
		h.add("Content-Type", contenttype);
		return h;
	}
	
	private MultiValueMap<String, String> toMap(String authorization) {
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", authorization);
		return h;
	}
	
	public <T> HttpEntity<T> toHttpEntity(T t, String authorization, String contenttype) {
		MultiValueMap<String, String> h = toMap(authorization, contenttype);
		return new HttpEntity<>(t, h);
	}
	
	public <T> HttpEntity<T> toHttpEntity(String authorization, String contenttype) {
		MultiValueMap<String, String> h = toMap(authorization, contenttype);
		return new HttpEntity<>(h);
	}	
	
	public <T> HttpEntity<T> toHttpEntity(String authorization) {
		MultiValueMap<String, String> h = toMap(authorization);
		return new HttpEntity<>(h);
	}
	
}
