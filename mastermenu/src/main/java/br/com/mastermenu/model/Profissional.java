package br.com.mastermenu.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@DiscriminatorValue(value = "P")
@Table(name = "profissional")
public class Profissional extends Usuario {

	private static final long serialVersionUID = 5375414652966008113L;
	
	@NotNull(message = "PIS obrigatório.")
	@Length(min = 11, max = 11, message = "PIS contém 11 dígitos.")
	private String pis;
	
	@NotNull(message = "Salario não pode ser vazio.")
	private float salario;
	
	public String getPis() {
		return pis;
	}
	public void setPis(String pis) {
		this.pis = pis;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pis == null) ? 0 : pis.hashCode());
		result = prime * result + Float.floatToIntBits(salario);
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
		Profissional other = (Profissional) obj;
		if (pis == null) {
			if (other.pis != null)
				return false;
		} else if (!pis.equals(other.pis))
			return false;
		if (Float.floatToIntBits(salario) != Float.floatToIntBits(other.salario))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Profissional [pis=" + pis + ", salario=" + salario + "]";
	}
}
