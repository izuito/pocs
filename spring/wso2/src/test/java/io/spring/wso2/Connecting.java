package io.spring.wso2;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.wso2.controller.WSO2Service;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WSO2App.class })
public class Connecting {

	private static final Logger LOGGER = LoggerFactory.getLogger(Connecting.class);

	@Autowired
	private WSO2Service service;

	public static class JsonString {

		private String username;
		private String redirect_uris;
		private String tokenScope;
		private String client_name;
		private String grant_types;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getRedirect_uris() {
			return redirect_uris;
		}

		public void setRedirect_uris(String redirect_uris) {
			this.redirect_uris = redirect_uris;
		}

		public String getTokenScope() {
			return tokenScope;
		}

		public void setTokenScope(String tokenScope) {
			this.tokenScope = tokenScope;
		}

		public String getClient_name() {
			return client_name;
		}

		public void setClient_name(String client_name) {
			this.client_name = client_name;
		}

		public String getGrant_types() {
			return grant_types;
		}

		public void setGrant_types(String grant_types) {
			this.grant_types = grant_types;
		}

	}

}
