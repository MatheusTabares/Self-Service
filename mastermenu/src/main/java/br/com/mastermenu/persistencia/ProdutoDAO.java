package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Produto;

public class ProdutoDAO {
	
	 private Session sessao;
	   
	    
	    public void incluir(Produto p) throws Exception {
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
	    
	    public void alterar(Produto p) throws Exception {
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
	    
	    
	    public void remover(Produto p) {
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
	    
	    @SuppressWarnings("Unchecked")
	    public List<Produto> listar(){
	    Session sessao = HibernateUtil.getSessionFactory().openSession();
	    List<Produto> itens = null;
	    
	        try {
	            Query consulta = sessao.getNamedQuery("Item.listar");
	            itens = consulta.list();
	        }catch (Exception ex) {
	           
	        } finally{
	            sessao.close();
	        }
	        return itens;
	    }
	    
	    public Produto carregar(int id) throws Exception {
	        return (Produto) sessao.get(Produto.class, id);
	    }

}
