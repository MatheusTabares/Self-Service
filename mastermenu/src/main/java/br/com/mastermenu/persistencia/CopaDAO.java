package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Copa;
import br.com.mastermenu.model.Cozinha;
import br.com.mastermenu.util.HibernateUtil;

public class CopaDAO {
	
	private Session sessao;
	
	public CopaDAO() {
		sessao = HibernateUtil.getSessionFactory().openSession();
		
	}
	public void salvar(Copa copa) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
        	
        	sessao.save(copa);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar pedido da copa. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		//if(sessao.isOpen())
        			//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar pedido da copa. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Copa carregarAbertaPorIdCliente(Long idCliente) {
		boolean aberta = true;
		//Session sessao = null;
		Copa copa = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			copa = (Copa) sessao.createQuery("FROM Copa WHERE id_cliente=:idCliente AND aberta=:aberta")
									.setLong("idCliente", idCliente)
									.setBoolean("aberta", aberta)
									.uniqueResult();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Pedidos Copa por cliente. Erro: " + ex.getMessage());
		} finally {
			try {
				// fecha a entity manager
				//sessao.close();
			} catch (Throwable ex) {
				System.out.println("Erro ao fechar a operação de listar pedidos Copa por cliente. Mensagem:" + ex.getMessage());
			}
		}
		return copa;
	}
	
	@SuppressWarnings("unchecked")
	public List<Copa> carregarAbertas() {
		boolean aberta = true;
		//Session sessao = null;
		List<Copa> copa = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			copa = sessao.createQuery("FROM Copa WHERE aberta=:aberta")
									.setBoolean("aberta", aberta)
									.list();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível listar Pedidos Copa por cliente. Erro: " + ex.getMessage());
		}
		return copa;
	}
	
	public void atualizar(Copa copa) {
		//Session sessao = null;
		Transaction transacao = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(copa);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar pedido Copa. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de atualizar pedido copa. Mensagem:" + ex.getMessage());
        	}
        }
	}
}

