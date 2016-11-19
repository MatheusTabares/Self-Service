package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mastermenu.model.Pedido;

@ManagedBean(name = "comandaBean")
@SessionScoped
public class ComandaBean {
	private List<Pedido> pedidos;
	private Double total = 0.0;
	
	public ComandaBean() {
		this.pedidos = new ArrayList<Pedido>();
	}
	
	/*public void gerarTotal() {
		this.total = 0.0;
		for(int i = 0; i < this.pedidos.size(); i++) {
			this.total += this.pedidos.get(i).getSubTotal(); 
		}
//	}*/

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
