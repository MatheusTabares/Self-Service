package br.com.mastermenu.persistencia;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Comanda;
import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.util.HibernateUtil;

public class ComandaDAO {
	public void salvar(Comanda comanda) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(comanda);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar comanda. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar comanda. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Comanda carregarPorIdCliente(Long idCliente) {
		Session sessao = null;
		Comanda comanda = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			comanda = (Comanda) sessao.createQuery("FROM Comanda WHERE id_cliente=:idCliente").setLong("idCliente", idCliente).uniqueResult();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Comanda por cliente. Erro: " + ex.getMessage());
		} finally {
			try {
				// fecha a entity manager
				sessao.close();
			} catch (Throwable ex) {
				System.out.println("Erro ao fechar a operação de listar comanda por cliente. Mensagem:" + ex.getMessage());
			}
		}
		return comanda;
	}
	public void atualizar(Comanda comanda) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(comanda);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar comanda. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de atualizar comanda. Mensagem:" + ex.getMessage());
        	}
        }
	}
}

