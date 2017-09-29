package io.spring.wso2am.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.util.UriComponentsBuilder;

@ConfigurationProperties(prefix = "wso2.applications")
public class ApplicationProperties {

	private Create create;
	private Update update;
	private Delete delete;
	private Get get;
	private GetAll getAll;
	private GenerateKeys generateKeys;

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

	public GenerateKeys getGenerateKeys() {
		return generateKeys;
	}

	public void setGenerateKeys(GenerateKeys generateKeys) {
		this.generateKeys = generateKeys;
	}

	public static class Create {

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

	public static class Update {

		private String url;
		private String contenttype;
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

		public String getAuthorization() {
			return authorization;
		}

		public void setAuthorization(String authorization) {
			this.authorization = authorization;
		}

	}

	public static class Delete {

		private String url;
		private String authorization;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getAuthorization() {
			return authorization;
		}

		public void setAuthorization(String authorization) {
			this.authorization = authorization;
		}

	}

	public static class Get {

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

	}

	public static class GetAll {

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

	public static class GenerateKeys {

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

		public String uri(String applicationId) {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(getUrl()).queryParam("applicationId",
					applicationId);
			return uri.toUriString();
		}

	}

}
