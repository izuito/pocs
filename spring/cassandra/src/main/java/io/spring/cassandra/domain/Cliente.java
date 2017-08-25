package io.spring.cassandra.domain;

import java.util.UUID;

import org.apache.cassandra.utils.UUIDGen;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import io.spring.cassandra.enumeration.ClienteStatus;
import io.spring.cassandra.enumeration.TipoPlano;

@Table("cliente")
public class Cliente {

	@PrimaryKey
	@Column("id")
	private UUID id;

	@Column("nome")
	private String nome;

	@Column("msisdn")
	private String msisdn;

	@Column("status")
	private ClienteStatus status;

	@Column("tipo_plano")
	private TipoPlano tipoPlano;

	@Column("nome_plano")
	private String nomePlano;

	public Cliente() {
		id = UUIDGen.getTimeUUID();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public ClienteStatus getStatus() {
		return status;
	}

	public void setStatus(ClienteStatus status) {
		this.status = status;
	}

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public String getNomePlano() {
		return nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}

	@Override
	public String toString() {
		return "Cliente [id= " + id + " nome= " + nome + " msisdn= " + msisdn + " status= " + status + " tipoPlano= "
				+ tipoPlano + " nomePlano= " + nomePlano + "]";
	}

}
