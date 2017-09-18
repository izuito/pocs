package io.spring.wso2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(value = "throttling.tier")
@Validated
public class ThrottlingTierProperties {

	private Create create;

	public Create getCreate() {
		return create;
	}

	public void setCreate(Create create) {
		this.create = create;
	}

	public static class Create {

		private String url;
		private String tierLevel;
		private String authorization;
		private String contentType;
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
