package org.tom.manager.structured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.ModeloException;
import org.tom.entities.structuredDB.Collaboration;

public class CollaborationManager extends JPABaseDAO<Collaboration>{

	public List<Collaboration> getCollaborations(){
		List<Collaboration> authors  = new ArrayList<Collaboration>();
		try {
			authors = createQueryList("Select a from Collaboration a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return authors;
	}
	
	public List<Collaboration> getCollaborationByArtifact(int artifactId){
		List<Collaboration> authors  = new ArrayList<Collaboration>();
		try {
			authors = createQueryList("Select a from Collaboration a where a.idItem=:id", new Object[]{"id",artifactId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return authors;
	}
	

	public List<Collaboration> getCollaborationByItem(int idItem){
		List<Collaboration> authors  = new ArrayList<Collaboration>();
		try {
			authors = createNativeQueryList("Select * from collaboration a " +
										"JOIN item i ON (a.idItem = i.idItem) " +
										"JOIN link l ON (l.idItemRel = i.idItem) " +
										"JOIN typeItem tI ON (i.idTypeItem = tI.idTypeItem AND tI.subType = 'A')" +
										"WHERE l.idItem ='" + idItem + "'", Collaboration.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return authors;
	}
	
	
	
	

}
