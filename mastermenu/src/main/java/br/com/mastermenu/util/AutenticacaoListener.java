package br.com.mastermenu.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.omnifaces.util.Faces;

import br.com.mastermenu.bean.AuthenticationClienteBean;
import br.com.mastermenu.model.Cliente;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();
		boolean ehPgautenticacao = paginaAtual.contains("loginCliente.xhtml");
		boolean ehIndex = paginaAtual.contains("index.xhtml");
		boolean ehAtendimento = paginaAtual.contains("atendimento.xhtml");
		boolean ehPgautenticacaoFunc = paginaAtual.contains("loginFuncionario.xhtml");
		boolean ehEscolherPagina = paginaAtual.contains("escolherLogin.xhtml");

		if (!ehPgautenticacao) {
				AuthenticationClienteBean autenticacaobean = Faces.getSessionAttribute("authenticationClienteBean");
				if (autenticacaobean == null) {
					try {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/mastermenu/loginCliente.xhtml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				Cliente cliente = autenticacaobean.getClienteLogado();
				
				if (ehIndex || ehAtendimento || ehPgautenticacaoFunc || ehEscolherPagina) {
					return;
				}
				
				if (cliente.getEmail() == null) {
					try {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/mastermenu/loginCliente.xhtml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
		}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {

		return PhaseId.ANY_PHASE;
	}

}
