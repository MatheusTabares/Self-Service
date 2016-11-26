package br.com.mastermenu.persistencia;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.Cliente;
import br.com.mastermenu.model.Profissional;
import br.com.mastermenu.util.HibernateUtil;

public class ProfissionalDAO {
	private final Session sessao;

    public ProfissionalDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }

	public void salvar(Profissional profissional) {
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
        	
        	sessao.save(profissional);
     
            transacao.commit();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível adicionar. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public void atualizar(Profissional profissional) {
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
        	
        	sessao.update(profissional);
     
            transacao.commit();
     	} catch (Exception ex) {
			System.out.println("Não foi possível atualizar. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Profissional carregarPorEmail(String email) {
		Profissional profissional = new Profissional();
		try {
			profissional = (Profissional) sessao.createQuery("FROM Usuario WHERE email=:email").setString("email", email).uniqueResult();
		} catch (HibernateException ex) {
			System.out.println("Não foi possível encontrar o profissional por email. Erro: " + ex.getMessage());
		} finally {
			try {
				// fecha a entity manager
				//sessao.close();
			} catch (Throwable ex) {
				System.out.println("Erro ao fechar a operação de carregar por email. Mensagem:" + ex.getMessage());
			}
		}
		return profissional;
	}
	
	//Authentication
	public Profissional authentication (String email, String senha){
		Profissional prof = null;
		try {
				Query consulta = sessao.getNamedQuery("profissional.authentication");
				consulta.setString("email", email);
				consulta.setString("senha", senha);
				prof = (Profissional) consulta.uniqueResult();
			
		} catch (Exception ex) {
			throw ex;
		}finally {
			//sessao.close();
		}
		
		return prof;
	}

	public void excluir(Profissional profissional) {
		Transaction transacao = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
        	
        	sessao.delete(profissional);
     
            transacao.commit();
		} catch (Exception ex) {
			System.out.println("Não foi possível remover. Erro: " + ex.getMessage());
		}  finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	public Profissional carregar(Long id) {
    	Profissional profissional = new Profissional();
    	try {
    		profissional =  (Profissional)sessao.get(Profissional.class, id);
        } catch (HibernateException ex) {
            System.out.println("Não foi possível carregar o profissional. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de carregar profissional. Mensagem:" + ex.getMessage());
        	}
        }
        return profissional;
    }
	
	public List<Profissional> listar() {
		Transaction transacao = null;
		Query consulta = null;
		List<Profissional> lista = null;
		try {
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
        	//	sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de adicionar. Mensagem:" + ex.getMessage());
        	}
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listarAtivos() {
    	boolean ativo = true;
		List<Profissional> listaAtivos = null;
    	try {
    		//sessao = HibernateUtil.getSessionFactory().openSession();
    		listaAtivos = sessao.createQuery("FROM Profissional WHERE ativo = :ativo").setBoolean("ativo", ativo).list();
        } catch (HibernateException ex) {
            System.out.println("Não foi possível listar profissionais ativos. Erro: " + ex.getMessage());
        } finally {
        	try {
        		// fecha a entity manager
        		//sessao.close();
        	} catch (Throwable ex) {
        		System.out.println("Erro ao fechar a operação de listar profissionais ativos. Mensagem:" + ex.getMessage());
        	}
        }
        return listaAtivos;
    }

}
