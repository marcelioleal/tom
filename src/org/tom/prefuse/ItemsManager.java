package org.tom.prefuse;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.ModeloException;
import org.tom.entities.structuredDB.Item;

public class ItemsManager extends JPABaseDAO<Item>{
	
	public List<Item> getItems(){
		List<Item> items  = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a", null);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}

}
