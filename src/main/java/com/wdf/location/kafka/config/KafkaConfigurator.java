package com.wdf.location.kafka.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnBean(KafkaSenderConfig.class)
@Configuration
@Data
public class KafkaConfigurator {

	@Autowired
	KafkaSenderConfig kafkaSenderConfig;

	@Bean
	public Map<String, Object> senderConfigs() {

		Map<String, Object> props = new HashMap<>();
		// list of host:port pairs used for establishing the initial connections to the
		// Kakfa cluster

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaSenderConfig.getBootstrapServers());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return props;

	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {

		return new DefaultKafkaProducerFactory<>(senderConfigs());

	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {

		return new KafkaTemplate<>(producerFactory());

	}

}
