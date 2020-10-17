package com.wdf.location.httpclient.model;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HttpClientRequest<A, B> {

	String clientName;

	HttpMethod httpMethod;

	String completeUrl;

	A body;

	Map<String, String> headers;

	Class<B> responseType;

	MultiValueMap<String, String> queryParams;

}
