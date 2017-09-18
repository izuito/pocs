package io.spring.wso2.model;

import java.util.Base64;

public class RegisterResponse {

	private String callBackURL;
	private String jsonString;
	private String clientName;
	private String clientId;
	private String clientSecret;
	private String isSaasApplication;
	private String appOwner;

	public String getCallBackURL() {
		return callBackURL;
	}

	public void setCallBackURL(String callBackURL) {
		this.callBackURL = callBackURL;
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

	public String getIsSaasApplication() {
		return isSaasApplication;
	}

	public void setIsSaasApplication(String isSaasApplication) {
		this.isSaasApplication = isSaasApplication;
	}

	public String getAppOwner() {
		return appOwner;
	}

	public void setAppOwner(String appOwner) {
		this.appOwner = appOwner;
	}

	public String authorization() {
		String code = clientId + ":" + clientSecret;
		return Base64.getEncoder().encodeToString(code.getBytes());
	}

	@Override
	public String toString() {
		return "RegisterResponse [callBackURL=" + callBackURL + ", jsonString=" + jsonString + ", clientName="
				+ clientName + ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", isSaasApplication="
				+ isSaasApplication + ", appOwner=" + appOwner + "]";
	}

}

