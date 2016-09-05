package br.com.mastermenu.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.PratoPrincipal;
import br.com.mastermenu.persistencia.DAOGenerico;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {
	private Cliente cliente = new Cliente();
	private String confirmarSenha;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String novo() {
		this.cliente = new Cliente();
		this.cliente.setAtivo(true);
		return "cliente";
	}
	
	public String adiciona() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.cliente.getSenha();
		if(!senha.equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		this.cliente.setAtivo(true);
		new DAOGenerico<Cliente>(Cliente.class).adiciona(this.cliente);
		
		return "sucessoCliente?faces-redirect=true";
	}
}
