package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.util.HibernateUtil;

public class CozinhaDAO {
	public void salvar(Pedido pedido) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(pedido);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar pedido da cozinha. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar pedido da cozinha. Mensagem:" + ex.getMessage());
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
			System.out.println("Não foi possível listar todos pedidos. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de listar Pedidos. Mensagem:" + ex.getMessage());
        	}
        }
	}
}
