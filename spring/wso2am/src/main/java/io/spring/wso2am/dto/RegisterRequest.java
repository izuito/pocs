package io.spring.wso2am.dto;

public class RegisterRequest {

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
