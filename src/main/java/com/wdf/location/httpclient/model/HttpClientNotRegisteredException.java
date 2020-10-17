package com.wdf.location.httpclient.model;

import lombok.Getter;

@Getter
public class HttpClientNotRegisteredException extends RuntimeException {

	private final String clientName;

	public HttpClientNotRegisteredException(String clientName) {

		super("Http Client " + clientName + " not Registered in the store.");

		this.clientName = clientName;

	}

}
