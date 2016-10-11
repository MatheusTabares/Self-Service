package br.com.mastermenu.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("U")
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 6510223654635279605L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario", updatable = false, nullable = false)
	private Long id;
	
	@Length(max = 150, message = "Máximo 150 caracteres.")
	@NotNull(message = "Nome obrigatório.")
	private String nome;
	
	@NotNull(message = "E-mail Obrigatório.")
	@Email(message= "E-mail inválido.")
	private String email;
	
	@NotNull(message = "Telefone obrigatório.")
	@Length(min = 8, max = 15, message = "Telefone entre 8 e 15 numeros.")
	private String telefone;
	
	@NotNull(message = "CPF Obrigatório.")
	@Length(min = 11, max = 11, message="CPF contém 11 dígitos.")
	private String cpf;
	
	
	@NotNull(message = "Senha Obrigatória.")
	@Length(min = 1, max = 200, message = "Máximo 200 dígitos.")
	private String senha;
	
	@OneToOne(mappedBy = "usuario")
    private Endereco endereco;
	
	@Length(max = 20)
	private String imagem;
	
	@NotNull
	private boolean ativo;
	
	private String token;
	
	@OneToOne(mappedBy = "usuario")
    private SegurancaSenha segurancaSenha;
	
	@Column(insertable=false, updatable=false)
    private String tipo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public SegurancaSenha getSegurancaSenha() {
		return segurancaSenha;
	}
	
	public void setSegurancaSenha(SegurancaSenha segurancaSenha) {
		this.segurancaSenha = segurancaSenha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagem == null) ? 0 : imagem.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((segurancaSenha == null) ? 0 : segurancaSenha.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagem == null) {
			if (other.imagem != null)
				return false;
		} else if (!imagem.equals(other.imagem))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (segurancaSenha == null) {
			if (other.segurancaSenha != null)
				return false;
		} else if (!segurancaSenha.equals(other.segurancaSenha))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", senha=" + senha + ", endereco=" + endereco + ", imagem="
				+ imagem + ", telefone=" + telefone + ", ativo=" + ativo + ", email=" + email + ", cpf=" + cpf
				+ ", token=" + token + ", segurancaSenha=" + segurancaSenha + "]";
	}

		
}
