package com.wdf.location.httpclient.template.factory;

import com.wdf.location.httpclient.model.HttpClientPropertiesVo;
import com.wdf.location.httpclient.model.RestExceptionHandler;
import com.wdf.location.httpclient.template.properties.RestTemplatePropertiesSpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpClientFactory {

	private static List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

	@PostConstruct
	public void init() {

		messageConverters.add(new FormHttpMessageConverter());

	}

	public HttpClientPropertiesVo createHttpClient(RestTemplatePropertiesSpecification templateProperties) {

		PoolingHttpClientConnectionManager connectionManager = getConnectionManager(templateProperties);

		return createHttpClientPropertiesVO(connectionManager, templateProperties);

	}

	private HttpClientPropertiesVo createHttpClientPropertiesVO(PoolingHttpClientConnectionManager connectionManager,
			RestTemplatePropertiesSpecification templateProperties) {

		RestTemplate restTemplate = createRestTemplate(connectionManager, templateProperties);

		return new HttpClientPropertiesVo(templateProperties.getClientName(), templateProperties, restTemplate,
				connectionManager);

	}

	private RestTemplate createRestTemplate(PoolingHttpClientConnectionManager connectionManager,
			RestTemplatePropertiesSpecification templateProperties) {

		RestTemplate restTemplate = new RestTemplate(
				createClientHttpRequestFactory(connectionManager, templateProperties));

		restTemplate.getMessageConverters().addAll(messageConverters);

		restTemplate.setErrorHandler(getRestExceptionHandler());

		return restTemplate;

	}

	private ClientHttpRequestFactory createClientHttpRequestFactory(
			PoolingHttpClientConnectionManager connectionManager,
			RestTemplatePropertiesSpecification templateProperties) {

		return isProxyConfigAvailable(templateProperties)
				? getProxiedClientHttpRequestFactory(connectionManager, templateProperties)
				: getUnProxiedClientHttpRequestFactory(connectionManager, templateProperties);

	}

	private ClientHttpRequestFactory getProxiedClientHttpRequestFactory(
			PoolingHttpClientConnectionManager connectionManager,
			RestTemplatePropertiesSpecification templateProperties) {

		RequestConfig config = getRequestConfigWithProxy(templateProperties);

		return createRequestFactoryWithProxy(connectionManager, config);

	}

	private ClientHttpRequestFactory getUnProxiedClientHttpRequestFactory(
			PoolingHttpClientConnectionManager connectionManager,
			RestTemplatePropertiesSpecification templateProperties) {

		RequestConfig config = getRequestConfig(templateProperties);

		return createRequestFactory(connectionManager, config);

	}

	private PoolingHttpClientConnectionManager getConnectionManager(
			RestTemplatePropertiesSpecification templateProperties) {

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

		connectionManager.setMaxTotal(templateProperties.getMaxTotalConnection());

		connectionManager.setDefaultMaxPerRoute(templateProperties.getMaxPerChannel());

		connectionManager.setValidateAfterInactivity(templateProperties.getStaleConnectionCheckAfterInactivityPeriod());

		return connectionManager;

	}

	private RequestConfig getRequestConfig(RestTemplatePropertiesSpecification templateProperties) {

		return RequestConfig.custom().setConnectTimeout(templateProperties.getConnectionTimeout())
				.setConnectionRequestTimeout(templateProperties.getConnectionRequestTimeout())
				.setSocketTimeout(templateProperties.getReadTimeout()).build();

	}

	private boolean isProxyConfigAvailable(RestTemplatePropertiesSpecification templateProperties) {

		// return templateProperties.getProxyScheme() != null &&
		// templateProperties.getProxyHost() != null
		// && templateProperties.getProxyPort() > 0;
		return StringUtils.isNotBlank(templateProperties.getProxyScheme())
				&& StringUtils.isNotBlank(templateProperties.getProxyHost()) && templateProperties.getProxyPort() > 0;

	}

	private RequestConfig getRequestConfigWithProxy(RestTemplatePropertiesSpecification templateProperties) {

		return RequestConfig.custom().setConnectTimeout(templateProperties.getConnectionTimeout())
				.setConnectionRequestTimeout(templateProperties.getConnectionRequestTimeout())
				.setSocketTimeout(templateProperties.getReadTimeout()).setProxy(getProxy(templateProperties)).build();

	}

	private HttpHost getProxy(RestTemplatePropertiesSpecification templateProperties) {

		return new HttpHost(templateProperties.getProxyHost(), templateProperties.getProxyPort(),
				templateProperties.getProxyScheme());

	}

	private ClientHttpRequestFactory createRequestFactoryWithProxy(HttpClientConnectionManager connectionManager,
			RequestConfig config) {

		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(config).setProxy(config.getProxy()).disableCookieManagement().build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);

	}

	private ClientHttpRequestFactory createRequestFactory(HttpClientConnectionManager connectionManager,
			RequestConfig config) {

		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(config).disableCookieManagement().build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);

	}

	private RestExceptionHandler getRestExceptionHandler() {

		return new RestExceptionHandler();

	}

}