package io.spring.wso2.properties;

import java.util.Base64;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(value = "wso2")
@Validated
public class WSO2Properties {

	private Register register;

	private Token token;

	@Valid
	private Publisher publisher;

	private Apim apim;

	@Valid
	private ThrottlingTier throttlingTier;

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

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Apim getApim() {
		return apim;
	}

	public void setApim(Apim apim) {
		this.apim = apim;
	}

	public ThrottlingTier getThrottlingTier() {
		return throttlingTier;
	}

	public void setThrottlingTier(ThrottlingTier throttlingTier) {
		this.throttlingTier = throttlingTier;
	}

	public static class Publisher {

		@NotNull
		private String url;
		@NotNull
		private String granttype;
		@NotNull
		private String username;
		@NotNull
		private String password;
		@Valid
		private Add add;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getGranttype() {
			return granttype;
		}

		public void setGranttype(String granttype) {
			this.granttype = granttype;
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

		public Add getAdd() {
			return add;
		}

		public void setAdd(Add add) {
			this.add = add;
		}

		public static class Add {

			@NotNull
			private String tierLevel;

			@NotNull
			private String contentType;

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getContentType() {
				return contentType;
			}

			public void setContentType(String contentType) {
				this.contentType = contentType;
			}

		}

		@Override
		public String toString() {
			return "Publisher [url=" + url + "]";
		}

	}

	public static class Apim {

		private String tierView;

		private String tierManage;

		public String getTierView() {
			return tierView;
		}

		public void setTierView(String tierView) {
			this.tierView = tierView;
		}

		public String getTierManage() {
			return tierManage;
		}

		public void setTierManage(String tierManage) {
			this.tierManage = tierManage;
		}

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

	}

	public static class ThrottlingTier {

		@Valid
		private Create create;

		public Create getCreate() {
			return create;
		}

		public void setCreate(Create create) {
			this.create = create;
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

	}

	@Override
	public String toString() {
		return "WSO2Properties [register=" + register + ", publisher=" + publisher + "]";
	}

}