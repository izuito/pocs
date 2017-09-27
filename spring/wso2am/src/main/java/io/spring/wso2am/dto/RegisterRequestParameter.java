package io.spring.wso2am.dto;

import java.util.Base64;

public class RegisterRequestParameter extends RegisterRequest {

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
		String encode = Base64.getEncoder().encodeToString(key.getBytes());
		return "Basic " + encode;
	}
	
}
