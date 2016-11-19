package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.util.HibernateUtil;

public class PedidosDAO {
	public void salvarPedidosCozinha(Pedido pedido) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(pedido);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar pedido na cozinha. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar pedido na cozinha. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> listar() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Pedido> lista = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	consulta = sessao.createQuery("from Pedido");
        	lista = consulta.list();
        	transacao.commit();
        	return lista;
		} catch (Exception ex) {
			System.out.println("Não foi possível listar todos os pedidos. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de lista pedidos. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> listarAbertasPorIdCliente(Long idCliente) {
    	boolean aberta = true;
    	Long id = idCliente;
		Session sessao = null;
    	List<Pedido> listaAbertasPorIdCliente = null;
    	try {
    		sessao = HibernateUtil.getSessionFactory().openSession();
    		listaAbertasPorIdCliente = sessao.createQuery("FROM Pedido WHERE aberta = :aberta"
    				+ " AND cliente_id = :id").setBoolean("aberta", aberta).setLong("id", id).list();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível listar comandas em aberto pelo id do cliente. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de listar comandas em aberto pelo id do cliente. Mensagem:" + ex.getMessage());
        	}
        }
        return listaAbertasPorIdCliente;
    }
	
	/*@SuppressWarnings("unchecked")
	public List<Pedido> listarAbertas() {
    	boolean aberta = true;
		Session sessao = null;
    	List<Cliente> listaAtivos = null;
    	try {
    		sessao = HibernateUtil.getSessionFactory().openSession();
    		listaAtivos = sessao.createQuery("FROM Cliente WHERE ativo = :ativo").setBoolean("ativo", ativo).list();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível listar clientes ativos. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de listar clientes ativos. Mensagem:" + ex.getMessage());
        	}
        }
        return listaAtivos;
    }*/
}
