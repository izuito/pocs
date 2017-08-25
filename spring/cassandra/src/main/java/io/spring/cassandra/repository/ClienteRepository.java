package io.spring.cassandra.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import io.spring.cassandra.domain.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, UUID> {

	Cliente findByMsisdn(String msisdn);
	
}
