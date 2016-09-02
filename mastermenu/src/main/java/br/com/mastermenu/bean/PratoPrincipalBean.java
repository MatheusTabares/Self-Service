package br.com.mastermenu.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.mastermenu.model.PratoPrincipal;
import br.com.mastermenu.persistencia.DAOGenerico;


@ManagedBean
@SessionScoped
public class PratoPrincipalBean {
	
	private PratoPrincipal prato = new PratoPrincipal();
	private List<PratoPrincipal> pratos;
	private List<PratoPrincipal> pratosFiltrados;
	
	
	 public PratoPrincipalBean() {
		super();
	}

	public void prepararPesquisa() {
	        try {
	        	new DAOGenerico<PratoPrincipal>(PratoPrincipal.class).listaTodos();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	 public void preparaNovo() {
	        PratoPrincipal prato = new PratoPrincipal();
	    }
	 
	 public void incluirPratoPrincipal() {
	        try {
	        	
	        	if (this.prato.getId() == null) {
	    			new DAOGenerico<PratoPrincipal>(PratoPrincipal.class).adiciona(this.prato);
	    		} else {
	    			new DAOGenerico<PratoPrincipal>(PratoPrincipal.class).atualiza(this.prato);
	    		}
	        }
	        	catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
	    	}
	        	
	        	
	        	
	    public void excluir() {
	        try {

	        	System.out.println("Removendo cliente " + prato.getNome());
	    		new DAOGenerico<PratoPrincipal>(PratoPrincipal.class).remove(prato);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

		public PratoPrincipal getPrato() {
			return prato;
		}

		public void setPrato(PratoPrincipal prato) {
			this.prato = prato;
		}

		public List<PratoPrincipal> getPratos() {
			return pratos;
		}

		public void setPratos(List<PratoPrincipal> pratos) {
			this.pratos = pratos;
		}

		public List<PratoPrincipal> getPratosFiltrados() {
			return pratosFiltrados;
		}

		public void setPratosFiltrados(List<PratoPrincipal> pratosFiltrados) {
			this.pratosFiltrados = pratosFiltrados;
		}
	    
}