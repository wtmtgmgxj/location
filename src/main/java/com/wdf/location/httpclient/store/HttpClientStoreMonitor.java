package com.wdf.location.httpclient.store;

import com.wdf.location.httpclient.model.HttpClientPropertiesVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HttpClientStoreMonitor {

	@Autowired
	private HttpClientStore clientsStore;

	@Scheduled(fixedDelay = 1000)
	public void monitorClientConnections() {

		for (Entry<String, HttpClientPropertiesVo> entry : clientsStore.cache.entrySet()) {

			String clientName = entry.getKey();

			PoolingHttpClientConnectionManager connManager = entry.getValue().getConnManager();

			PoolStats poolStats = connManager.getTotalStats();

			connManager.closeExpiredConnections();

			connManager.closeIdleConnections(1800, TimeUnit.SECONDS);

			log.info("Client: {} PoolStats: {}", clientName, poolStats);

		}

	}

}
