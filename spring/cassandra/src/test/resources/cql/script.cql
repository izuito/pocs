
CREATE KEYSPACE IF NOT EXISTS spring 
           WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  
            AND durable_writes = true;

CREATE TABLE spring.table (
	id timeuuid PRIMARY KEY,
	datahora timestamp,
	data date,
	texto text
);
