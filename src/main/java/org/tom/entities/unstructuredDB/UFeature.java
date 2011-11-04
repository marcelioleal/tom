package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_feature")
@PrimaryKeyJoinColumn(name="identifier")
public class UFeature extends UItem{
	
	@Column(name = "actors")
	private String actors;

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}
	
	@Override
	public String getType() {
		return UItemTypeEnum.FEATURE.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.FEATURE.getType();;
	}
}
