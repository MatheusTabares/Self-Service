package br.com.mastermenu.persistencia;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Item;
import br.com.mastermenu.util.HibernateUtil;

public class ItemDAO {
	

	    private Session sessao;
	   
	    
	    public void incluir(Item i) throws Exception {
	        sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	        try {
	            t = sessao.beginTransaction();
	            sessao.save(i);
	            t.commit();
	        }catch(Exception ex){
	            if(t == null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    public void alterar(Item i) throws Exception {
	        sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	        
	     try {
	            t = sessao.beginTransaction();
	            sessao.update(i);
	            t.commit();
	        }catch(Exception ex){
	            if(t != null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    
	    public void remover(Item i) {
	       sessao = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = null;
	        
	     try {
	            t = sessao.beginTransaction();
	            sessao.delete(i);
	            t.commit();
	        }catch(Exception ex){
	            if(t != null){
	                t.rollback();
	            }
	        } finally {
	            sessao.close();
	        }
	    }
	    
	    public List<Item> listar() {
			Session sessao = null;
			Transaction transacao = null;
			Query consulta = null;
			List<Item> lista = null;
			try {
				sessao = HibernateUtil.getSessionFactory().openSession();
				transacao = sessao.beginTransaction();
	        	consulta = sessao.createQuery("from Item where ativo = 1");
	        	lista = consulta.list();
	        	transacao.commit();
	        	return lista;
			} catch (Exception ex) {
				System.out.println("Não foi possível listar todos. Erro: " + ex.getMessage());
				throw new HibernateException(ex);
			}  finally {
	        	try {
	        		
	        	} catch (Throwable ex) {
	        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
	        	}
	        }
	    }
	    
	    public Item carregar(Long id) throws Exception {
	    	 
	    	sessao = HibernateUtil.getSessionFactory().openSession();
	    	try {
	    	Item i = (Item) sessao.get(Item.class, id);
	        return i;
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	} finally {
        		// fecha a entity manager
        		sessao.close();
	    
	    }
			return null;	       
	}
	    
    }
