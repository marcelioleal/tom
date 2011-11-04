/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tom.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tom.entities.structuredDB.Item;

/**
 *
 * @author Marcelio
 */
@Entity
@Table(name = "baseline")
public class Baseline implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBaseline")
    private Integer idBaseline;
    
    @Column(name = "id")
    private String id;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "phase")
    private String phase;
    
    @Column(name = "dateTime")
    private Date dateTime;
    
    @Column(name = "status")
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name="idProject", nullable=false)
    private Project project;
    

    public Baseline() {
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBaseline != null ? idBaseline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Baseline other = (Baseline) object;
        if ((this.idBaseline == null && other.idBaseline != null) || (this.idBaseline != null && !this.idBaseline.equals(other.idBaseline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Version[idVersion=" + idBaseline + "]";
    }



	public Integer getIdBaseline() {
		return idBaseline;
	}



	public void setIdBaseline(Integer idBaseline) {
		this.idBaseline = idBaseline;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getPhase() {
		return phase;
	}



	public void setPhase(String phase) {
		this.phase = phase;
	}



	public Date getDateTime() {
		return dateTime;
	}



	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}



	public Project getProject() {
		return project;
	}



	public void setProject(Project project) {
		this.project = project;
	}



	public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}


}
