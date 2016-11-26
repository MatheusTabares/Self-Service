package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Comanda;
import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.util.HibernateUtil;

public class ComandaDAO {
	
	private Session sessao;
	
	public ComandaDAO() {
		sessao = HibernateUtil.getSessionFactory().openSession();
		
	}
	public void salvar(Comanda comanda) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
        	
        	sessao.save(comanda);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar comanda. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar comanda. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Comanda> carregarAbertas() {
		boolean aberta = true;
		//Session sessao = null;
		List<Comanda> comanda = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			comanda = (List<Comanda>) sessao.createQuery("FROM Comanda WHERE aberta=:aberta")
									.setBoolean("aberta", aberta)
									.list();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Comandas abertas. Erro: " + ex.getMessage());
		} finally {
			try {
				// fecha a entity manager
				//sessao.close();
			} catch (Throwable ex) {
				System.out.println("Erro ao fechar a operação de listar Comandas abertas. Mensagem:" + ex.getMessage());
			}
		}
		return comanda;
	}
	
	public Comanda carregarAbertaPorIdCliente(Long idCliente) {
		boolean aberta = true;
		//Session sessao = null;
		Comanda comanda = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			comanda = (Comanda) sessao.createQuery("FROM Comanda WHERE id_cliente=:idCliente AND aberta=:aberta")
											.setLong("idCliente", idCliente)
											.setBoolean("aberta", aberta)
											.uniqueResult();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Comanda por cliente. Erro: " + ex.getMessage());
		} 
		return comanda;
	}
	public void atualizar(Comanda comanda) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(comanda);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar comanda. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de atualizar comanda. Mensagem:" + ex.getMessage());
        	}
        }
	}
}

