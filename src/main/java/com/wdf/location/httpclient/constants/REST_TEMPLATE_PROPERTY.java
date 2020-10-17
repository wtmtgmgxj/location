package com.wdf.location.httpclient.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum REST_TEMPLATE_PROPERTY {

	CREATE_CLIENTS("http.client.names", null), SOCKET_TIMEOUT("http.client.clientName.socket.timeout",
			"2000"), CONNECTION_TIMEOUT("http.client.clientName.connection.timeout",
					"2000"), CONNECTION_REQUEST_TIMEOUT("http.client.clientName.connection.request.timeout",
							"500"), MAX_CONNECTIONS("http.client.clientName.max.connections",
									"50"), MAX_CONNECTIONS_PER_ROUTE("http.client.clientName.max.per.channel",
											"20"), STALE_CONNECTIONS_CHECK_PERIOD(
													"http.client.clientName.connection.validate.inactivity.period",
													"2000"), PROXY_SCHEME("http.client.clientName.proxy.scheme",
															""), PROXY_HOST("http.client.clientName.proxy.host",
																	""), PROXY_PORT("http.client.clientName.proxy.port",
																			"8080");

	private String propertyName;

	private String defaultValue;

}
