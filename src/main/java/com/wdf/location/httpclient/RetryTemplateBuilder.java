package com.wdf.location.httpclient;

import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class RetryTemplateBuilder {

	private RetryPolicy retryPolicy = new SimpleRetryPolicy();

	private BackOffPolicy backOffPolicy = new FixedBackOffPolicy();

	private RetryListener[] retryListeners = new RetryListener[] { new RetryListenerSupport() };

	public RetryTemplate build() {

		RetryTemplate retryTemplate = new RetryTemplate();

		retryTemplate.setRetryPolicy(retryPolicy);

		retryTemplate.setBackOffPolicy(backOffPolicy);

		retryTemplate.setListeners(retryListeners);

		return retryTemplate;

	}

	public RetryTemplateBuilder withRetryPolicy(RetryPolicy retryPolicy) {

		this.retryPolicy = retryPolicy;

		return this;

	}

	public RetryTemplateBuilder withBackOffPolicy(BackOffPolicy backOffPolicy) {

		this.backOffPolicy = backOffPolicy;

		return this;

	}

	public RetryTemplateBuilder withRetryListeners(RetryListener[] retryListeners) {

		this.retryListeners = retryListeners;

		return this;

	}

}
