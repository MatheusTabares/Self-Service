package br.com.mastermenu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3253914292390210978L;
	
	@Column(name = "cupom", length = 100)
	private String cupom;
	
	@Column(name = "token", length = 100)
	private String token;

	public String getCupom() {
		return cupom;
	}

	public void setCupom(String cupom) {
		this.cupom = cupom;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cupom == null) ? 0 : cupom.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cupom == null) {
			if (other.cupom != null)
				return false;
		} else if (!cupom.equals(other.cupom))
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
		return "Cliente [cupom=" + cupom + ", token=" + token + "]";
	}
	
	
}
