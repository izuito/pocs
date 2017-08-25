package io.spring.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CassandraApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CassandraApplication.class, args);
	}

}
