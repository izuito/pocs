package io.spring.wso2am.dto;

public class TokenRequest {

	private String url;
	private String granttype;
	private String username;
	private String password;
	private String scope;
	private String authorization;

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
