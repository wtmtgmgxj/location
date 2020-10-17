package com.wdf.location.httpclient.store;

import com.wdf.location.httpclient.model.HttpClientPropertiesVo;
import com.wdf.location.httpclient.template.factory.HttpClientFactory;
import com.wdf.location.httpclient.template.properties.RestTemplatePropertiesSpecification;
import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class HttpClientStore {

	@Autowired
	private HttpClientFactory httpClientFactory;

	ConcurrentHashMap<String, HttpClientPropertiesVo> cache = new ConcurrentHashMap<>();

	private ScheduledExecutorService shutDownExecutors = Executors.newScheduledThreadPool(10);

	public RestTemplate getRestTemplateForClient(String clientName) {

		HttpClientPropertiesVo clientProperties = cache.get(clientName);

		if (clientProperties != null) {

			return clientProperties.getRestTemplate();

		}

		return null;

	}

	/**
	 * @param clientName
	 * @param templateProperties
	 * @return boolean value , whether client was actually updated or not.
	 */
	public boolean updateRestTemplateForClient(String clientName,
			RestTemplatePropertiesSpecification templateProperties) {

		if (cache.get(clientName) == null) {

			updateClientInCache(clientName, templateProperties);

			return true;

		}
		else if (!cache.get(clientName).getTemplateProperties().ifEquals(templateProperties)) {

			HttpClientPropertiesVo oldClientProperties = cache.get(clientName);

			updateClientInCache(clientName, templateProperties);

			destroyOldClient(oldClientProperties);

			return true;

		}
		else {

			return false;

		}

	}

	private void updateClientInCache(String clientName, RestTemplatePropertiesSpecification templateProperties) {

		HttpClientPropertiesVo client = httpClientFactory.createHttpClient(templateProperties);

		cache.put(clientName, client);

	}

	private void destroyOldClient(HttpClientPropertiesVo clientProperties) {

		int readTimeout = clientProperties.getTemplateProperties().getReadTimeout();

		HttpClientConnectionManager connManager = clientProperties.getConnManager();

		shutDownExecutors.schedule(connManager::shutdown, readTimeout, TimeUnit.MILLISECONDS);

	}

}
