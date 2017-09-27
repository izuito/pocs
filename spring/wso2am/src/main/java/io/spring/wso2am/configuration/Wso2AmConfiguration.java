package io.spring.wso2am.configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class Wso2AmConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Wso2AmConfiguration.class);

	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		LOGGER.info("*** Wso2AmConfiguration - RestTemplate");
		RestTemplate rt = new RestTemplate(httpComponentsClientHttpRequestFactory());
		rt.setMessageConverters(getListHttpMessageConverter());
		return rt;
	}

	private List<HttpMessageConverter<?>> getListHttpMessageConverter() {
		LOGGER.info("*** Wso2AmConfiguration - List<HttpMessageConverter<?>>");
		List<HttpMessageConverter<?>> list = new ArrayList<>();
		list.add(new MappingJackson2HttpMessageConverter(objectMapper()));
		return list;
	}

	public ObjectMapper objectMapper() {
		LOGGER.info("*** Wso2AmConfiguration - ObjectMapper");
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		om.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return om;
	}

	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		LOGGER.info("*** Wso2AmConfiguration - HttpComponentsClientHttpRequestFactory");
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				return true;
			}
		};
		SSLContext sslc = SSLContexts.custom().loadTrustMaterial(trustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslc, new NoopHostnameVerifier());
		CloseableHttpClient hc = HttpClients.custom().setSSLSocketFactory(csf).build();
		return new HttpComponentsClientHttpRequestFactory(hc);
	}
	
}
