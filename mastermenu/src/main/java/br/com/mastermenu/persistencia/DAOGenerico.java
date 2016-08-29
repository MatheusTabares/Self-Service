package br.com.mastermenu.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAOGenerico<T> {

	private final Class<T> classe;

	public DAOGenerico(Class<T> classe) {
		this.classe = classe;
	}

	public void adiciona(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
        	// abre transacao
        	em.getTransaction().begin();
        	// persiste o objeto
            em.persist(t);
            // commita a transacao
    		em.getTransaction().commit();
    		
            
        }catch(Exception ex){
            if(t != null){
                em.getTransaction().rollback();
            }
        } finally {
        	// fecha a entity manager
    		em.close();
        }
	}
	
	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			
		//inicia transação
		em.getTransaction().begin();
		//remove o objeto
		em.remove(em.merge(t));
		//commita a transação
		em.getTransaction().commit();
		}catch(Exception c){
		//fecha entity manager
		em.close();
	}
	}
	
	public void atualiza(T t) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try{
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		}catch(Exception c){
			em.close();
		}
	}

	public List<T> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		List<T> lista = null;
		try{
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		lista = em.createQuery(query).getResultList();
		}catch(Exception c){
		em.close();
		}
		return lista;
	}

	public T buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = null;
		try{
		instancia = em.find(classe, id);
		}catch(Exception c){
		em.close();
		}
		return instancia;
	}

	public int contaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = 0;
		try{
		result = (Long) em.createQuery("select count(n) from pratoPrincipal n")
				.getSingleResult();
		}catch(Exception c){
		em.close();
		}
		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = new JPAUtil().getEntityManager();
		List<T> lista = null;
		try{
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
		}catch(Exception c){
		em.close();
		}
		return lista;
	}

}
