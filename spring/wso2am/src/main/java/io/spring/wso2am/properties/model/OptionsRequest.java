package io.spring.wso2am.properties.model;

public abstract class OptionsRequest {

	private String url;
	private String contentType;
	private String scope;
	private String authorizationType;
	private String authorizationEncoded;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}

	public String getAuthorizationEncoded() {
		return authorizationEncoded;
	}

	public void setAuthorizationEncoded(String authorizationEncoded) {
		this.authorizationEncoded = authorizationEncoded;
	}

}
