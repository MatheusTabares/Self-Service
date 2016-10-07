/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mastermenu.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.mastermenu.model.Ingrediente;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;

@ManagedBean
@ViewScoped
public class ItemBean {
	
	private Item item = new Item();
	private List<Item> itens;
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
			for (Object ingrediente : ingredientesSelecionados) {
				System.out.println("class: " + ingrediente.getClass());
			}
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

}
