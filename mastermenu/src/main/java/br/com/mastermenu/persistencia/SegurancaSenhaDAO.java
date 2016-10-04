package br.com.mastermenu.persistencia;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mastermenu.model.SegurancaSenha;
import br.com.mastermenu.util.HibernateUtil;

public class SegurancaSenhaDAO {
	private final Session sessao;

    public SegurancaSenhaDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }

    public void salvar(SegurancaSenha ss) {
        Transaction transacao = sessao.beginTransaction();
        sessao.save(ss);
        transacao.commit();
    }

    public SegurancaSenha carregar(Long id) {
        return (SegurancaSenha) sessao.get(SegurancaSenha.class, id);
    }

    @SuppressWarnings("unchecked")
	public ArrayList<SegurancaSenha> listarSegurancasSenhas() {
        return (ArrayList<SegurancaSenha>) sessao.createCriteria(SegurancaSenha.class).list();
    }

    public void excluir(Long id) {
        Transaction transacao = sessao.beginTransaction();
        sessao.delete(carregar(id));
        transacao.commit();
    }
}
