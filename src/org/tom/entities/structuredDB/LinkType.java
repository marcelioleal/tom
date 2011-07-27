/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tom.entities.structuredDB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mayara
 */
@Entity
@Table(name = "linkType")
public class LinkType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idTypeLink", nullable = false)
    private Integer idTypeLink;
    @Column(name = "description")
    private String description;

    public LinkType() {
    }

    public LinkType(Integer idTypeLink) {
        this.idTypeLink = idTypeLink;
    }

    public Integer getIdTypeLink() {
        return idTypeLink;
    }

    public void setIdTypeLink(Integer idTypeLink) {
        this.idTypeLink = idTypeLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeLink != null ? idTypeLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkType)) {
            return false;
        }
        LinkType other = (LinkType) object;
        if ((this.idTypeLink == null && other.idTypeLink != null) || (this.idTypeLink != null && !this.idTypeLink.equals(other.idTypeLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.TypeLink[idTypeLink=" + idTypeLink + "]";
    }

}
