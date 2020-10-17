package com.wdf.location.httpclient;

import com.wdf.location.httpclient.model.HttpClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@Service
public class RetryableHttpClient extends HttpClient {

	public <A, B> B request(String clientName, HttpMethod httpMethod, String completeUrl, A body,
			Map<String, String> headers, Class<B> responseType, MultiValueMap<String, String> queryParams,
			RetryTemplate template) {

		B response = template.execute(
				context -> request(clientName, httpMethod, completeUrl, body, headers, responseType, queryParams));

		log.info("Response recieved {}", response);

		return response;

	}

	public <A, B> B request(HttpClientRequest<A, B> httpClientRequest, RetryTemplate template) {

		return request(httpClientRequest.getClientName(), httpClientRequest.getHttpMethod(),
				httpClientRequest.getCompleteUrl(), httpClientRequest.getBody(), httpClientRequest.getHeaders(),
				httpClientRequest.getResponseType(), httpClientRequest.getQueryParams(), template);

	}

}
