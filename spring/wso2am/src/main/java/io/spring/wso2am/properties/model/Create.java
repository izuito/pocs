package io.spring.wso2am.properties.model;

public class Create {

	private String url;
	private String contenttype;
	private String scope;
	private String authorization;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
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

	@Override
	public String toString() {
		return "Create [url=" + url + ", contenttype=" + contenttype + ", scope=" + scope + ", authorization="
				+ authorization + "]";
	}

}
