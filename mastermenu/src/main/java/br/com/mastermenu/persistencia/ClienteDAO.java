package br.com.mastermenu.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Endereco;

public class ClienteDAO {
	public void salvar(Cliente cliente) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(cliente);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível adicionar. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public void atualizar(Cliente cliente) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(cliente);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public void excluir(Cliente cliente) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.delete(cliente);
     
            transacao.commit();
		} catch (Exception ex) {
			System.out.println("Não foi possível remover. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Cliente carregar(Long id) {
    	Cliente cliente = new Cliente();
    	Session sessao = null;
    	try {
    		sessao = HibernateUtil.getSessionFactory().openSession();
        	cliente =  (Cliente)sessao.get(Cliente.class, id);
        } catch (HibernateException ex) {
            System.out.println("Não foi possível carregar o cliente. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de carregar cliente. Mensagem:" + ex.getMessage());
        	}
        }
        return cliente;
    }
	
	public List<Cliente> listar() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Cliente> lista = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	consulta = sessao.createQuery("from Cliente");
        	lista = consulta.list();
        	transacao.commit();
        	return lista;
		} catch (Exception ex) {
			System.out.println("Não foi possível listar todos. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}

}
