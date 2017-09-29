package io.spring.wso2am.dto;

public class TokenRequest {

	private String url;
	private String granttype;
	private String username;
	private String password;

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

}
