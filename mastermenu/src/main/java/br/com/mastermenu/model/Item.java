package br.com.mastermenu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.com.mastermenu.enums.Tipo;


@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idItem", updatable = false, nullable = false)
	private Long idItem;
	
	@Column(name = "nome")
	private String nome;
	
	@ManyToMany(targetEntity = Ingrediente.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Item_Ingredientes",
	joinColumns = @JoinColumn(unique=false, name = "ingrediente_id")) 
    private Collection<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	
	@Column(name= "ativo")
	private boolean ativo = true;
	
	@Column(name = "valor")
	private double valor;
	
	private Tipo tipo;
	
	@Column(name = "dataCadastro")
	private Calendar dataDeInscricao = Calendar.getInstance();


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

	public Collection<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Collection<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	

	@Override
	public String toString() {
		return nome;
	}

	

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((ingredientes == null) ? 0 : ingredientes.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (ativo != other.ativo)
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (ingredientes == null) {
			if (other.ingredientes != null)
				return false;
		} else if (!ingredientes.equals(other.ingredientes))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo != other.tipo)
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	public Calendar getDataDeInscricao() {
		return dataDeInscricao;
	}

	public void setDataDeInscricao(Calendar dataDeInscricao) {
		this.dataDeInscricao = dataDeInscricao;
	}

	
	
	

}