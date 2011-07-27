package org.tom.manager;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.History;
import org.tom.util.DateFormat;

public class HistoryManager extends JPABaseDAO<History>{

	private BaselineManager vm = new BaselineManager();
	
	public void historyPersist(String operation,String description, String user) throws ModeloException{
		History h = new History();
		h.setOperation(operation);
		h.setDescription(description);
		h.setDate(DateFormat.today());
		h.setTime(DateFormat.now());
		h.setUser(user);
		h.setIdBaseline(vm.getActiveBaseline().getIdBaseline());
		persist(h);
		JPAUtil.commitTransaction();
	}
	
	public List<History> getHistories(){
		List<History> histories  = new ArrayList<History>();
		try {
			histories = createQueryList("Select a from History a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return histories;
	}
	
	
	public History getLastHistory(){
		List<History> histories  = new ArrayList<History>();
		try {
			String sql = "SELECT h.* FROM history h order by h.date DESC, h.time DESC LIMIT 0,1";
			histories = createNativeQueryList(sql,History.class,null);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		if(histories.isEmpty())
			return null;
		else
			return histories.get(0);
	}
	
	public List<History> getHistoriesByVersion(int idBaseline){
		List<History> histories  = new ArrayList<History>();
		try {
			histories = createQueryList("Select a from History a where a.idBaseline=:idBaseline", new Object[]{"idBaseline",idBaseline});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return histories;
	}
	
}
