package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Copa;
import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.model.Pedido;
import br.com.mastermenu.util.HibernateUtil;

public class CozinhaDAO {
	
	private Session sessao;
	
	public CozinhaDAO() {
		sessao = HibernateUtil.getSessionFactory().openSession();
		
	}
	public void salvar(Cozinha cozinha) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
        	
        	sessao.save(cozinha);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar pedido da cozinha. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		if(sessao.isOpen())
        			sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar pedido da cozinha. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Cozinha carregarAbertaPorIdCliente(Long idCliente) {
		boolean aberta = true;
		//Session sessao = null;
		Cozinha cozinha = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			cozinha = (Cozinha) sessao.createQuery("FROM Cozinha WHERE id_cliente=:idCliente AND aberta=:aberta")
									.setLong("idCliente", idCliente)
									.setBoolean("aberta", aberta)
									.uniqueResult();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Pedidos Cozinha por cliente. Erro: " + ex.getMessage());
		} finally {
			try {
				// fecha a entity manager
			//	sessao.close();
			} catch (Throwable ex) {
				System.out.println("Erro ao fechar a operação de listar pedidos Cozinha por cliente. Mensagem:" + ex.getMessage());
			}
		}
		return cozinha;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cozinha> carregarAbertas() {
		boolean aberta = true;
		//Session sessao = null;
		List<Cozinha> cozinha = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			cozinha = sessao.createQuery("FROM Cozinha WHERE aberta=:aberta")
									.setBoolean("aberta", aberta)
									.list();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Pedidos Cozinha por cliente. Erro: " + ex.getMessage());
		}
		return cozinha;
	}
	
	public void atualizar(Cozinha cozinha) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(cozinha);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar pedido cozinha. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de atualizar pedido cozinha. Mensagem:" + ex.getMessage());
        	}
        }
	}
}

