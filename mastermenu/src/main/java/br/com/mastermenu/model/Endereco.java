package br.com.mastermenu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable{
	
	private static final long serialVersionUID = -5718287958022963461L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_endereco", updatable = false, nullable = false)
    private int id;

    @OneToOne
    private Usuario usuario;

    /*@Length(min = 8, max = 8, message = "CEP deve conter somente 8 dígitos.")
    @NotNull(message = "CEP Obrigatório.")*/
    private String CEP;
    
    /*@Length(min = 2, max = 2, message="UF deve conter somente 2 caracteres.")
    @NotNull(message = "UF obrigatório.")*/
    private String UF;


    /*@Length(min = 3, max = 40, message = "Cidade entre 6 e 40 caracteres.")
    @NotNull(message = "Cidade obrigatório.")*/
    private String cidade;

    /*@Length(min = 3, max = 40, message = "Bairro entre 3 e 40 caracteres.")
    @NotNull(message = "Bairro obrigatório.")*/
    private String bairro;
    
    /*@NotNull(message = "Logradouro obrigatório.")
    @Length(min = 6, max = 40, message = "Logradouro entre 6 e 40 caracteres.")*/
    private String logradouro;

    /*@NotNull(message = "Numero Obrigatório.")
    @Length(max = 20, message = "Máximo 20 dígitos.")*/
    private String numero;

    /*@Length(max = 20, message = "Complemento máximo 20 caracteres.")*/
    private String complemento = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento.toUpperCase();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
