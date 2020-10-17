package com.wdf.location.request;

import lombok.Data;

import java.util.Map;

@Data
public abstract class BaseRequest {

	Map<String, String> headers;

	// QUERY PARAMS
	private Long requestTimestamp;

	private String tracer;

	// language handled by gateway at Kong level
	// private String language;

}
