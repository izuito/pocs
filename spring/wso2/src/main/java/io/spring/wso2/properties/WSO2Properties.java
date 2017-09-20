package io.spring.wso2.properties;

import java.util.Base64;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(value = "wso2")
@Validated
public class WSO2Properties {

	private String publisherUrl;

	@Valid
	private Register register = new Register();

	@Valid
	private Token token = new Token();

	@Valid
	private ThrottlingTier throttlingTier = new ThrottlingTier();

	@Valid
	private Api api;

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}

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

	public ThrottlingTier getThrottlingTier() {
		return throttlingTier;
	}

	public void setThrottlingTier(ThrottlingTier throttlingTier) {
		this.throttlingTier = throttlingTier;
	}

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public static class Register {

		private String url;
		private String callbackUrl;
		private String clientName;
		private String tokenScope;
		private String owner;
		private String grantType;
		private boolean saasApp;
		private String authorization;
		private String username;
		private String password;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

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

		public String getAuthorization() {
			return authorization;
		}

		public void setAuthorization(String authorization) {
			this.authorization = authorization;
		}

		/**
		 * Base64 Encoder String no campo Authorization
		 * 
		 * @return String
		 */
		public String authorization() {
			return Base64.getEncoder().encodeToString(getAuthorization().getBytes());
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

	}

	public static class Token {

		private String url;
		private String grantType;
		private String username;
		private String password;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getGrantType() {
			return grantType;
		}

		public void setGrantType(String grantType) {
			this.grantType = grantType;
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

		public String authorization() {
			String key = username + ":" + password;
			return Base64.getEncoder().encodeToString(key.getBytes());
		}

	}

	public static class ThrottlingTier {

		@Valid
		private Create create;

		@Valid
		private Update update;

		@Valid
		private Delete delete;

		@Valid
		private Get get;

		@Valid
		private GetAll getAll;

		public Create getCreate() {
			return create;
		}

		public void setCreate(Create create) {
			this.create = create;
		}

		public Update getUpdate() {
			return update;
		}

		public void setUpdate(Update update) {
			this.update = update;
		}

		public Delete getDelete() {
			return delete;
		}

		public void setDelete(Delete delete) {
			this.delete = delete;
		}

		public Get getGet() {
			return get;
		}

		public void setGet(Get get) {
			this.get = get;
		}

		public GetAll getGetAll() {
			return getAll;
		}

		public void setGetAll(GetAll getAll) {
			this.getAll = getAll;
		}

		public static class Create {

			@NotNull
			private String url;
			@NotNull
			private String tierLevel;
			private String authorization;
			@NotNull
			private String contentType;
			@NotNull
			private String scope;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

			public String getContentType() {
				return contentType;
			}

			public void setContentType(String contentType) {
				this.contentType = contentType;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

		}

		public static class Update {

			@NotNull
			private String url;
			@NotNull
			private String tierLevel;
			private String authorization;
			@NotNull
			private String contentType;
			@NotNull
			private String scope;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

			public String getContentType() {
				return contentType;
			}

			public void setContentType(String contentType) {
				this.contentType = contentType;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

		}

		public static class Delete {

			@NotNull
			private String url;

			@NotNull
			private String tierLevel;

			private String authorization;

			@NotNull
			private String scope;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

		}

		public static class Get {

			@NotNull
			private String url;

			@NotNull
			private String tierLevel;

			private String authorization;

			@NotNull
			private String scope;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

		}

		public static class GetAll {

			@NotNull
			private String url;

			@NotNull
			private String tierLevel;

			private String authorization;

			@NotNull
			private String scope;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

		}

	}

	public static class Api {

		private Get get;

		public Get getGet() {
			return get;
		}

		public void setGet(Get get) {
			this.get = get;
		}

		public static class Get {

			private String url;
			private String scope;
			private String authorization;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getScope() {
				return scope;
			}

			public void setScope(String scope) {
				this.scope = scope;
			}

			public String getAuthorization() {
				return authorization;
			}

			public void setAuthorization(String authorization) {
				this.authorization = authorization;
			}

		}

	}

}
