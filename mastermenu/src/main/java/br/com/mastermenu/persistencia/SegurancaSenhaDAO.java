package br.com.mastermenu.persistencia;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.util.HibernateUtil;

public class SegurancaSenhaDAO {
	private final Session sessao;

    public SegurancaSenhaDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }

    public void salvar(SegurancaSenha ss) {
    	Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.save(ss);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar segunraça da senha. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar segurança da senha. Mensagem:" + ex.getMessage());
        	}
        }
    }

    public SegurancaSenha carregar(Long id) {
        return (SegurancaSenha) sessao.get(SegurancaSenha.class, id);
    }

    @SuppressWarnings("unchecked")
	public ArrayList<SegurancaSenha> listarSegurancasSenhas() {
        return (ArrayList<SegurancaSenha>) sessao.createCriteria(SegurancaSenha.class).list();
    }
    
    public void atualizar(SegurancaSenha senha) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.update(senha);
     
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

    public void excluir(Long id) {
        Transaction transacao = sessao.beginTransaction();
        sessao.delete(carregar(id));
        transacao.commit();
    }
}
