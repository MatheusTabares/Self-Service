package br.com.mastermenu.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.DAOGenerico;
import java.io.Serializable;


@ManagedBean
@SessionScoped
public class ItemBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Item item = new Item();
	private List<Item> itens;
	private List<Item> itensFiltrados;
	
	
	 public ItemBean() {
		super();
	}

	public void prepararPesquisa() {
	        try {
	        	new DAOGenerico<Item>(Item.class).listaTodos();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	 public void preparaNovo() {
	        Item item = new Item();
	    }
	 
	 public void incluirPratoPrincipal() {
	        try {
	        	
	        	if (this.item.getId() == null) {
	    			new DAOGenerico<Item>(Item.class).adiciona(this.item);
	    		} else {
	    			new DAOGenerico<Item>(Item.class).atualiza(this.item);
	    		}
	        }
	        	catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
	    	}
	        	
	        	
	        	
	    public void excluir() {
	        try {

	        	System.out.println("Removendo cliente " + item.getNome());
	    		new DAOGenerico<Item>(Item.class).remove(item);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

		public Item getPrato() {
			return item;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public List<Item> getItens() {
			return itens;
		}

		public void setItens(List<Item> itens) {
			this.itens = itens;
		}

		public List<Item> getItensFiltrados() {
			return itensFiltrados;
		}

		public void setItensFiltrados(List<Item> itensFiltrados) {
			this.itensFiltrados = itensFiltrados;
		}
		

}