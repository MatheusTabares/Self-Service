package br.com.mastermenu.bean;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import br.com.mastermenu.model.Item;
import br.com.mastermenu.persistencia.ItemDAO;
import junit.framework.Assert;

public class ItemBeanTest {
	
	ItemDAO dao = new ItemDAO();

	@Test
	public void testPrepararPesquisa() {
		List<Item> itens = dao.listar();
		ItemDAO itemFalso = mock(ItemDAO.class);
		when(itemFalso.listar()).thenReturn(itens);		
	}

	@Test
	public void testPreparaNovo() {
		Item item = new Item();
		Item itemFalso = mock(Item.class);
		Assert.assertEquals(item, itemFalso);
	}

	@Test
	public void testGetMetodoLista() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscaDados() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncluir() {
		fail("Not yet implemented");
	}

	@Test
	public void testExclusaoLogica() {
		fail("Not yet implemented");
	}

	@Test
	public void testExclusaoFisica() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFotoDownload() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpload() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandleFileUpload() {
		fail("Not yet implemented");
	}

	@Test
	public void testMetodo() {
		fail("Not yet implemented");
	}

}
