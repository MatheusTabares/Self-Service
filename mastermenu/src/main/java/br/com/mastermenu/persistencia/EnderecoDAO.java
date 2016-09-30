package br.com.mastermenu.persistencia;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Endereco;

public class EnderecoDAO {

    private final Session sessao;

    public EnderecoDAO() {
        this.sessao = HibernateUtil.getSessionFactory().openSession();
    }

    public void salvar(Endereco e) {
        try {
        	Transaction t = sessao.beginTransaction();
        	sessao.save(e);
            t.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível salvar o endereço. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de salvar endereço. Mensagem:" + ex.getMessage());
        	}
        }
    }

    public Endereco carregar(Long id) {
    	Endereco endereco = new Endereco();
    	try {
        	endereco =  (Endereco)sessao.get(Endereco.class, id);
        } catch (HibernateException ex) {
            System.out.println("Não foi possível carregar o endereço. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de carregar endereço. Mensagem:" + ex.getMessage());
        	}
        }
        return endereco;
    }
        

    public void atualizar(Endereco e) {
        try {
        	Transaction t = sessao.beginTransaction();
            sessao.update(e);
            t.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível atualizar o endereço. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de atualizar endereço. Mensagem:" + ex.getMessage());
        	}
        }
    }

    public void excluir(Long id) {
        try {
        	Transaction t = sessao.beginTransaction();
        	sessao.delete(carregar(id));
            t.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível excluir o endereço. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de excluir endereço. Mensagem:" + ex.getMessage());
        	}
        }
        
    }

    public ArrayList<Endereco> listar() {
    	ArrayList<Endereco> lista = new ArrayList<Endereco>();
    	try {
        	lista = (ArrayList<Endereco>) sessao.createCriteria(Endereco.class).list();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível listar endereços. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de listar endereços. Mensagem:" + ex.getMessage());
        	}
        }
    	return lista;
    }

}