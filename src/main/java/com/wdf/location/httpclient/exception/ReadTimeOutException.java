package com.wdf.location.httpclient.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class ReadTimeOutException extends RuntimeException {

	private static Integer CODE = 503;

	private static String MESSAGE = "Downstream system failed.";

	private String client;

	public ReadTimeOutException(String client) {

		super(client + " " + MESSAGE);

		this.client = client;

	}

	public ReadTimeOutException(Throwable cause) {

		super(StringUtils.isNotBlank(cause.getMessage()) ? cause.getMessage() : MESSAGE);

	}

}
