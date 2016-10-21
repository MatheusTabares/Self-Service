/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mastermenu.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import br.com.mastermenu.model.Ingrediente;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;

@ManagedBean
@ViewScoped
public class ItemBean {
	
	private Item item = new Item();
	private List<Item> itens;
	private Byte foto;
	private List<Item> itensFiltrados;
	private List<Ingrediente> ingredientesSelecionados;
	
	@PostConstruct
	public void prepararPesquisa() {
		try {
			ItemDAO dao = new ItemDAO();
			setItens(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void preparaNovo() {
		item = new Item();
	}
	
	
	public void incluir() {
		try {
			ItemDAO dao = new ItemDAO();
			item.setIngredientes(ingredientesSelecionados);
			dao.incluir(getItem());
			setItens(dao.listar());
		} catch (Exception ex) {
			ex.getMessage();

		}

		item = new Item();
	}

	public void exclusaoLogica() {
		try {

			ItemDAO dao = new ItemDAO();
			item.setAtivo(false);
			dao.alterar(getItem());
			setItens(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
		}
	}

	public void exclusaoFisica() {
		try {

			ItemDAO dao = new ItemDAO();
			dao.remover(getItem());
			setItens(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
		}
	}

	public void editar() {
		try {
			ItemDAO dao = new ItemDAO();
			dao.alterar(getItem());

			setItens(dao.listar());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	 public void downloadArquivo(Item i) {

	        FacesContext facesContext = FacesContext.getCurrentInstance();

	        if (i.getFoto() == null) {

	            facesContext.addMessage(null, new FacesMessage("Foto não encontrada!"));

	        } else {

	            OutputStream out = null;

	            try {
	                
	                ExternalContext context = facesContext.getExternalContext();
	                
	                HttpServletResponse response = (HttpServletResponse) context.getResponse();
	                response.setHeader("Content-Disposition", "attachment;filename=\""+i.getNome()+".jpeg\"");
	                response.setContentType("application/download");
	                
	                out = response.getOutputStream();
	                out.write(i.getFoto());
	                
	                facesContext.responseComplete();
	                
	            } catch (IOException ex) {
	                Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
	            }finally{
	                if(out != null){
	                    try {
	                        out.flush();
	                        out.close();
	                    } catch (IOException ex) {
	                        Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	                }
	            }

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

	public List<Ingrediente> getIngredientesSelecionados() {
		return ingredientesSelecionados;
	}

	public void setIngredientesSelecionados(List<Ingrediente> ingredientesSelecionados) {
		this.ingredientesSelecionados = ingredientesSelecionados;
	}

	public Byte getFoto() {
		return foto;
	}

	public void setFoto(Byte foto) {
		this.foto = foto;
	}



}
