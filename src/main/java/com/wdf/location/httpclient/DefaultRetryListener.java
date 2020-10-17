package com.wdf.location.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

@Slf4j
public class DefaultRetryListener extends RetryListenerSupport {

	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {

		log.warn("Encountered following exception in previous attempt: {}", throwable.getClass().getSimpleName());

		log.warn("Exception message: {}", throwable.getMessage());

		log.warn("Retrying...");

		super.onError(context, callback, throwable);

	}

}
