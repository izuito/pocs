package io.spring.kafka;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasPartition;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class KafkaTemplateTests {

	private static final String TEMPLATE_TOPIC = "event-stream";

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TEMPLATE_TOPIC);
	
	private KafkaMessageListenerContainer<Integer, String> container;
	
	private BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();
	
	@Before
	public void start() {
		Map<String, Object> consumer = KafkaTestUtils.consumerProps("sva-gestao-group", "false", embeddedKafka);
		DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(consumer);
		container = new KafkaMessageListenerContainer<>(cf, new ContainerProperties(TEMPLATE_TOPIC));
		container.setupMessageListener(new MessageListener<Integer, String>() {
			@Override
			public void onMessage(ConsumerRecord<Integer, String> record) {
				records.add(record);
			}
		});
		container.setBeanName("templateTests");		
		container.start();
	}

	@Test
	public void testTemplate() throws Exception {

		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
		Map<String, Object> sender = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
		ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(sender);
		KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
		
		template.setDefaultTopic(TEMPLATE_TOPIC);
		template.sendDefault("foo");
		assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("foo"));
		
		template.sendDefault(0, 2, "bar");
		ConsumerRecord<Integer, String> received = records.poll(10, TimeUnit.SECONDS);
		assertThat(received, hasKey(2));
		assertThat(received, hasPartition(0));
		assertThat(received, hasValue("bar"));
		
		template.send(TEMPLATE_TOPIC, 0, 2, "baz");
		received = records.poll(10, TimeUnit.SECONDS);
		assertThat(received, hasKey(2));
		assertThat(received, hasPartition(0));
		assertThat(received, hasValue("baz"));
		
	}
	
	@After
	public void stop() {
		container.stop();
	}

}
