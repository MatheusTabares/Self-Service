package br.com.mastermenu.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.persistencia.EnderecoDAO;

@ManagedBean(name = "enderecoBean")
@SessionScoped
public class EnderecoBean{
	private EnderecoDAO enderecoDAO = new EnderecoDAO();
	
	public void salvar(Endereco endereco) {
		enderecoDAO = new EnderecoDAO();
		enderecoDAO.salvar(endereco);
	}

	public EnderecoDAO getEnderecoDAO() {
		return enderecoDAO;
	}

	public void setEnderecoDAO(EnderecoDAO enderecoDAO) {
		this.enderecoDAO = enderecoDAO;
	}
	
}
