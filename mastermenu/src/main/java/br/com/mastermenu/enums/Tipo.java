package br.com.mastermenu.enums;


public enum Tipo {
	
	  
	  Comida(1),
	  Bebida(2),
	  Outro(3);

	   private int tipo;
	  
	  Tipo(int tipo){
	   this.tipo = tipo;
	  }

	   public int getTipo(){
	   return this.tipo;
	  }

}
