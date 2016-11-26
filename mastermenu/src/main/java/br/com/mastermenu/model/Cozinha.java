package br.com.mastermenu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "cozinha")
public class Cozinha implements Serializable{
	
	private static final long serialVersionUID = -2929123743178307906L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	private boolean aberta = true;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(targetEntity = Item.class)
	@JoinTable(name = "cozinha_tem_pedidos",
	joinColumns = @JoinColumn(unique=false, name = "cozinha_id"))
	private List<Item> pedidosSolicitados = new ArrayList<Item>();
	
	public Long getId() {
		return id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Item> getPedidosSolicitados() {
		return pedidosSolicitados;
	}
	public void setPedidosSolicitados(List<Item> pedidosSolicitados) {
		this.pedidosSolicitados = pedidosSolicitados;
	}
	public boolean isAberta() {
		return aberta;
	}
	public void setAberta(boolean aberta) {
		this.aberta = aberta;
	}
	
}
