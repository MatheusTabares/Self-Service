package br.com.mastermenu.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.persistencia.DAOGenerico;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {
	private Cliente cliente = new Cliente();
	private String confirmarSenha;
	private String destinoSalvar;
	
	public String novo() {
		this.destinoSalvar = "sucesso";
		this.cliente = new Cliente();
		this.cliente.setAtivo(true);
		this.cliente.setTipo("cliente");
		this.cliente.setImagem("imagem");
		return "cadastrarCliente";
	}
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.cliente.getSenha();
		if(!senha.equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		Long id = cliente.getId();
		if(id == null || id == 0) {
			new DAOGenerico<Cliente>(Cliente.class).adiciona(this.cliente);
		} else {
			new DAOGenerico<Cliente>(Cliente.class).atualiza(this.cliente);
		}
		return this.destinoSalvar;
	}
	
	public void excluir(Cliente cliente) {
		new DAOGenerico<Cliente>(Cliente.class).remove(this.cliente);
	}
	
	public String alterar() {
		this.confirmarSenha = this.cliente.getSenha();
		return "cadastrarCliente";
	}
	

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

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}
}
