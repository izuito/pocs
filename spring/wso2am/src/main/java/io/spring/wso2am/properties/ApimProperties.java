package io.spring.wso2am.properties;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.spring.wso2am.dto.RegisterRequest;
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

	@JsonIgnoreProperties(value = { "parameter" })
	public static class Register extends RegisterRequest {

		private Parameter parameter;

		public Parameter getParameter() {
			return parameter;
		}

		public void setParameter(Parameter parameter) {
			this.parameter = parameter;
		}

		public static class Parameter {

			private String url;
			private String username;
			private String password;
			private String contenttype;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			public String getContenttype() {
				return contenttype;
			}

			public void setContenttype(String contenttype) {
				this.contenttype = contenttype;
			}

			public String getAuthorization() {
				String key = getUsername() + ":" + getPassword();
				return Base64.getEncoder().encodeToString(key.getBytes());
			}

		}

	}

	public static class Token extends TokenRequest {

		public String authorization() {
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
