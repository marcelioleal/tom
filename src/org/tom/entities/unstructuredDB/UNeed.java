package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_need")
@PrimaryKeyJoinColumn(name="identifier")
public class UNeed extends UItem{
	
	@Column(name = "features")
	private String features;
	
	@Column(name = "benefit")
	private String benefit;

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}
	
	@Override
	public String getType() {
		return UItemTypeEnum.NEED.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.NEED.getType();
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

}
