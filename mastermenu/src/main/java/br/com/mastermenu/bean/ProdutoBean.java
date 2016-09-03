package br.com.mastermenu.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.mastermenu.model.Produto;
import br.com.mastermenu.persistencia.DAOGenerico;

@ManagedBean
@ViewScoped
public class ProdutoBean {
	
	
	private Produto produto = new Produto();
	private List<Produto> produtos;
	private List<Produto> produtosFiltrados;
	
	
	 public ProdutoBean() {
		super();
	}

	public void prepararPesquisa() {
	        try {
	        	new DAOGenerico<Produto>(Produto.class).listaTodos();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	 public void preparaNovo() {
	        Produto produto = new Produto();
	    }
	 
	 public void incluirOuEditarItens() {
	        try {
	        	
	        	if (this.produto.getId() == null) {
	    			new DAOGenerico<Produto>(Produto.class).adiciona(this.produto);
	    		} else {
	    			new DAOGenerico<Produto>(Produto.class).atualiza(this.produto);
	    		}
	        }
	        	catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
	    	}
	        	
	        	
	        	
	    public void excluir() {
	        try {

	        	System.out.println("Removendo Produto " + produto.getNome());
	    		new DAOGenerico<Produto>(Produto.class).remove(produto);
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
