package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.model.Profissional;
import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.persistencia.ProfissionalDAO;
import br.com.mastermenu.persistencia.SegurancaSenhaDAO;
import br.com.mastermenu.util.HashUtil;

@SessionScoped
@ManagedBean(name = "profissionalBean")
public class ProfissionalBean {
	private SegurancaSenha segurancaSenha = new SegurancaSenha();
	private SegurancaSenhaDAO segurancaSenhaDAO = new SegurancaSenhaDAO();
	private String senha;
	private String novaSenha;
	private EnderecoBean enderecoBean = new EnderecoBean(); 
	private Endereco endereco = new Endereco();
	private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
	private Profissional profissional = new Profissional();
	private String destinoSalvar;
	private String confirmarSenha;
	private List<Profissional> lista = new ArrayList<Profissional>();
	private List<Profissional> listaAtivos = new ArrayList<Profissional>();
	
	public String novo(){
		this.destinoSalvar = "sucessoProfissional";
		this.profissional = new Profissional();
		this.profissional.setAtivo(true);
		this.profissional.setImagem("imagem");
		this.profissional.setToken("token");
		this.profissional.setSalario(0.0f);
		return "cadastrarProfissional";
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.profissional.getSenha();
		if(!senha.trim().equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		
		this.profissionalDAO = new ProfissionalDAO();
		this.enderecoBean= new EnderecoBean();
		this.profissional.setSenha(HashUtil.geraHash(this.profissional.getSenha(), this.segurancaSenha.getSALT()));
		this.profissional.setEndereco(this.endereco);
		this.profissionalDAO.salvar(this.profissional);
		this.segurancaSenha.setUsuario(this.profissionalDAO.carregar(this.profissional.getId()));
		this.segurancaSenhaDAO.salvar(this.segurancaSenha);
		this.endereco.setUsuario(this.profissionalDAO.carregar(this.profissional.getId()));
		this.enderecoBean.salvar(endereco);
		FacesMessage facesMessage = new FacesMessage("Profissional cadastrado com sucesso.");
		context.addMessage(null, facesMessage);
		return this.destinoSalvar;
	}
	
	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String confirmarSenha = HashUtil.geraHash(this.confirmarSenha, this.segurancaSenha.getSALT());
		String senha = this.profissional.getSenha();
		if(!senha.trim().equals(confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		this.profissionalDAO = new ProfissionalDAO();
		this.profissionalDAO.atualizar(profissional);
		this.enderecoBean.atualizar(this.endereco);
		FacesMessage facesMessage = new FacesMessage("Profissional atualizado com sucesso.");
		context.addMessage(null, facesMessage);
		this.confirmarSenha = "";
		return this.destinoSalvar;
	}
	
	public String alterarSenha() {
		if(this.senha.trim().equals("") || this.senha.trim().equals(null)
				|| this.novaSenha.trim().equals("") || this.novaSenha.trim().equals(null)
					|| this.confirmarSenha.trim().equals("") || this.confirmarSenha.trim().equals(null)) {
			FacesMessage facesMessage = new FacesMessage("Senha(s) inválida(s).");
			return null;
		}
		String senha = HashUtil.geraHash(this.senha, this.segurancaSenha.getSALT());
		if(!senha.trim().equals(this.profissional.getSenha()) ||
				!this.novaSenha.trim().equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("Senhas diferentes.");
			return null;
		} else {
			String novaSenha = HashUtil.geraHash(this.novaSenha, segurancaSenha.getSALT());
			this.profissional.setSenha(novaSenha);	
			atualizar();
			FacesMessage facesMessage = new FacesMessage("Senha alterada com sucesso.");
		}
		this.senha = "";
		this.novaSenha = "";
		this.confirmarSenha = "";
		
		return "visualizarProfissional";
	}
	
	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		profissionalDAO = new ProfissionalDAO();
		profissionalDAO.excluir(profissional);
		FacesMessage facesMessage = new FacesMessage("Profissional excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}
	
	public void excluirLogicamente() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.profissionalDAO = new ProfissionalDAO();
		this.profissional.setAtivo(false);
		this.profissionalDAO.atualizar(this.profissional);
		FacesMessage facesMessage = new FacesMessage("Profissional excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}
	
	public void listar() {
		profissionalDAO = new ProfissionalDAO();
		lista = profissionalDAO.listar();
	}
	
	public String alterar() {
		this.confirmarSenha = "";
		//this.confirmarSenha = this.cliente.getSenha();
		return "alterarProfissional";
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

	public List<Profissional> getListaAtivos() {
		this.profissionalDAO = new ProfissionalDAO();
		this.listaAtivos = this.profissionalDAO.listarAtivos();
		return listaAtivos;
	}

	public void setListaAtivos(List<Profissional> listaAtivos) {
		this.listaAtivos = listaAtivos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public SegurancaSenha getSegurancaSenha() {
		return segurancaSenha;
	}

	public void setSegurancaSenha(SegurancaSenha segurancaSenha) {
		this.segurancaSenha = segurancaSenha;
	}

	public SegurancaSenhaDAO getSegurancaSenhaDAO() {
		return segurancaSenhaDAO;
	}

	public void setSegurancaSenhaDAO(SegurancaSenhaDAO segurancaSenhaDAO) {
		this.segurancaSenhaDAO = segurancaSenhaDAO;
	}	
	
}
