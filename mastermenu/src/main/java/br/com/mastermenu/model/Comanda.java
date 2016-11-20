package br.com.mastermenu.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "comanda")
public class Comanda {
	
	private Double total = 0.0;
	
	private Pedido pedido = new Pedido();
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
