package br.com.mastermenu.model;

import javax.persistence.Column;

public class Cliente extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3253914292390210978L;
	
	@Column(name = "email", length = 50, nullable = false)
	private String email;
}
