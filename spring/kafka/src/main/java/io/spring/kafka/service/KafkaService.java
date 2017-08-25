package io.spring.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
	
	private final KafkaTemplate<String, String> kt;

	public KafkaService(KafkaTemplate<String, String> kt) {
		this.kt = kt;
	}

	@KafkaListener(topics = { "event-stream" })
	public void onMessage(ConsumerRecord<String, String> cr) {
		logger.info("receive:: data[{}]", cr.toString());
	}
	
	public void send(String topic, String data) {
		ListenableFuture<SendResult<String, String>> future = kt.send(topic, data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("sent message='{}' with offset={}", data, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("unable to send message='{}'", data, ex);
			}
		});
	}
	
}
