package com.wdf.location.kafka.sender;

import com.wdf.location.kafka.config.KafkaSenderConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@Slf4j
@Component
@ConditionalOnBean(KafkaSenderConfig.class)
public class KafkaSender {

	@Autowired
	KafkaSenderConfig senderConfig;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/*
	 * send to producer inputs : topic,payload
	 */
	public void send(String topic, String payload) {

		log.info("sending payload to topic='{}: payload={}'", topic, payload);

		ListenableFuture<SendResult<String, String>> kafkaResponse = kafkaTemplate.send(topic, payload);

		SuccessCallback<SendResult<String, String>> successCallback = result -> {

			log.info("Successfully sent message [{}] in kafka Topic : {}", payload, topic);

			MDC.clear();

		};

		FailureCallback failureCallback = ex -> {

			log.warn("Not able to sent message : [{}] into kafka Topic : {} ... Retrying", payload, topic);

			retry(topic, payload, 0);

			MDC.clear();

		};

		kafkaResponse.addCallback(successCallback, failureCallback);

	}

	/*
	 * send to producer inputs : topic,key,payload
	 */
	public void send(String topic, String key, String payload) {

		log.info("sending payload to topic='{}: payload={}'", topic, payload);

		ListenableFuture<SendResult<String, String>> kafkaResponse = kafkaTemplate.send(topic, key, payload);

		SuccessCallback<SendResult<String, String>> successCallback = result -> {

			log.info("Successfully sent message[{}] in kafka Topic : {}", payload, topic);

			MDC.clear();

		};

		FailureCallback failureCallback = ex -> {

			log.warn("Not able to sent message : {} into kafka Topic : {} ... Retrying", payload, topic);

			retry(topic, key, payload, 0);

			MDC.clear();

		};

		kafkaResponse.addCallback(successCallback, failureCallback);

	}

	private void retry(String topic, String payload, int retryCount) {

		retryCount++;

		ListenableFuture<SendResult<String, String>> kafkaResponse = kafkaTemplate.send(topic, payload);

		int finalRetryCount = retryCount;

		SuccessCallback<SendResult<String, String>> successCallback = result -> {

			log.info("Successfully sent message[{}] in kafka Topic : {} in : {} retry attempt", payload, topic,
					finalRetryCount);

		};

		FailureCallback failureCallback = ex -> {

			if (finalRetryCount < senderConfig.getRetryCount()) {

				retry(topic, payload, finalRetryCount);

			}
			else {

				log.error("Message {} not sent after {} try ", payload, finalRetryCount);

			}

		};

		kafkaResponse.addCallback(successCallback, failureCallback);

	}

	private void retry(String topic, String key, String payload, int retryCount) {

		retryCount++;

		ListenableFuture<SendResult<String, String>> kafkaResponse = kafkaTemplate.send(topic, key, payload);

		int finalRetryCount = retryCount;

		SuccessCallback<SendResult<String, String>> successCallback = result -> log.info(
				"Successfully sent message[{}] in kafka Topic : {} in : {} retry attempt", payload, topic,
				finalRetryCount);

		FailureCallback failureCallback = ex -> {

			if (finalRetryCount < senderConfig.getRetryCount()) {

				retry(topic, key, payload, finalRetryCount);

			}
			else {

				log.error("Message {} not sent after {} try ", payload, finalRetryCount);

			}

		};

		kafkaResponse.addCallback(successCallback, failureCallback);

	}

}
