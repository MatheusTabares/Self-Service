package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.mastermenu.model.Pedido;

@ManagedBean(name = "copaBean")
@SessionScoped
public class CopaBean {
	private List<Pedido> pedidosCopa;
	private Pedido pedido;
	
	public CopaBean() {
		this.pedidosCopa = new ArrayList<Pedido>();
		this.pedido = new Pedido();
	}
	
	public void salvar() {}

	public List<Pedido> getPedidosCopa() {
		return pedidosCopa;
	}

	public void setPedidosCopa(List<Pedido> pedidosCopa) {
		this.pedidosCopa = pedidosCopa;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
}
