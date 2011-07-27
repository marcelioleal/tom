/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tom.entities.structuredDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mayara
 */
@Entity
@Table(name = "link")
@NamedQueries({@NamedQuery(name = "Link.findByIdLink", query = "SELECT l FROM Link l WHERE l.idLink = :idLink"), @NamedQuery(name = "Link.findByIdTypeLink", query = "SELECT l FROM Link l WHERE l.idTypeLink = :idTypeLink"), @NamedQuery(name = "Link.findByIdItem", query = "SELECT l FROM Link l WHERE l.idItem = :idItem"), @NamedQuery(name = "Link.findByIdItemRel", query = "SELECT l FROM Link l WHERE l.idItemRel = :idItemRel"), @NamedQuery(name = "Link.findByObs", query = "SELECT l FROM Link l WHERE l.obs = :obs")})
public class Link implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLink")
    private Long idLink;
    
    @Column(name = "idTypeLink", nullable = false)
    private Integer idTypeLink;
    
    @Column(name = "idItem", nullable = false)
    private Integer idItem;
    
    @Column(name = "idItemRel", nullable = false)
    private Integer idItemRel;
    
    @Column(name = "automatic", nullable = false)
    private boolean automatic;
    
    @Column(name = "obs")
    private String obs;


	public Link() {
    }


    public Link(int idTypeLink, int idItem, int idItemRel,boolean automatic, String obs) {
        this.idTypeLink = idTypeLink;
        this.idItem = idItem;
        this.idItemRel = idItemRel;
        this.automatic = automatic;
        this.obs = obs;
    }

    public Long getIdLink() {
        return idLink;
    }

    public void setIdLink(Long idLink) {
        this.idLink = idLink;
    }

    public Integer getIdTypeLink() {
        return idTypeLink;
    }

    public void setIdTypeLink(Integer idTypeLink) {
        this.idTypeLink = idTypeLink;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdItemRel() {
        return idItemRel;
    }

    public void setIdItemRel(Integer idItemRel) {
        this.idItemRel = idItemRel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLink != null ? idLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Link)) {
            return false;
        }
        Link other = (Link) object;
        if ((this.idLink == null && other.idLink != null) || (this.idLink != null && !this.idLink.equals(other.idLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Link[idLink=" + idLink + "]";
    }

	public boolean getAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

}
