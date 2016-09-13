package br.com.mastermenu.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.mastermenu.model.Endereco;

@ManagedBean
@ViewScoped
public class EnderecoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Endereco endereco;
	private String cep;	
	
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void buscarEnderecoPorCep(){
		
		String URL = "http://www.devmedia.com.br/api/cep/service/?cep="+cep+"&chave=4WWD3JFH&formato=json";
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL);
		Response response = target.request().get();
		String json = response.readEntity(String.class);
		response.close();
		
		endereco = new Gson().fromJson(json, Endereco.class);
	}
	

}
