package com.wdf.location.kafka.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("kafka.sender.bootstrap-servers")
@ConfigurationProperties("kafka.sender")
@Data
public class KafkaSenderConfig {

	private String bootstrapServers;

	private Integer retryCount;

}
