package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.model.Profissional;
import br.com.mastermenu.persistencia.ClienteDAO;
import br.com.mastermenu.persistencia.ProfissionalDAO;

@SessionScoped
@ManagedBean(name = "profissionalBean")
public class ProfissionalBean {
	private EnderecoBean enderecoBean = new EnderecoBean(); 
	private Endereco endereco = new Endereco();
	private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
	private Profissional profissional = new Profissional();
	private String destinoSalvar;
	private String confirmarSenha;
	private List<Profissional> lista = new ArrayList<Profissional>();
	
	public String novo(){
		this.destinoSalvar = "sucessoProfissional";
		this.profissional = new Profissional();
		this.profissional.setAtivo(true);
		this.profissional.setTipo("profissional");
		this.profissional.setImagem("imagem");
		this.profissional.setSalario(0.0f);
		return "cadastrarProfissional";
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.profissional.getSenha();
		if(!senha.equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		Long id = this.profissional.getId();
		this.profissionalDAO = new ProfissionalDAO();
		this.enderecoBean= new EnderecoBean();
		if(id == null || id == 0) {
			this.profissional.setEndereco(this.endereco);
			this.profissionalDAO.salvar(this.profissional);
			this.endereco.setUsuario(this.profissionalDAO.carregar(this.profissional.getId()));
			this.enderecoBean.salvar(endereco);
			FacesMessage facesMessage = new FacesMessage("Profissional cadastrado com sucesso.");
			context.addMessage(null, facesMessage);
		} else {
			this.profissionalDAO.atualizar(this.profissional);
			this.enderecoBean.atualizar(this.endereco);
			FacesMessage facesMessage = new FacesMessage("Profissional atualizado com sucesso.");
			context.addMessage(null, facesMessage);
		}
		return this.destinoSalvar;
	}
	
	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		profissionalDAO = new ProfissionalDAO();
		profissionalDAO.excluir(profissional);
		FacesMessage facesMessage = new FacesMessage("Profissional excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}
	
	public void listar() {
		profissionalDAO = new ProfissionalDAO();
		lista = profissionalDAO.listar();
	}
	
	public String alterar() {
		//this.confirmarSenha = this.cliente.getSenha();
		return "cadastrarProfissional";
	}
	
	public String visualizar() {
		return "visualizarProfissional";
	}
	
	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public ProfissionalDAO getProfissionalDAO() {
		return profissionalDAO;
	}

	public void setProfissionalDAO(ProfissionalDAO profissionalDAO) {
		this.profissionalDAO = profissionalDAO;
	}

	public List<Profissional> getLista() {
		lista = profissionalDAO.listar();
		return lista;
	}

	public void setLista(List<Profissional> lista) {
		this.lista = lista;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
	}	
}
