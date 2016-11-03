package br.com.mastermenu.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

@Entity
@DiscriminatorValue(value = "C")
@Table(name = "cliente")
public class Cliente extends Usuario{

	private static final long serialVersionUID = 3253914292390210978L;
	
	@Length(max = 20)
	private String cupom;
	
	public String getCupom() {
		return cupom;
	}

	public void setCupom(String cupom) {
		this.cupom = cupom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cupom == null) ? 0 : cupom.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [cupom=" + cupom + "]";
	}
	
	
}
