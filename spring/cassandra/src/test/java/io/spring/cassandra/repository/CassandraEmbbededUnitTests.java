package io.spring.cassandra.repository;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionIntegrationTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.spring.cassandra.CassandraApplication;
import io.spring.cassandra.domain.Cliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CassandraApplication.class })
@TestExecutionListeners({ CassandraUnitDependencyInjectionIntegrationTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@CassandraDataSet(keyspace = "sva_gestao", value = { "cql/sva.cql" })
@EmbeddedCassandra
@TestPropertySource("/application.properties")
public class CassandraEmbbededUnitTests {

	@Autowired
	private ClienteRepository cr;
	
	@Before
	public void setup() {
		Cliente cliente = new Cliente();
		cliente.setMsisdn("11987654321");
		cliente.setNome("teste");
		cr.save(cliente);
	}

	@Test
	public void testCount() throws Exception {
		Assert.assertEquals(5, cr.count());
	}

}
