package io.spring.wso2am.utils;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The Class HttpEntityUtils.
 * 
 * @author izuito
 */
@Component
public class HttpEntityUtils {

	/**
	 * To http entity.
	 *
	 * @param <T> the generic type
	 * @param t the t
	 * @param authorization the authorization
	 * @param contenttype the contenttype
	 * @return the http entity
	 */
	public <T> HttpEntity<T> toHttpEntity(T t, String authorization, String contenttype) {
		MultiValueMap<String, String> h = toMap(authorization, contenttype);
		return new HttpEntity<>(t, h);
	}

	/**
	 * To http entity.
	 *
	 * @param <T> the generic type
	 * @param authorization the authorization
	 * @param contenttype the contenttype
	 * @return the http entity
	 */
	public <T> HttpEntity<T> toHttpEntity(String authorization, String contenttype) {
		MultiValueMap<String, String> h = toMap(authorization, contenttype);
		return new HttpEntity<>(h);
	}

	/**
	 * To http entity.
	 *
	 * @param <T> the generic type
	 * @param authorization the authorization
	 * @return the http entity
	 */
	public <T> HttpEntity<T> toHttpEntity(String authorization) {
		MultiValueMap<String, String> h = toMap(authorization);
		return new HttpEntity<>(h);
	}

	/**
	 * To map.
	 *
	 * @param authorization the authorization
	 * @param contenttype the contenttype
	 * @return the multi value map
	 */
	private MultiValueMap<String, String> toMap(String authorization, String contenttype) {
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", authorization);
		h.add("Content-Type", contenttype);
		return h;
	}

	/**
	 * To map.
	 *
	 * @param authorization the authorization
	 * @return the multi value map
	 */
	private MultiValueMap<String, String> toMap(String authorization) {
		MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
		h.add("Authorization", authorization);
		return h;
	}

}
