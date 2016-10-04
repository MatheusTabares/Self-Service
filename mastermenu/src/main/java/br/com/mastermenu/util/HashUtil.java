package br.com.mastermenu.util;

import java.util.UUID;

public class HashUtil {
	
	 public static String geraHash(String senha, String SALT) {
        String senhaCriptografada;
        senhaCriptografada = org.apache.commons.codec.digest.DigestUtils.sha512Hex(senha + SALT);
        return senhaCriptografada;
	 }
	
	 public static String geraSALT() {
	    // Gera um valor aleatório
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0, 20); // Retorno os 20 primeiros caracteres.	
	 }
	    
     public static String geraNovaSenha() {
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        return myRandom.substring(0, 6);
     }
	
}
