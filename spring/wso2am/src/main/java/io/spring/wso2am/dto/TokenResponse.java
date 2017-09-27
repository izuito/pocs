package io.spring.wso2am.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_token")
	private Long expiresToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getExpiresToken() {
		return expiresToken;
	}

	public void setExpiresToken(Long expiresToken) {
		this.expiresToken = expiresToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String authorization() {
		return getTokenType() + " " + getAccessToken();
	}

	@Override
	public String toString() {
		return "Token [scope=" + scope + ", tokenType=" + tokenType + ", expiresToken=" + expiresToken
				+ ", refreshToken=" + refreshToken + ", accessToken=" + accessToken + ", expiresIn=" + expiresIn
				+ "]";
	}

}

