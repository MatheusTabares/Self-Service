package br.com.mastermenu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Endereco")
public class Endereco implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5718287958022963461L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_endereco", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "estado", length = 50, nullable = false)
	private String estado;
	
	@Column(name = "uf", length = 2, nullable = false)
	private String uf;
	
	@Column(name = "cidade", length = 50, nullable = false)
	private String cidade;
	
	@Column(name = "bairro", length = 50, nullable = false)
	private String bairro;
	
	@Column(name = "logradouro", length = 50, nullable = false)
	private String logradouro;
	
	@Column(name = "numero", length = 6, nullable = false)
	private Integer numero;
	
	@Column(name = "cep", length = 9, nullable = false)
	private String cep;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
}
