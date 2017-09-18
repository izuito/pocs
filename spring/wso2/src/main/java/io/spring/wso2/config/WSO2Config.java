package io.spring.wso2.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.OkHttpClient;

import io.spring.wso2.properties.WSO2Properties;
import io.spring.wso2.properties.WSO2Properties.Publisher;
import io.swagger.client.ApiClient;
import io.swagger.client.api.ThrottlingTierCollectionApi;
import io.swagger.client.api.ThrottlingTierIndividualApi;

@Configuration
@EnableConfigurationProperties(value = { WSO2Properties.class })
public class WSO2Config {

	private static final Logger LOGGER = LoggerFactory.getLogger(WSO2Config.class);

	private WSO2Properties properties;

	public WSO2Config(WSO2Properties properties) {
		this.properties = properties;
	}

	@PostConstruct
	public void name() {
		LOGGER.info("*** {}", properties);
	}

	@Bean
	public ThrottlingTierIndividualApi throttlingTierIndividualApi() throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.info("*** WSO2Config - ThrottlingTierIndividualApi");
		return new ThrottlingTierIndividualApi(apiClient());
	}

	@Bean
	public ThrottlingTierCollectionApi throttlingTierCollectionApi() throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.info("*** WSO2Config - ThrottlingTierCollectionApi");
		return new ThrottlingTierCollectionApi(apiClient());
	}

	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		LOGGER.info("*** WSO2Config - RestTemplate");
		RestTemplate rt = new RestTemplate(httpComponentsClientHttpRequestFactory());
		rt.setMessageConverters(getListHttpMessageConverter());
		return rt;
	}

	private List<HttpMessageConverter<?>> getListHttpMessageConverter() {
		LOGGER.info("*** WSO2Config - List<HttpMessageConverter<?>>");
		List<HttpMessageConverter<?>> list = new ArrayList<>();
		list.add(new MappingJackson2HttpMessageConverter(objectMapper()));
		return list;
	}

	public ObjectMapper objectMapper() {
		LOGGER.info("*** WSO2Config - ObjectMapper");
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		om.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return om;
	}

	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		LOGGER.info("*** WSO2Config - HttpComponentsClientHttpRequestFactory");
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

	@Bean
	public ApiClient apiClient() throws KeyManagementException, NoSuchAlgorithmException {
		ApiClient ac = new ApiClient();
		Publisher publisher = properties.getPublisher();
		ac.setHttpClient(okHttpClient());
		ac.setBasePath(publisher.getUrl());
		return ac;
	}
	
	public OkHttpClient okHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
		OkHttpClient client = new OkHttpClient();
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		// Install the all-trusting trust manager
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		// Create an ssl socket factory with our all-trusting manager
		final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		client.setSslSocketFactory(sslSocketFactory);
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
		return client;
	}

}
