package br.com.mastermenu.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Produto;
import br.com.mastermenu.persistencia.ProdutoDAO;

@ManagedBean
@ViewScoped
public class ProdutoBean {

	private Produto produto = new Produto();
	private List<Produto> produtos;
	private List<Produto> produtosFiltrados;

	@PostConstruct
	public void prepararPesquisa() {
		try {
			ProdutoDAO dao = new ProdutoDAO();
			setProdutos(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void preparaNovo() {
		Produto produto = new Produto();
	}

	public String incluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.incluir(getProduto());
			setProdutos(dao.listar());
		} catch (Exception ex) {
			ex.getMessage();

		}

		return null;
	}

	public void excluir() {
		try {

			ProdutoDAO dao = new ProdutoDAO();
			dao.remover(getProduto());
			setProdutos(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
		}
	}

	public void editar() {
		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.alterar(getProduto());
			setProdutos(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
		this.produtosFiltrados = produtosFiltrados;
	}

}
