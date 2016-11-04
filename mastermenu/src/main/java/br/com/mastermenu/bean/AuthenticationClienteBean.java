package br.com.mastermenu.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.persistencia.ClienteDAO;
import br.com.mastermenu.util.HashUtil;

@SessionScoped
@ManagedBean
public class AuthenticationClienteBean {
private Cliente clienteLogado;
	

	public Cliente getClienteLogado() {
		if (clienteLogado == null) {
			clienteLogado = new Cliente();

		}
		return clienteLogado;
	}

	public void setClienteLogado(Cliente clienteLogado) {
		this.clienteLogado = clienteLogado;
	}

	public String autenticar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			ClienteDAO clienteDao = new ClienteDAO();
			Cliente cliente = new Cliente();
			//SegurancaSenhaDAO segurancaSenhaDAO = new SegurancaSenhaDAO();
			String senhaCompleta = null;
			cliente = clienteDao.carregarPorEmail(this.clienteLogado.getEmail());
			if (cliente != null) {
				senhaCompleta = HashUtil.geraHash(this.clienteLogado.getSenha(), cliente.getSegurancaSenha().getSALT());
			} else {
				FacesMessage msg = new FacesMessage("Usuário inválido!");
				context.addMessage(null, msg);
				return null;
			}

			if (senhaCompleta != null && senhaCompleta.equals(cliente.getSenha())) {
				FacesMessage msg = new FacesMessage("Logado!");
				clienteLogado = cliente;
				context.addMessage(null, msg);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("homeCliente.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				FacesMessage msg = new FacesMessage("Senha Inválida");
				context.addMessage(null, msg);
				return null;
			}
			

		} catch (RuntimeException ex) {
			FacesMessage msg = new FacesMessage("Erro ao Autenticar");
			context.addMessage(null, msg);
		}
		return null;

	}
	public String sair() {
		clienteLogado = null;
		return "/loginCliente.xhtml?faces-redirect=true";
		
	}
	

}
