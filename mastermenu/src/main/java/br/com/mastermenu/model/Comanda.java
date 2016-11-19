package br.com.mastermenu.model;

public class Comanda {
	private Cliente cliente = new Cliente();
	private Pedido pedido = new Pedido();
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
