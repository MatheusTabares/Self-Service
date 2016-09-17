package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.persistencia.ClienteDAO;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {
	private Cliente cliente = new Cliente();
	private String confirmarSenha;
	private String destinoSalvar;
	private List<Cliente> lista = new ArrayList<Cliente>();
	private ClienteDAO  clienteDAO = new ClienteDAO();
	
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
		clienteDAO = new ClienteDAO();
		if(id == null || id == 0) {
			clienteDAO.salvar(cliente);
			FacesMessage facesMessage = new FacesMessage("Cliente cadastrado com sucesso.");
			context.addMessage(null, facesMessage);
		} else {
			clienteDAO.atualizar(cliente);
			FacesMessage facesMessage = new FacesMessage("Cliente atualizado com sucesso.");
			context.addMessage(null, facesMessage);
		}
		return this.destinoSalvar;
	}
	
	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		clienteDAO = new ClienteDAO();
		clienteDAO.excluir(cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}
	
	public void listar() {
		clienteDAO = new ClienteDAO();
		lista = clienteDAO.listar();
	}
	
	public Cliente buscarPorId() {
		//NAO IMPLEMENTADO
		return cliente;
	}
	public String alterar() {
		//this.confirmarSenha = this.cliente.getSenha();
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

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public List<Cliente> getLista() {
		lista = clienteDAO.listar();
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}
}
