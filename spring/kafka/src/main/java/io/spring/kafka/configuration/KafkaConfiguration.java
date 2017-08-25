package io.spring.kafka.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class);

	@Autowired
	private ProducerFactory<String, String> pf;

	@Autowired
	private ConsumerFactory<String, String> cf;

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		logger.info("KafkaTemplate Loading...");
		return new KafkaTemplate<String, String>(pf);
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		logger.info("KafkaListenerContainerFactory Loading...");
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(cf);
		return factory;
	}

}
