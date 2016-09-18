/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mastermenu.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;


@ManagedBean
@ViewScoped
public class ItemBean {
    
     private Item item = new Item();
     private List<Item> itens;
     private List<Item> itensFiltrados;
     
     
@PostConstruct     
     public void prepararPesquisa(){
         try{
         ItemDAO dao = new ItemDAO();
         setItens(dao.listar());
         }catch(Exception ex){
             ex.printStackTrace();
         }
     }
     
     
     public void preparaNovo(){
         Item item = new Item();
     }
    
     public String incluir() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {
            ItemDAO dao = new ItemDAO();
            dao.incluir(getItem());
            setItens(dao.listar());
        } catch (Exception ex) {
            ex.getMessage();
                  
        }
        
        return null;
    }

  public void excluir() {
        try{
    
        ItemDAO dao = new ItemDAO();
        dao.remover(getItem());
        setItens(dao.listar());
        }catch(Exception ex){
           ex.printStackTrace();
           ex.getMessage();
        }
    }
  
  public void editar(){
        try{
        ItemDAO dao = new ItemDAO();
        dao.alterar(getItem());
        
        setItens(dao.listar());
        }catch(Exception ex){
            ex.printStackTrace();
        }
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
