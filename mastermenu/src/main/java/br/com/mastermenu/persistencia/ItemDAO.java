package br.com.mastermenu.persistencia;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.mastermenu.model.Item;
import org.hibernate.Query;

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
	            if(t != null){
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
	    
	    @SuppressWarnings("Unchecked")
	    public List<Item> listar(){
	    Session sessao = HibernateUtil.getSessionFactory().openSession();
	    List<Item> itens = null;
	    
	        try {
	            Query consulta = sessao.getNamedQuery("Item.listar");
	            itens = consulta.list();
	        }catch (Exception ex) {
	           
	        } finally{
	            sessao.close();
	        }
	        return itens;
	    }
	    
	    public Item carregar(int id) throws Exception {
	        return (Item) sessao.get(Item.class, id);
	    }

	    
	}
