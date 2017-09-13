package io.spring.wso2.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(value = "wso2")
@Validated
public class WSO2Properties {

	private String register;

	private String token;

	@Valid
	private Publisher publisher;

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public static class Publisher {

		@NotNull
		private String url;

		private Add add;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Add getAdd() {
			return add;
		}

		public void setAdd(Add add) {
			this.add = add;
		}

		public static class Add {
			private String tierLevel;
			private String contentType;

			public String getTierLevel() {
				return tierLevel;
			}

			public void setTierLevel(String tierLevel) {
				this.tierLevel = tierLevel;
			}

			public String getContentType() {
				return contentType;
			}

			public void setContentType(String contentType) {
				this.contentType = contentType;
			}

		}

		@Override
		public String toString() {
			return "Publisher [url=" + url + "]";
		}

	}

	@Override
	public String toString() {
		return "WSO2Properties [register=" + register + ", publisher=" + publisher + "]";
	}

}
