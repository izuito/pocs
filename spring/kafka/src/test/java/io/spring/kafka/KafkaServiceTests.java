package io.spring.kafka;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.kafka.service.KafkaService;

@DirtiesContext
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaServiceTests {
	
	private static final String TOPIC = "event-stream";
	private static final String GROUP_ID_CONFIG = "sva-gestao-group";
	private static final String ENABLE_AUTO_COMMIT_CONFIG = "false";

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TOPIC);
	
	private KafkaMessageListenerContainer<String, String> container;
	
	private BlockingQueue<ConsumerRecord<String, String>> records;
	
	@Before
	public void start() {
		records = new LinkedBlockingQueue<>();
		Map<String, Object> consumer = KafkaTestUtils.consumerProps(embeddedKafka.getBrokersAsString(), GROUP_ID_CONFIG, ENABLE_AUTO_COMMIT_CONFIG);
		container = new KafkaMessageListenerContainer<>(new DefaultKafkaConsumerFactory<String, String>(consumer), new ContainerProperties(TOPIC));
		container.setupMessageListener(getMessageListener());
		container.setBeanName("templateTests");		
		container.start();
	}

	@Test
	public void testName() throws Exception {
		
		KafkaTemplate<String, String> kt = new KafkaTemplate<>(getProducerFactory());
		
		KafkaService ks = new KafkaService(kt);

		ks.send(TOPIC, "test");
		
		assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("test"));
		
	}
	
	@After
	public void stop() {
		container.stop();
	}

	private ProducerFactory<String, String> getProducerFactory() throws Exception {
		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
		Map<String, Object> sender = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
		return new DefaultKafkaProducerFactory<String, String>(sender);
	}
	
	private MessageListener<String, String> getMessageListener() {
		return new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				records.add(record);
			}
		};
	}
	
}
