package io.spring.wso2am.properties;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.spring.wso2am.dto.RegisterRequestParameter;
import io.spring.wso2am.dto.TokenRequest;

@ConfigurationProperties(prefix = "wso2.apim")
public class ApimProperties {

	private Register register;

	private Token token;

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@JsonIgnoreProperties(value = { "url", "username", "password", "contenttype", "authorization" })
	public static class Register extends RegisterRequestParameter {

	}

	public static class Token extends TokenRequest {

		public String getAuthorization() {
			String key = getUsername() + ":" + getPassword();
			String encode = Base64.getEncoder().encodeToString(key.getBytes());
			return "Basic " + encode;
		}

		public String uri() {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(getUrl())
					.queryParam("grant_type", getGranttype()).queryParam("username", getUsername())
					.queryParam("password", getPassword()).queryParam("scope", getScope());
			return uri.toUriString();
		}

	}

}
