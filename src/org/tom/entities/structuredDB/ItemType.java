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
@Table(name = "itemType")
public class ItemType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idTypeItem", nullable = false)
    private Integer idTypeItem;
    @Column(name = "description")
    private String description;
    @Column(name = "subType")
    private String subType;

    public ItemType() {
    }

    public ItemType(Integer idTypeItem) {
        this.idTypeItem = idTypeItem;
    }

    public Integer getIdTypeItem() {
        return idTypeItem;
    }

    public void setIdTypeItem(Integer idTypeItem) {
        this.idTypeItem = idTypeItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeItem != null ? idTypeItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemType)) {
            return false;
        }
        ItemType other = (ItemType) object;
        if ((this.idTypeItem == null && other.idTypeItem != null) || (this.idTypeItem != null && !this.idTypeItem.equals(other.idTypeItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.TypeItem[idTypeItem=" + idTypeItem + "]";
    }

}
