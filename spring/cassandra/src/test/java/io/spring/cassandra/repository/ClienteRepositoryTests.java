package io.spring.cassandra.repository;

import org.cassandraunit.spring.CassandraUnit;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.spring.cassandra.domain.Cliente;

@TestExecutionListeners(
		  listeners = CassandraUnitTestExecutionListener.class,
		  mergeMode = MergeMode.MERGE_WITH_DEFAULTS
		)
@CassandraUnit
@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository cr;

	@Test
	public void testCount() throws Exception {
		Assert.assertEquals(5, cr.count());
	}

	@Test
	public void testFindMsisdn() throws Exception {
		String msisdn = "61970564036";

		Cliente cliente = cr.findByMsisdn(msisdn);

		Assert.assertNotNull("Cliente NULL", cliente);

		Assert.assertEquals(msisdn, cliente.getMsisdn());
	}

}
