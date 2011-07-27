package org.tom.entities.structuredDB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idItem")
    private Integer idItem;
    
    @Column(name = "idTypeItem", nullable = false)
    private int idTypeItem;
    
    @Column(name = "id")
    private String idLoco;
    
    @Column(name = "name")
    private String name;
    
    @Lob
    @Column(name = "description")
    private String description;
    
    @Column(name = "version")
    private String version;
    
    @Column(name = "automatic")
    private boolean automatic;
    
    @Column(name = "idBaseline")
    private Integer idBaseline;
    
    @Column(name = "artifactName")
    private String artifactName;


	public Item() {
    }
	
    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public int getIdTypeItem() {
        return idTypeItem;
    }

    public void setIdTypeItem(int idTypeItem) {
        this.idTypeItem = idTypeItem;
    }

    public String getId() {
        return idLoco;
    }

    public void setId(String id) {
        this.idLoco = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Type: "+idTypeItem+" - id: " + idItem + " - Name: "+name+" - Auto: "+automatic+" - Baseline:"+idBaseline+" - Artifact: "+ artifactName;
    }

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	public Integer getIdBaseline() {
		return idBaseline;
	}

	public void setIdBaseline(Integer idBaseline) {
		this.idBaseline = idBaseline;
	}
	
    public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}



}
