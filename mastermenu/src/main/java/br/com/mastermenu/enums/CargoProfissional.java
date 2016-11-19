package br.com.mastermenu.enums;

public enum CargoProfissional {
	GARCOM("Garcom"),
	CAIXA("Caixa"),
	COZINHEIRO("Cozinheiro");
	
	private String cargo;
	
	CargoProfissional(String cargo) {
		this.cargo = cargo;
	}

	public String getCargo() {
		return cargo;
	}
	
}
