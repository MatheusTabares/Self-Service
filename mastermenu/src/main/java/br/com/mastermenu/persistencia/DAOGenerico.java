package br.com.mastermenu.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;

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
        } catch (HibernateException ex) {
            if(t != null){
                em.getTransaction().rollback();
            }
            System.out.println("N�o foi poss�vel adicionar. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
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
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel remover. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public void atualiza(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
	
			em.merge(t);
	
			em.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel atualizar. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}

	public List<T> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		List<T> lista = null;
		try {
			CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
			query.select(query.from(classe));
			lista = em.createQuery(query).getResultList();
			return lista;
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel listar todos. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}

	public T buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = null;
		try {
			instancia = em.find(classe, id);
			return instancia;
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel buscar por id. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}

	public int contaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = 0;
		try {
			result = (Long) em.createQuery("select count(n) from pratoPrincipal n")
					.getSingleResult();

			return (int) result;
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel contar todos. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		}  finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = new JPAUtil().getEntityManager();
		List<T> lista = null;
		try {
			CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
			query.select(query.from(classe));
	
			lista = em.createQuery(query).setFirstResult(firstResult)
					.setMaxResults(maxResults).getResultList();
			return lista;
		} catch (Exception ex) {
			System.out.println("N�o foi poss�vel listar todos paginada. Erro: " + ex.getMessage());
			throw new HibernateException(ex);
		} finally {
        	try {
        		// fecha a entity manager
        		em.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a opera��o de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
}
