package com.wdf.location.httpclient.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class HttpClientException extends RuntimeException {

	public HttpClientException(String s) {

		super(s);

	}

	public HttpClientException(Throwable e) {

		super(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName());

	}

}
