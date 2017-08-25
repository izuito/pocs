package io.spring.test.powermock;

import org.springframework.stereotype.Component;

@Component
public final class FinalClass {

	public final String sayHello() {
		return "Hello, man!";
	}

}
