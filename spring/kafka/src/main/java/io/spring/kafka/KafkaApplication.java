package io.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.kafka.service.KafkaService;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@RestController
	public static class Rest {
		
		private final KafkaService ks;
		
		public Rest(KafkaService ks) {
			this.ks = ks;
		}

		@GetMapping(path = "/kafka/send")
		public void send() {
			ks.send("event-stream", "test...");
		}
	}

}
