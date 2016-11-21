package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Comanda;
import br.com.mastermenu.model.Copa;
import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.persistencia.ClienteDAO;
import br.com.mastermenu.persistencia.ComandaDAO;
import br.com.mastermenu.persistencia.CopaDAO;
import br.com.mastermenu.persistencia.CozinhaDAO;
import br.com.mastermenu.persistencia.SegurancaSenhaDAO;
import br.com.mastermenu.util.HashUtil;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {
	
	@ManagedProperty(value = "#{authenticationClienteBean}")
	private AuthenticationClienteBean authenticationClienteBean;
	private String senha;
	private String novaSenha;
	private EnderecoBean enderecoBean;
	private Endereco endereco;
	private Cliente cliente;
	private String confirmarSenha;
	private String destinoSalvar;
	private List<Cliente> lista;
	private List<Cliente> listaAtivos;
	private ClienteDAO clienteDAO;
	private SegurancaSenha segurancaSenha;
	private SegurancaSenhaDAO segurancaSenhaDAO;
	private String msgCompleteSeusDados;
	private Item item;
	private List<Pedido> listaDePedidos = new ArrayList<Pedido>();
	private List<Item> pedidos;
	private List<Comanda> comandasAbertas;
	private List<Copa> pedidosCopa;
	private List<Cozinha> pedidosCozinha;
	private Comanda comanda;
	private Cozinha cozinha;
	private ComandaDAO comandaDAO;
	private Copa copa;
	private CozinhaDAO cozinhaDAO;
	private CopaDAO copaDAO;
	
	public ClienteBean() {
		this.cliente = new Cliente();
		this.segurancaSenha = new SegurancaSenha();
		this.segurancaSenhaDAO = new SegurancaSenhaDAO();
		this.clienteDAO = new ClienteDAO();
		this.enderecoBean = new EnderecoBean();
		this.endereco = new Endereco();
		this.destinoSalvar = "sucesso";
		this.msgCompleteSeusDados = "";
		this.senha = "";
		this.novaSenha = "";
		this.lista = new ArrayList<Cliente>();
		this.listaAtivos = new ArrayList<Cliente>();
		this.item = new Item();
		this.pedidos = new ArrayList<Item>();
		this.comandasAbertas = new ArrayList<Comanda>();
		this.comanda = new Comanda();
		this.cozinha = new Cozinha();
		this.comandaDAO = new ComandaDAO();
		this.copa = new Copa();
		this.pedidosCopa = new ArrayList<Copa>();
		this.pedidosCozinha = new ArrayList<Cozinha>();
		this.cozinhaDAO = new CozinhaDAO();
		this.copaDAO = new CopaDAO();
	}

	public void novo() {
		this.destinoSalvar = "sucesso";
		this.cliente.setAtivo(true);
		this.cliente.setCpf("12345678910");
		this.cliente.setCupom("cupom");
		this.cliente.setTelefone("telefone");
		this.cliente.setToken("token");
		this.cliente.setImagem("imagem");
	}
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (validarEmail()) {
			FacesMessage facesMessage = new FacesMessage("Email: " + this.cliente.getEmail() + ", já cadastrado.");
			context.addMessage(null, facesMessage);
			this.cliente = new Cliente();
			return null;
		}
		String senha = this.cliente.getSenha();
		if (!senha.trim().equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			this.confirmarSenha = "";
			return null;
		}

		novo();
		this.cliente.setSenha(HashUtil.geraHash(this.cliente.getSenha(), this.segurancaSenha.getSALT()));
		this.cliente.setEndereco(this.endereco);
		this.clienteDAO.salvar(this.cliente);
		this.segurancaSenha.setUsuario(this.cliente);
		this.segurancaSenhaDAO.salvar(this.segurancaSenha);
		FacesMessage facesMessage = new FacesMessage("Cliente " + this.cliente.getNome() + " cadastrado com sucesso!");
		context.addMessage(null, facesMessage);
		this.senha = "";
		this.cliente = new Cliente();
		this.confirmarSenha = "";
		this.listar();
		return "loginCliente?faces-redirect=true";
	}

	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String senhaTela = HashUtil.geraHash(this.confirmarSenha, this.cliente.getSegurancaSenha().getSALT());
		String senhaCliente = this.cliente.getSenha();
		if (!senhaTela.trim().equals(senhaCliente)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}

		if (this.cliente.getEndereco() == null) {
			this.enderecoBean.salvar(this.endereco);
		} else {
			this.enderecoBean.atualizar(this.endereco);
		}
		this.clienteDAO.atualizar(this.cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente " + this.cliente.getNome() + " atualizado com sucesso.");
		context.addMessage(null, facesMessage);
		this.cliente = new Cliente();
		this.endereco = new Endereco();
		this.confirmarSenha = "";
		senhaTela = "";
		senhaCliente = "";

		return "listagem";
	}

	private boolean validarEmail() {
		this.listar();
		String email = this.cliente.getEmail();
		for (Cliente cliente : this.lista) {
			if (email.trim().equals(cliente.getEmail())) {
				return true;
			}
		}
		return false;
	}

	public String novaSenha() {
		if (novaSenha.trim().equals(confirmarSenha)) {
			Cliente clienteAtual = clienteDAO.carregarPorEmail(cliente.getEmail());
			String senha = HashUtil.geraHash(this.cliente.getSenha(), clienteAtual.getSegurancaSenha().getSALT());
			if (clienteAtual.getSenha().equals(senha)) {
				clienteAtual.setSenha(HashUtil.geraHash(this.getNovaSenha(), this.segurancaSenha.getSALT()));
				segurancaSenha = segurancaSenhaDAO.carregar(clienteAtual.getSegurancaSenha().getId());
				segurancaSenha.setUsuario(cliente);
				segurancaSenhaDAO.atualizar(segurancaSenha);
				clienteDAO.atualizar(clienteAtual);
			}
		}
		this.cliente = new Cliente();
		this.segurancaSenha = new SegurancaSenha();
		this.novaSenha = "";
		this.confirmarSenha = "";
		
		return novaSenha;
	}

	public String alterarSenha() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.senha.trim().equals("") || this.senha.trim().equals(null) || this.novaSenha.trim().equals("")
				|| this.novaSenha.trim().equals(null) || this.confirmarSenha.trim().equals("")
				|| this.confirmarSenha.trim().equals(null)) {
			FacesMessage facesMessage = new FacesMessage("Senha(s) inválida(s).");
			context.addMessage(null, facesMessage);
			return null;
		}

		if (this.novaSenha.trim().equals(this.senha.trim()) || this.confirmarSenha.trim().equals(this.senha.trim())) {
			FacesMessage facesMessage = new FacesMessage("Nova senha ou Confirma Senha igual a senha atual.");
			context.addMessage(null, facesMessage);
			return null;
		}

		if (!this.novaSenha.trim().equals(this.confirmarSenha.trim())) {
			FacesMessage facesMessage = new FacesMessage("Confirme a senha corretamente.");
			context.addMessage(null, facesMessage);
			return null;
		}

		String senha = HashUtil.geraHash(this.senha, this.cliente.getSegurancaSenha().getSALT());

		if (!senha.trim().equals(this.cliente.getSenha())) {
			FacesMessage facesMessage = new FacesMessage("Erro ao preencher a senha atual.");
			context.addMessage(null, facesMessage);
			return null;
		} else {
			String novaSenha = HashUtil.geraHash(this.novaSenha, this.cliente.getSegurancaSenha().getSALT());
			this.cliente.setSenha(novaSenha);
			this.clienteDAO.atualizar(this.cliente);
			FacesMessage facesMessage = new FacesMessage("Senha alterada com sucesso.");
			context.addMessage(null, facesMessage);
		}
		
		this.senha = "";
		this.novaSenha = "";
		this.confirmarSenha = "";
		this.cliente = new Cliente();
		
		return "visualizarProfissional";
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		clienteDAO.excluir(cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente excluido com sucesso.");
		context.addMessage(null, facesMessage);
		this.cliente = new Cliente();
	}

	public void excluirLogicamente() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.cliente.setAtivo(false);
		this.clienteDAO.atualizar(this.cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente excluido com sucesso.");
		context.addMessage(null, facesMessage);
		this.cliente = new Cliente();
	}

	public void listar() {
		lista = clienteDAO.listar();
	}

	public String alterar() {
		if (this.cliente.getEndereco() != null) {
			this.endereco = this.enderecoBean.encontrarPorIdUsuario(this.cliente.getId());
			this.msgCompleteSeusDados = "";
		} else {
			this.msgCompleteSeusDados = "Complete seus Dados " + this.cliente.getNome() + " , por gentileza.";
			this.endereco.setUsuario(this.cliente);
		}
		
		this.confirmarSenha = "";
		this.endereco = new Endereco();
		this.cliente = new Cliente();
		return "alterarCliente";
	}
	
	public void incluirItemNaListaDePedidos(Item item) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;
		this.pedidos.add(item);
		
		facesMessage = new FacesMessage(item.getNome() + " adicionado a lista de pedidos.");
		context.addMessage(null, facesMessage);
		item = new Item();
	}
	
	public String solicitarPedido() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(this.comanda.getCliente() == null && this.cozinha.getCliente() == null 
				&& this.copa.getCliente() == null) {
			this.comanda.setCliente(this.authenticationClienteBean.getClienteLogado());
			this.cozinha.setCliente(this.authenticationClienteBean.getClienteLogado());
			this.copa.setCliente(this.authenticationClienteBean.getClienteLogado());
		}
		
		 
		for(Item item : this.pedidos) {
				this.comanda.getPedidosSolicitados().add(item);
				//this.comandaDAO.salvar(this.comanda);
				
				if(item.getTipo().getTipo() == 1) {
					this.cozinha.getPedidosSolicitados().add(item);
				}	
				else if (item.getTipo().getTipo() == 2) { 
					this.copa.getPedidosSolicitados().add(item);
				}
			}
		
		if(this.pedidosCopa.isEmpty()) {
			this.pedidosCopa.add(this.copa);
		}
		
		somarTotalComanda();
		for(Comanda comanda : this.comandasAbertas) {
			if(comanda.getCliente().equals(this.comanda.getCliente())) {
				comanda = this.comanda;
				this.comandaDAO.atualizar(comanda);
			} else {
				this.comandasAbertas.add(this.comanda);
				this.comandaDAO.salvar(comanda);
			}
		}
		
		if(this.comandasAbertas.isEmpty()) {
			this.comandasAbertas.add(this.comanda);
			this.comandaDAO.salvar(comanda);
		}
		
		
		
		if(this.pedidosCozinha.isEmpty()) {
			this.pedidosCozinha.add(this.cozinha);
		} else {
			for(int i = 0; i < this.pedidosCozinha.size(); i++) {
				if(this.pedidosCozinha.get(i).getId().equals(this.cozinha.getId())) {
					this.pedidosCozinha.set(i, this.cozinha);
					//this.cozinhaDAO.salvar(this.cozinha);
				} else {
					this.pedidosCozinha.add(this.cozinha);
					//this.cozinhaDAO.atualizar(this.cozinha);
				}
			}
			for(int i = 0; i < this.pedidosCopa.size(); i++) {
				if(this.pedidosCopa.get(i).getId().equals(this.copa.getId())) {
					this.pedidosCopa.set(i, this.copa);
					//this.copaDAO.salvar(this.copa);
					
				} else {
					this.pedidosCopa.add(this.copa);
					//this.copaDAO.atualizar(this.copa);
				}
			}
		}
		FacesMessage facesMessage = new FacesMessage("Seus Pedidos foram solicitados.");
		context.addMessage(null, facesMessage);
		this.pedidos = new ArrayList<Item>();
		return "";
	}
	
	public String excluirItemDaListaDePedidos(Item item) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage();	
		if(this.pedidos.contains(item)) {
			this.pedidos.remove(item);
		}
		
		facesMessage = new FacesMessage("Retirado " + item.getNome() + " da lista de pedidos.");
		context.addMessage(null, facesMessage);
		item = new Item();
		return null;
	}
	
	public void somarTotalComanda() {
		Double somador = 0.0;
		for(Item item : this.comanda.getPedidosSolicitados()) {
			somador += item.getValor();
		}
		this.comanda.setTotal(somador);
	}
	
	public String encerrarComanda(Comanda comandaTela) {
		for(Comanda comanda : this.comandasAbertas) {
			if(comanda.equals(comandaTela)) {
				comanda.setAberta(false);
			}
		}
		return null;
	}

	/*public String excluirItemDaListaDePedidos() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage();	
		if(this.pedido.getListaItens().contains(this.item)) {
			if(this.item.getQuantidade() != 1) {
				Integer qtd = this.item.getQuantidade();
				this.item.setQuantidade(qtd - 1);
				this.pedido.getListaItens().remove(this.item);
				this.pedido.getListaItens().add(this.item);
				facesMessage = new FacesMessage("Retirado um(a) " + this.item.getNome() + " da lista de pedidos.");
			} else {
				this.pedido.getListaItens().remove(this.item);
				facesMessage = new FacesMessage(this.item.getNome() + " excluido da lista de pedidos.");
			}
		}
		context.addMessage(null, facesMessage);
		this.item = new Item();
		return null;
	}

	
	public void incluirItemNaListaDePedidos(Item item) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;
		this.pedido.setCliente(this.authenticationClienteBean.getClienteLogado());
		if(!incrementarPedido(item)) {
			this.pedido.getListaItens().add(item);
			facesMessage = new FacesMessage(item.getNome() + " adicionado a lista de pedidos.");
		} else {
			facesMessage = new FacesMessage(item.getQuantidade() +
					" unidades de " + item.getNome() + " em sua lista.");
		}
		context.addMessage(null, facesMessage);
		item = new Item();
	}
		
	public boolean incrementarPedido(Item item) {
		for(int i = 0; i < this.pedido.getListaItens().size(); i++) {
			if(this.pedido.getListaItens().get(i).getIdItem().equals(item.getIdItem())) {
				//Integer qtd = this.pedido.getListaItens().get(i).getQuantidade();
				this.pedido.getListaItens().get(i).setQuantidade(+1);
				return true;
			}
		}
		return false;
	}
	
	public String solicitarPedido() {
		FacesContext context = FacesContext.getCurrentInstance();
		solicitarCopa();
		solicitarCozinha();
		FacesMessage facesMessage = new FacesMessage("Seus Pedidos foram solicitados.");
		context.addMessage(null, facesMessage);
		this.pedido = new Pedido();
		this.item = new Item();
		return "";
	}
	
	public void solicitarCopa() {
		List<Item> pedidosCopa = new ArrayList<Item>();
		for(Item item : this.pedido.getListaItens()) {			
			if(item.getTipo().getTipo() == 2) {
				pedidosCopa.add(item);
			}
		}
		
		if(this.pedidosCopa.isEmpty()) {
			Pedido pedidoTemp = new Pedido();
			pedidoTemp.setCliente(this.authenticationClienteBean.getClienteLogado());
			pedidoTemp.setListaItens(pedidosCopa);
			this.pedidosCopa.add(pedidoTemp);
			this.acompanharPedidosCopa.addAll(pedidosCopa);
		} else {
			for(Pedido pedido : this.pedidosCopa) {			
				if(pedido.getCliente().equals(this.authenticationClienteBean.getClienteLogado())) {
					for(int i = 0; i < pedido.getListaItens().size(); i++) {
						for(int j = 0; j < pedidosCopa.size(); j++) {
							if(pedido.getListaItens().get(i).getIdItem().equals(pedidosCopa.get(j).getIdItem())) {
								Integer qtdSolicitar = pedidosCopa.get(j).getQuantidade();
								Integer qtdCopa = pedido.getListaItens().get(i).getQuantidade();
								pedido.getListaItens().get(i).setQuantidade(qtdSolicitar + qtdCopa);
								this.acompanharPedidosCopa.remove(i);
								this.acompanharPedidosCopa.add(pedido.getListaItens().get(i));
							}
						}
					}
				} else {
					Pedido pedidoTemp = new Pedido();
					pedidoTemp.setCliente(this.authenticationClienteBean.getClienteLogado());
					pedidoTemp.setListaItens(pedidosCopa);
					this.pedidosCopa.add(pedidoTemp);
					this.acompanharPedidosCopa.addAll(pedidosCopa);
				}
			}
		}
	}
	
	public void solicitarCozinha() {
		List<Item> pedidosCozinha = new ArrayList<Item>();
		for(Item item : this.pedido.getListaItens()) {			
			if(item.getTipo().getTipo() == 1) {
				pedidosCozinha.add(item);
			}
		}
		
		if(!pedidosCozinha.isEmpty()) {
			for(Pedido pedido : this.pedidosCozinha) {			
				if(pedido.getCliente().equals(this.authenticationClienteBean.getClienteLogado())) {
					for(int i = 0; i < pedido.getListaItens().size(); i++) {
						for(int j = 0; j < pedidosCozinha.size(); j++) {
							if(pedido.getListaItens().get(i).getIdItem().equals(pedidosCozinha.get(j).getIdItem())) {
								Integer qtdSolicitar = pedidosCozinha.get(j).getQuantidade();
								Integer qtdCozinha = pedido.getListaItens().get(i).getQuantidade();
								pedido.getListaItens().get(i).setQuantidade(qtdSolicitar + qtdCozinha);
								this.acompanharPedidosCozinha.remove(i);
								this.acompanharPedidosCozinha.add(pedido.getListaItens().get(i));
							}
						}
					}
				}
			}
			if(this.pedidosCopa.isEmpty() || !this.pedidosCopa.contains(this.authenticationClienteBean.getClienteLogado())) {
				Pedido pedidoTemp = new Pedido();
				pedidoTemp.setCliente(this.authenticationClienteBean.getClienteLogado());
				pedidoTemp.setListaItens(pedidosCozinha);
				this.pedidosCozinha.add(pedidoTemp);
				this.acompanharPedidosCozinha.addAll(pedidosCozinha);
			}
		}
	}
	*/
	public String visualizar() {
		return "visualizarCliente";
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public List<Cliente> getLista() {
		this.clienteDAO = new ClienteDAO();
		this.lista = this.clienteDAO.listar();
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Cliente> getListaAtivos() {
		this.clienteDAO = new ClienteDAO();
		this.listaAtivos = this.clienteDAO.listarAtivos();
		return listaAtivos;
	}

	public void setListaAtivos(List<Cliente> listaAtivos) {
		this.listaAtivos = listaAtivos;
	}

	public SegurancaSenha getSegurancaSenha() {
		return segurancaSenha;
	}

	public void setSegurancaSenha(SegurancaSenha segurancaSenha) {
		this.segurancaSenha = segurancaSenha;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getMsgCompleteSeusDados() {
		return msgCompleteSeusDados;
	}

	public void setMsgCompleteSeusDados(String msgCompleteSeusDados) {
		this.msgCompleteSeusDados = msgCompleteSeusDados;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Pedido> getListaDePedidos() {
		return listaDePedidos;
	}

	public void setListaDePedidos(List<Pedido> listaDePedidos) {
		this.listaDePedidos = listaDePedidos;
	}

	public AuthenticationClienteBean getAuthenticationClienteBean() {
		return authenticationClienteBean;
	}

	public void setAuthenticationClienteBean(AuthenticationClienteBean authenticationClienteBean) {
		this.authenticationClienteBean = authenticationClienteBean;
	}

	public List<Item> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Item> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Comanda> getComandasAbertas() {
		return comandasAbertas;
	}

	public void setComandasAbertas(List<Comanda> comandasAbertas) {
		this.comandasAbertas = comandasAbertas;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public Cozinha getCozinha() {
		return cozinha;
	}

	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	public Copa getCopa() {
		return copa;
	}

	public void setCopa(Copa copa) {
		this.copa = copa;
	}

	public List<Copa> getPedidosCopa() {
		return pedidosCopa;
	}

	public void setPedidosCopa(List<Copa> pedidosCopa) {
		this.pedidosCopa = pedidosCopa;
	}

	public List<Cozinha> getPedidosCozinha() {
		return pedidosCozinha;
	}

	public void setPedidosCozinha(List<Cozinha> pedidosCozinha) {
		this.pedidosCozinha = pedidosCozinha;
	}
	
	
	
	
	
}
