/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mastermenu.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import br.com.mastermenu.model.Ingrediente;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ItemBean {

	private Item item = new Item();
	private List<Item> itens;
	private List<Item> itensFiltrados;
	private List<Ingrediente> ingredientesSelecionados;
	private UploadedFile fotoUpload;
	private StreamedContent fotoDownload;
	private StreamedContent metodo;

	@PostConstruct
	public void prepararPesquisa() {
		fotoDownload = new DefaultStreamedContent();
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

	public StreamedContent getFotoDownload() throws IOException {
		try {
			if (fotoUpload != null && fotoUpload.getContents() != null) {
				fotoDownload = new DefaultStreamedContent(new ByteArrayInputStream(fotoUpload.getContents()),
						"image/jpg", fotoUpload.getFileName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (fotoDownload.getStream() != null) {
			fotoDownload.getStream().reset();
		}

		return fotoDownload;
	}

	public void upload(FileUploadEvent fileUploadEvent) throws Exception {
		fotoUpload = fileUploadEvent.getFile();
		item.setFoto(fotoUpload.getContents());
		fotoDownload = new DefaultStreamedContent(new ByteArrayInputStream(fotoUpload.getContents()), "image/jpg",
				fotoUpload.getFileName());
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			fotoDownload = new DefaultStreamedContent(event.getFile().getInputstream());
			byte[] foto = event.getFile().getContents();
			this.item.setFoto(foto);
		} catch (IOException ex) {
			Logger.getLogger(ItemBean.class.getName()).log(Level.ERROR, null, ex);
		}
	}



	public StreamedContent metodo() throws IOException, Exception {
		FacesContext context = FacesContext.getCurrentInstance();

		if (item.getFoto() == null) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			ItemDAO dao = new ItemDAO();
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			return new DefaultStreamedContent(new ByteArrayInputStream(item.getFoto()));
			}
		}
	
	public StreamedContent getImage() throws IOException, Exception {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String itemId = context.getExternalContext().getRequestParameterMap().get("itemId");
			ItemDAO dao = new ItemDAO();
			Item item = dao.carregar(Long.valueOf(itemId));

			return new DefaultStreamedContent(new ByteArrayInputStream(item.getFoto()));

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

	public UploadedFile getFotoUpload() {
		return fotoUpload;
	}

	public void setFotoUpload(UploadedFile fotoUpload) {
		this.fotoUpload = fotoUpload;
	}

	public void setFotoDownload(StreamedContent fotoDownload) {
		this.fotoDownload = fotoDownload;
	}

	public StreamedContent getMetodo() {
		return metodo;
	}

	public void setMetodo(StreamedContent metodo) {
		this.metodo = metodo;
	}

}
