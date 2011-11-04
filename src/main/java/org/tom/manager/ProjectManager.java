package org.tom.manager;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.ModeloException;
import org.tom.entities.Project;

public class ProjectManager extends JPABaseDAO<Project>{

	public Project getProject(){
		Project project  = new Project();
		try {
			project = createQueryUnique("Select a from Project a WHERE a.id = 1");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return project;
	}
	
}
