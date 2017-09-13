package io.spring.wso2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "io.spring.wso2" })
public class WSO2App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WSO2App.class, args);
	}
}
