package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Profissional;

public class ProfissionalDAO {
	public void salvar(Profissional profissional) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(profissional);
     
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
	
	public void atualizar(Profissional profissional) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(profissional);
     
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
	
	public void excluir(Profissional profissional) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.delete(profissional);
     
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
	
	public Profissional carregar(Long id) {
    	Profissional profissional = new Profissional();
    	Session sessao = null;
    	try {
    		sessao = HibernateUtil.getSessionFactory().openSession();
        	profissional =  (Profissional)sessao.get(Profissional.class, id);
        } catch (HibernateException ex) {
            System.out.println("Não foi possível carregar o profissional. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de carregar profissional. Mensagem:" + ex.getMessage());
        	}
        }
        return profissional;
    }
	
	public List<Profissional> listar() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Profissional> lista = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	consulta = sessao.createQuery("from Profissional");
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
