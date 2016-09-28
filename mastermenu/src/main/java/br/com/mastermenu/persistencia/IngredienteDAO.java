package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Ingrediente;

public class IngredienteDAO {
	
	 private Session sessao;
	   
	    
	    public void incluir(Ingrediente p) throws Exception {
	        sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	    
	        try {
	            t = sessao.beginTransaction();
	            sessao.save(p);
	            t.commit();
	        }catch(Exception ex){
	            if(t != null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    public void alterar(Ingrediente p) throws Exception {
	        sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	        
	     try {
	            t = sessao.beginTransaction();
	            sessao.update(p);
	            t.commit();
	        }catch(Exception ex){
	            if(t != null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    
	    public void remover(Ingrediente p) {
	       sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	        
	     try {
	            t = sessao.beginTransaction();
	            sessao.delete(p);
	            t.commit();
	        }catch(Exception ex){
	            if(t != null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    public List<Ingrediente> listar() {
			Session sessao = null;
			Transaction transacao = null;
			Query consulta = null;
			List<Ingrediente> lista = null;
			try {
				sessao = HibernateUtil.getSessionFactory().openSession();
				transacao = sessao.beginTransaction();
	        	consulta = sessao.createQuery("from Ingrediente where ativo = 1");
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
	    
	    public Ingrediente carregar(int id) throws Exception {
	        return (Ingrediente) sessao.get(Ingrediente.class, id);
	    }

}
