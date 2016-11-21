package br.com.mastermenu.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;

@ManagedBean
@SessionScoped
public class CardapioBean {

	private Item item = new Item();
	private List<Item> itens;
	private List<Item> itensFiltrados;
	private UploadedFile fotoUpload;
	private StreamedContent fotoDownload;
	private StreamedContent metodo;
	private StreamedContent metodoLista;
	

	public StreamedContent getMetodoLista() throws NumberFormatException, Exception {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {

			String itemId = context.getExternalContext().getRequestParameterMap().get("itemId");

			ItemDAO dao = new ItemDAO();

			Item i = dao.carregar(Long.valueOf(itemId));

			this.metodoLista = new DefaultStreamedContent(new ByteArrayInputStream(i.getFoto()));
			return metodoLista;
		}
	}
	
	public void buscaDados(Long id) throws NumberFormatException, Exception {
			ItemDAO dao = new ItemDAO();
			item = dao.carregar(id);
	}

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

	public void setMetodoLista(StreamedContent metodoLista) {
		this.metodoLista = metodoLista;
	}

}
