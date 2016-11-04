package br.com.mastermenu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Endereco;
import br.com.mastermenu.model.Item;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.persistencia.ClienteDAO;
import br.com.mastermenu.persistencia.SegurancaSenhaDAO;
import br.com.mastermenu.util.HashUtil;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {
	private String senha;
	private String novaSenha;
	private EnderecoBean enderecoBean = new EnderecoBean();
	private Endereco endereco = new Endereco();
	private Cliente cliente = new Cliente();
	private String confirmarSenha;
	private String destinoSalvar;
	private List<Cliente> lista = new ArrayList<Cliente>();
	private List<Cliente> listaAtivos = new ArrayList<Cliente>();
	private ClienteDAO clienteDAO = new ClienteDAO();
	private SegurancaSenha segurancaSenha = new SegurancaSenha();
	private SegurancaSenhaDAO segurancaSenhaDAO = new SegurancaSenhaDAO();
	private String msgCompleteSeusDados = "";
	private Item item = new Item();
	private List<Pedido> listaDePedidos = new ArrayList<Pedido>();
	private Double totalPedidos;
	private CozinhaBean cozinhaBean = new CozinhaBean();
	private Cliente clienteTemporario = new Cliente();
	private Pedido pedido = new Pedido();
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<Pedido> pedidosCopa = new ArrayList<Pedido>();
	private CopaBean copaBean = new CopaBean();
	private List<Pedido> pedidosCozinha = new ArrayList<Pedido>();
	private List<Pedido> comanda = new ArrayList<Pedido>();
	private Double total = 0.0;
	
	public ClienteBean() {
		this.destinoSalvar = "sucesso";
		this.cliente = new Cliente();
		this.cliente.setAtivo(true);
		this.cliente.setToken("token");
		this.cliente.setImagem("imagem");
	}

	public String novo() {
		this.destinoSalvar = "sucesso";
		this.cliente = new Cliente();
		this.cliente.setAtivo(true);
		this.cliente.setToken("token");
		this.cliente.setImagem("imagem");
		return "cadastrarCliente";
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (validacaoEmail()) {
			FacesMessage facesMessage = new FacesMessage("Email: " + this.cliente.getEmail() + ", já cadastrado.");
			context.addMessage(null, facesMessage);
			return null;
		}
		String senha = this.cliente.getSenha();
		if (!senha.trim().equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}

		this.clienteDAO = new ClienteDAO();
		this.cliente.setSenha(HashUtil.geraHash(this.cliente.getSenha(), this.segurancaSenha.getSALT()));
		this.cliente.setEndereco(this.endereco);
		this.clienteDAO.salvar(this.cliente);
		Cliente cliente = new Cliente();
		cliente = this.clienteDAO.carregarPorEmail(this.cliente.getEmail());
		this.segurancaSenha.setUsuario(cliente);
		this.segurancaSenhaDAO.salvar(this.segurancaSenha);
		FacesMessage facesMessage = new FacesMessage("Cliente " + this.cliente.getNome() + " cadastrado com sucesso!");
		context.addMessage(null, facesMessage);
		this.cliente = new Cliente();
		this.confirmarSenha = "";
		this.listar();
		return "homeCliente";
	}

	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.enderecoBean = new EnderecoBean();
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
		this.senha = "";
		this.cliente = new Cliente();
		this.endereco = new Endereco();
		return "listagem";
	}

	public boolean validacaoEmail() {
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
			ClienteDAO dao = new ClienteDAO();
			Cliente clienteAtual = dao.carregarPorEmail(cliente.getEmail());
			String senha = HashUtil.geraHash(this.cliente.getSenha(), clienteAtual.getSegurancaSenha().getSALT());
			if (clienteAtual.getSenha().equals(senha)) {
				clienteAtual.setSenha(HashUtil.geraHash(this.getNovaSenha(), this.segurancaSenha.getSALT()));
				segurancaSenhaDAO = new SegurancaSenhaDAO();
				segurancaSenha = segurancaSenhaDAO.carregar(clienteAtual.getSegurancaSenha().getId());
				segurancaSenha.setUsuario(cliente);
				segurancaSenhaDAO.atualizar(segurancaSenha);
				dao.atualizar(clienteAtual);
			}
		}

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
			this.clienteDAO = new ClienteDAO();
			this.clienteDAO.atualizar(this.cliente);
			FacesMessage facesMessage = new FacesMessage("Senha alterada com sucesso.");
			context.addMessage(null, facesMessage);
		}
		this.senha = "";
		this.novaSenha = "";
		this.confirmarSenha = "";

		return "visualizarProfissional";
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();
		clienteDAO = new ClienteDAO();
		clienteDAO.excluir(cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}

	public void excluirLogicamente() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.clienteDAO = new ClienteDAO();
		this.cliente.setAtivo(false);
		this.clienteDAO.atualizar(this.cliente);
		FacesMessage facesMessage = new FacesMessage("Cliente excluido com sucesso.");
		context.addMessage(null, facesMessage);
	}

	public void listar() {
		clienteDAO = new ClienteDAO();
		lista = clienteDAO.listar();
	}

	public String alterar() {
		this.confirmarSenha = "";
		this.endereco = new Endereco();
		if (this.cliente.getEndereco() != null) {
			this.endereco = this.enderecoBean.encontrarPorIdUsuario(this.cliente.getId());
			this.msgCompleteSeusDados = "";
		} else {
			this.msgCompleteSeusDados = "Complete seus Dados " + this.cliente.getNome() + " , por gentileza.";
			this.endereco.setUsuario(this.cliente);
		}
		return "alterarCliente";
	}
	
	public String incluirItemNaListaDePedidos() {
		FacesContext context = FacesContext.getCurrentInstance();
		//if(this.pedido.ge/////tCliente().getId().equals(null)) {
			this.clienteTemporario.setId(1L);
			this.pedido.setCliente(this.clienteTemporario);
		//}
		
		if(!incrementaPedido()){
			this.pedido.setItem(this.item);
			this.pedido.setSubTotal(this.item.getValor());
			this.pedidos.add(this.pedido);
			FacesMessage facesMessage = new FacesMessage(this.item.getNome() + " adicionado a lista de pedidos.");
			context.addMessage(null, facesMessage);
			this.item = new Item();
			this.pedido = new Pedido();
			return null;	
		}	
		FacesMessage facesMessage = new FacesMessage("Mais um(a) " + this.item.getNome() + " adicionado a lista de pedidos.");
		context.addMessage(null, facesMessage);
		this.item = new Item();
		this.pedido = new Pedido();
		return null;	
	}
		
	public boolean incrementaPedido() {
		for(int i = 0; i < this.pedidos.size(); i++) {
			if(this.pedidos.get(i).getItem().getIdItem()
					.equals(this.item.getIdItem())) {
				Integer quantidade = this.pedidos.get(i).getQuantidade();
				this.pedidos.get(i).setQuantidade(quantidade+1);
				this.pedidos.get(i).setSubTotal(
						(quantidade+1) * this.pedidos.get(i).getItem().getValor());
				return true;
			} 
		}
		return false;
	}
	
	public String excluirItemDaListaDePedidos() {
		FacesContext context = FacesContext.getCurrentInstance();
		for(int i = 0; i < this.getPedidos().size(); i++) {
			if(this.pedidos.get(i).getItem().equals(this.pedido.getItem())) {
				this.pedidos.remove(this.pedido);
				FacesMessage facesMessage = new FacesMessage(this.pedido.getItem().getNome() + " excluido da lista de pedidos.");
				context.addMessage(null, facesMessage);
			}
		}
		this.item = new Item();
		this.pedido = new Pedido();
		return null;
	}
	
	public String solicitarPedido() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		for(Pedido pedido : this.pedidos) {
			if(pedido.getItem().getTipo().getTipo() == 1) 
				this.pedidosCozinha.add(pedido);
			else if(pedido.getItem().getTipo().getTipo() == 2)
				this.pedidosCopa.add(pedido);
			else
				return null;
		}
		this.cozinhaBean.salvar();
		this.comanda.addAll(this.pedidos);
		gerarTotal();
		this.pedidos = new ArrayList<Pedido>();
		FacesMessage facesMessage = new FacesMessage("Seus Pedidos foram solicitados.");
		context.addMessage(null, facesMessage);
		return null;
	}
	
	public void gerarTotal() {
		this.total = 0.0;
		for(int i = 0; i < this.comanda.size(); i++) {
			this.total += this.comanda.get(i).getSubTotal(); 
		}
	}
	
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

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
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

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
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

	public SegurancaSenhaDAO getSegurancaSenhaDAO() {
		return segurancaSenhaDAO;
	}

	public void setSegurancaSenhaDAO(SegurancaSenhaDAO segurancaSenhaDAO) {
		this.segurancaSenhaDAO = segurancaSenhaDAO;
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

	public Double getTotalPedidos() {
		return totalPedidos;
	}

	public void setTotalPedidos(Double totalPedidos) {
		this.totalPedidos = totalPedidos;
	}

	public Cliente getClienteTemporario() {
		return clienteTemporario;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Pedido> getPedidosCopa() {
		return pedidosCopa;
	}

	public void setPedidosCopa(List<Pedido> pedidosCopa) {
		this.pedidosCopa = pedidosCopa;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public CopaBean getCopaBean() {
		return copaBean;
	}

	public void setCopaBean(CopaBean copaBean) {
		this.copaBean = copaBean;
	}

	public List<Pedido> getPedidosCozinha() {
		return pedidosCozinha;
	}

	public void setPedidosCozinha(List<Pedido> pedidosCozinha) {
		this.pedidosCozinha = pedidosCozinha;
	}

	public List<Pedido> getComanda() {
		return comanda;
	}

	public void setComanda(List<Pedido> comanda) {
		this.comanda = comanda;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
