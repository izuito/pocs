package io.spring.wso2;

import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.spring.wso2.config.WSO2Properties;
import io.spring.wso2.service.WSO2Service;
import io.swagger.client.api.ThrottlingTierCollectionApi;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
public class Connecting {

	private static final Logger LOGGER = LoggerFactory.getLogger(Connecting.class);

	@Autowired
	private RestTemplate rt;

	@Autowired
	private WSO2Properties wp;

	@Autowired
	private WSO2Service service;

	@Autowired
	private ThrottlingTierCollectionApi ttca;

	@Test
	public void validRegister() throws Exception {

		RegisterRequest body = getRegisterRequest();

		ResponseEntity<RegisterResponse> re = service.register(body);

		RegisterResponse rr = re.getBody();

		LOGGER.info(">>> {}", rr);

		Assert.assertEquals(200, re.getStatusCode().value());

	}

	@Test
	public void validToken() throws Exception {

		RegisterRequest body = getRegisterRequest();

		ResponseEntity<Token> re = service.token(body, "apim:tier_view");

		Token token = re.getBody();

		LOGGER.info(">>> {}", token);

		Assert.assertEquals(200, re.getStatusCode().value());

	}

	private RegisterRequest getRegisterRequest() {
		RegisterRequest body = new RegisterRequest();
		body.setCallbackUrl("www.google.lk");
		body.setClientName("rest_api_admin");
		body.setTokenScope("Production");
		body.setOwner("admin");
		body.setGrantType("password refresh_token");
		body.setSaasApp(true);
		return body;
	}

	public static class RegisterRequest {

		@JsonProperty("callbackURL")
		private String callbackUrl;
		private String clientName;
		private String tokenScope;
		private String owner;
		private String grantType;
		private boolean saasApp;

		public String getCallbackUrl() {
			return callbackUrl;
		}

		public void setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public String getTokenScope() {
			return tokenScope;
		}

		public void setTokenScope(String tokenScope) {
			this.tokenScope = tokenScope;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getGrantType() {
			return grantType;
		}

		public void setGrantType(String grantType) {
			this.grantType = grantType;
		}

		public boolean isSaasApp() {
			return saasApp;
		}

		public void setSaasApp(boolean saasApp) {
			this.saasApp = saasApp;
		}

	}

	public static class RegisterResponse {

		private String callbackUrl;
		private String jsonString;
		private String clientName;
		private String clientId;
		private String clientSecret;

		public String getCallbackUrl() {
			return callbackUrl;
		}

		public void setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
		}

		public String getJsonString() {
			return jsonString;
		}

		public void setJsonString(String jsonString) {
			this.jsonString = jsonString;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String encodeClientIdAndClientSecret() {
			String code = clientId + ":" + clientSecret;
			return Base64.getEncoder().encodeToString(code.getBytes());
		}

		@Override
		public String toString() {
			return "RegisterResponse [callbackUrl=" + callbackUrl + ", jsonString=" + jsonString + ", clientName="
					+ clientName + ", clientId=" + clientId + ", clientSecret=" + clientSecret + "]";
		}

	}

	public static class JsonString {

		private String username;
		private String redirect_uris;
		private String tokenScope;
		private String client_name;
		private String grant_types;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getRedirect_uris() {
			return redirect_uris;
		}

		public void setRedirect_uris(String redirect_uris) {
			this.redirect_uris = redirect_uris;
		}

		public String getTokenScope() {
			return tokenScope;
		}

		public void setTokenScope(String tokenScope) {
			this.tokenScope = tokenScope;
		}

		public String getClient_name() {
			return client_name;
		}

		public void setClient_name(String client_name) {
			this.client_name = client_name;
		}

		public String getGrant_types() {
			return grant_types;
		}

		public void setGrant_types(String grant_types) {
			this.grant_types = grant_types;
		}

	}

	public static class Token {

		private String scope;
		private String token_type;
		private int expires_token;
		private String refresh_token;
		private String access_token;

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public String getToken_type() {
			return token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public int getExpires_token() {
			return expires_token;
		}

		public void setExpires_token(int expires_token) {
			this.expires_token = expires_token;
		}

		public String getRefresh_token() {
			return refresh_token;
		}

		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		@Override
		public String toString() {
			return "Token [scope=" + scope + ", token_type=" + token_type + ", expires_token=" + expires_token
					+ ", refresh_token=" + refresh_token + ", access_token=" + access_token + "]";
		}

	}

}
