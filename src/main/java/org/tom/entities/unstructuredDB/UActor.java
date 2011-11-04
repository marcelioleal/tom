package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_actor")
@PrimaryKeyJoinColumn(name="identifier")
public class UActor extends UItem{
	
	@Column(name = "goals")
	private String goals;


	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}
	
	@Override
	public String getType() {
		return UItemTypeEnum.ACTOR.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.ACTOR.getType();;
	}
}
