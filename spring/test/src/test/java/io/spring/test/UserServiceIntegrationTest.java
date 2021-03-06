package io.spring.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MocksApplication.class)
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private NameService nameService;

	@Test
	public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {

		Mockito.when(nameService.getUserName("SomeId")).thenReturn("Mock user name");

		String testName = userService.getUserName("SomeId");

		Assert.assertEquals("Mock user name", testName);

	}

}
