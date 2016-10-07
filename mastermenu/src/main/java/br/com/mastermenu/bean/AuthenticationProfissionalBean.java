package br.com.mastermenu.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mastermenu.model.Profissional;
import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.persistencia.ProfissionalDAO;
import br.com.mastermenu.persistencia.SegurancaSenhaDAO;
import br.com.mastermenu.util.HashUtil;

@ManagedBean
@SessionScoped
public class AuthenticationProfissionalBean {
	private Profissional profissionalLogado;
	private SegurancaSenha converte;

	public Profissional getProfissionalLogado() {
		if (profissionalLogado == null) {
			profissionalLogado = new Profissional();

		}
		return profissionalLogado;
	}

	public void setprofissionalLogado(Profissional profissionalLogado) {
		this.profissionalLogado = profissionalLogado;
	}

	public String autenticar() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.converte = new SegurancaSenha();
		
			ProfissionalDAO profDao = new ProfissionalDAO();
			Profissional prof = new Profissional();
			SegurancaSenhaDAO segurancaSenhaDAO = new SegurancaSenhaDAO();
			String senhaCompleta = null;
			prof = profDao.carregarPorEmail(this.profissionalLogado.getEmail());
			
			if (prof != null) {
				senhaCompleta = HashUtil.geraHash(this.profissionalLogado.getSenha(), prof.getSegurancaSenha().getSALT());
			} else {
				FacesMessage msg = new FacesMessage("Usuário inválido!");
				context.addMessage(null, msg);
				return null;
			}

			if (senhaCompleta == null || !senhaCompleta.equals(prof.getSenha())) {
				FacesMessage msg = new FacesMessage("Senha Inválida");
				context.addMessage(null, msg);
				return null;
			} else {
				FacesMessage msg = new FacesMessage("Logado!");
				context.addMessage(null, msg);
			}
			return "index";
	}

}
