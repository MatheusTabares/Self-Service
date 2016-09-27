package br.com.mastermenu.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idItem", updatable = false, nullable = false)
	private Long idItem;
	
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(targetEntity = Produto.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "item_idProduto", referencedColumnName = "idProduto")
    private List<Produto> produtos;
	
	@Column(name= "ativo")
	private boolean ativo = true;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "quantidade")
	private int quantidade;


	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}


	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


}