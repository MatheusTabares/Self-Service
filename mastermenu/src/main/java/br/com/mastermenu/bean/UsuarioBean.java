package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.mastermenu.model.Usuario;
import br.com.mastermenu.persistencia.DAOGenerico;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean {
	private Usuario usuario = new Usuario();
	private List<Usuario> lista;
	
	//public String chamaListagem() {
	//	lista = new DAOGenerico<Usuario>(Usuario.class).listaTodos();
	//	return "listagem";
	//}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLista() {
		if(this.lista == null) {
			this.lista = new DAOGenerico<Usuario>(Usuario.class).listaTodos();
		}
		return lista;
	}
	
	public String excluir() {
		new DAOGenerico<Usuario>(Usuario.class).remove(this.usuario);
		this.lista = null;
		return null;
	}
}
