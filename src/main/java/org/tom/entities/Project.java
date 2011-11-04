/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tom.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.tom.entities.structuredDB.Item;


/**
 *
 * @author Marcelio
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProject")
    private Integer idProject;
    
    @Column(name = "id")
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "obs")
    private String obs;
    
    
    

    public Project() {
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProject != null ? idProject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.idProject == null && other.idProject != null) || (this.idProject != null && !this.idProject.equals(other.idProject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Project[idProject=" + idProject + "]";
    }



	public Integer getIdProject() {
		return idProject;
	}



	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getStatusDescription(){
		if(ProjectStatusEnum.UNSTRUCTURED.getId().equals(this.getStatus()))
			return "UNSTRUCTURED";
		else if(ProjectStatusEnum.STRUCTURED.getId().equals(this.getStatus()))
			return "STRUCTURED";
		else
			return "";
	}
}
