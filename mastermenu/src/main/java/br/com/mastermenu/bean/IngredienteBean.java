package br.com.mastermenu.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.mastermenu.model.Ingrediente;
import br.com.mastermenu.persistencia.IngredienteDAO;

@ManagedBean
@ViewScoped
public class IngredienteBean {

	private Ingrediente ingrediente = new Ingrediente();
	private List<Ingrediente> ingredientes;
	private List<Ingrediente> ingredientesFiltrados;
	

	@PostConstruct
	public void prepararPesquisa() {
		try {
			IngredienteDAO dao = new IngredienteDAO();
			setIngredientes(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void preparaNovo(){
        ingrediente = new Ingrediente();
    }

	public void incluir() {
		try {
			IngredienteDAO dao = new IngredienteDAO();
			dao.incluir(getIngrediente());
			setIngredientes(dao.listar());
		} catch (Exception ex) {
			ex.getMessage();

		}

		ingrediente = new Ingrediente();
	}

	public void exclusaoLogica() {
        try{
    
        IngredienteDAO dao = new IngredienteDAO();
        ingrediente.setAtivo(false);
        dao.alterar(getIngrediente());
        setIngredientes(dao.listar());
        }catch(Exception ex){
           ex.printStackTrace();
           ex.getMessage();
        }
    }
  
  public void exclusaoFisica() {
      try{
  
      IngredienteDAO dao = new IngredienteDAO();
      dao.remover(getIngrediente());
      setIngredientes(dao.listar());
      }catch(Exception ex){
         ex.printStackTrace();
         ex.getMessage();
      }
  }

	public void editar() {
		try {
			IngredienteDAO dao = new IngredienteDAO();
			dao.alterar(getIngrediente());
			setIngredientes(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<Ingrediente> getIngredientesFiltrados() {
		return ingredientesFiltrados;
	}

	public void setIngredientesFiltrados(List<Ingrediente> ingredientesFiltrados) {
		this.ingredientesFiltrados = ingredientesFiltrados;
	}

}
