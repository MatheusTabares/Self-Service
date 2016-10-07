package br.com.mastermenu.test;

import org.junit.Ignore;
import org.junit.Test;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Profissional;
import br.com.mastermenu.persistencia.ClienteDAO;
import br.com.mastermenu.persistencia.ProfissionalDAO;

public class LoginClienteTest {
	@Test
	@Ignore
	public void LoginCliente(){
		ClienteDAO cdao= new ClienteDAO();
		Cliente cliente = cdao.authentication("renantabares@hotmail.com", "7236a6a2a7baf7f4e74b893e990ce435f0639027051145491bd091d4128f1a3ac333d82e69098b0267a145095f83801550e831dbe9a25897e5d77404bfe91048");
		System.out.println(cliente.getNome()+"-"+cliente.getCpf());
		
	}
	@Test
	public void loginProfissional(){
		ProfissionalDAO daoprof = new ProfissionalDAO();
		Profissional prof = daoprof.authentication("veronica@hotmail.com", "185bb589e0c1c6462ee995e01b8ff8ca8ebcaaedda5b3c7eb5f70c24730399fc1243159cdef284a65f54b29185020cdfb2bdde369a6f5ae3545d79b936f946d2");
		System.out.println(prof.getNome()+""+prof.getCpf());
	}
}
