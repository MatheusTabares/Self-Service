package br.com.mastermenu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.mastermenu.util.HashUtil;

@Entity(name = "segurancaSenha")
public class SegurancaSenha {
	@Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario = new Usuario();

    private String SALT;

    public SegurancaSenha() {
        this.SALT = HashUtil.geraSALT();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSALT() {
        return SALT;
    }
}
