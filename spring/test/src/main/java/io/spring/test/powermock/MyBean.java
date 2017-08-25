package io.spring.test.powermock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	@Autowired
	private FinalClass finalClass;

	public String sayHello() {
		return finalClass.sayHello();
	}

	public Message generateMessage() {
		final long id = IdGenerator.generateNewId();
		return new Message(id, "My bean message");
	}

}
