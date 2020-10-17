package com.wdf.location.httpclient;

import com.wdf.location.httpclient.exception.HttpClientException;
import com.wdf.location.httpclient.exception.ReadTimeOutException;
import com.wdf.location.httpclient.model.HttpClientNotRegisteredException;
import com.wdf.location.httpclient.model.HttpClientRequest;
import com.wdf.location.httpclient.store.HttpClientStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Service
public class HttpClient {

	@Autowired
	private HttpClientStore clientsStore;

	public <A, B> B request(HttpClientRequest<A, B> httpClientRequest) {

		return request(httpClientRequest.getClientName(), httpClientRequest.getHttpMethod(),
				httpClientRequest.getCompleteUrl(), httpClientRequest.getBody(), httpClientRequest.getHeaders(),
				httpClientRequest.getResponseType(), httpClientRequest.getQueryParams());

	}

	public <A, B> B request(String clientName, HttpMethod httpMethod, String completeUrl, A body,
			Map<String, String> headers, Class<B> responseType, MultiValueMap<String, String> queryParams) {

		RestTemplate template = clientsStore.getRestTemplateForClient(clientName);

		// System.setProperty("https.protocols", "TLSv1.2");

		if (template == null) {

			throw new HttpClientNotRegisteredException(clientName);

		}

		ResponseEntity<B> responseEntity = null;

		try {

			URI url = getURI(completeUrl, queryParams);

			HttpHeaders headersMultiValueMap = new HttpHeaders();

			if (!CollectionUtils.isEmpty(headers)) {

				for (Entry<String, String> headerEntry : headers.entrySet()) {

					headersMultiValueMap.add(headerEntry.getKey(), headerEntry.getValue());

				}

			}

			RequestEntity<A> requestEntity = new RequestEntity<>(body, headersMultiValueMap, httpMethod, url);

			responseEntity = template.exchange(requestEntity, responseType);

		}
		catch (HttpStatusCodeException he) {

			log.error("Http Status Code Error: {} {}", he.getRawStatusCode(), he.getResponseBodyAsString());

			throw new HttpClientException(he);

		}
		catch (URISyntaxException he) {

			log.error("Wrong URI Syntax: {} {}", he);

			throw new HttpClientException("URL Syntactically Incorrect: " + completeUrl);

		}
		catch (ResourceAccessException e) {

			log.trace("Health Error: {} {}", e);

			handleResourceAccessException(e);

		}
		catch (Exception e) {

			log.error("Http Error: {} {}", e.getMessage());

			throw new HttpClientException(e);

		}

		if (responseEntity != null) {

			return responseEntity.getBody();

		}

		return null;

	}

	protected URI getURI(String completeURL, MultiValueMap<String, String> queryParams) throws URISyntaxException {

		URI uri = new URI(completeURL);

		UriComponents uriComponents = UriComponentsBuilder.fromUri(uri).queryParams(queryParams).build();

		return uriComponents.toUri();

	}

	protected void handleResourceAccessException(ResourceAccessException e) {

		log.error("Handling client access exception: {} ", e.getMessage());

		ReadTimeOutException exception = new ReadTimeOutException(e);

		// read timeout from client
		if (e.getCause() instanceof SocketTimeoutException) {

			throw exception;

		}
		else {

			throw new HttpClientException(e);

		}

	}

}
