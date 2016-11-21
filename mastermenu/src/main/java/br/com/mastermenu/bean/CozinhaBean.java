package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.persistencia.CozinhaDAO;

@ManagedBean(name = "cozinhaBean")
@SessionScoped
public class CozinhaBean {
	private List<Pedido> pedidosCozinha;
	private Cozinha cozinha;
	private Pedido pedido;
	private CozinhaDAO cozinhaDAO;
	
	public CozinhaBean() {
		this.pedidosCozinha  = new ArrayList<Pedido>();
		this.pedido = new Pedido();
		this.cozinhaDAO = new CozinhaDAO();
		this.cozinha = new Cozinha();
	}
	
	public void salvar() {
		for(Pedido pedido : pedidosCozinha) {
			//this.cozinhaDAO.salvar(pedido);
		}
		this.cozinhaDAO = new CozinhaDAO();
		//getPedidosCozinha();
	}
	
	public Cozinha getCozinha() {
		return cozinha;
	}
	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	//public List<Pedido> getPedidosCozinha() {
		//return pedidosCozinha = this.cozinhaDAO.listar();
	//}

	public void setPedidosCozinha(List<Pedido> pedidosCozinha) {
		this.pedidosCozinha = pedidosCozinha;
	}
	
}
