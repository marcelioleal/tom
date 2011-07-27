package org.tom.manager;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.ModeloException;
import org.tom.entities.Baseline;

public class BaselineManager extends JPABaseDAO<Baseline>{

	public Baseline getActiveBaseline(){
		Baseline version  = new Baseline();
		try {
			version = createQueryUnique("Select a from Baseline a WHERE a.status = 1");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return version;
	}
	
}
