package br.com.mastermenu.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.persistencia.EnderecoDAO;

@ManagedBean(name = "enderecoBean")
@SessionScoped
public class EnderecoBean{
	private Endereco endereco = new Endereco();
	private EnderecoDAO enderecoDAO = new EnderecoDAO();
	
	public void salvar(Endereco endereco) {
		this.enderecoDAO = new EnderecoDAO();
		this.enderecoDAO.salvar(endereco);
	}
	
	public void atualizar(Endereco endereco) {
		this.enderecoDAO = new EnderecoDAO();
		this.enderecoDAO.atualizar(endereco);
	}
	
	public void excluir(Long id) {
		this.enderecoDAO = new EnderecoDAO();
		this.enderecoDAO.excluir(id);
	}
	
	public Endereco encontrarPorIdUsuario(Long idUsuario) {
		Endereco endereco = new Endereco();
		this.enderecoDAO = new EnderecoDAO();
		endereco = this.enderecoDAO.encontrarPorIdUsuario(idUsuario);
		return endereco;
	}
	
	public EnderecoDAO getEnderecoDAO() {
		return enderecoDAO;
	}

	public void setEnderecoDAO(EnderecoDAO enderecoDAO) {
		this.enderecoDAO = enderecoDAO;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
