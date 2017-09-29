package io.spring.wso2am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "io.spring.wso2am" })
public class WSO2AmApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WSO2AmApplication.class, args);
	}
}
