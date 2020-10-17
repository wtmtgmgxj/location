package com.wdf.location.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("kafka.topics")
@Data
public class KafkaTopics {

	private String firstTopic;

}
