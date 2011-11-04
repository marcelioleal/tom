package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_useCase")
@PrimaryKeyJoinColumn(name="identifier") 
public class UUseCase extends UItem{
	
	@Column(name = "flows")
	private String flows;

	@Column(name = "actors")
	private String actors;
	
	@Column(name = "features")
	private String features;

	@Column(name = "includesUC")
	private String includesUC;
	
	@Column(name = "extendsUC")
	private String extendsUC;
	
	@Column(name = "dependentsUC")
	private String dependentsUC;
	
	@Column(name = "businessRulesRel")
	private String businessRulesRel;

	public String getFlows() {
		return flows;
	}

	public void setFlows(String flows) {
		this.flows = flows;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}
	
	@Override
	public String getType() {
		return UItemTypeEnum.USECASE.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.USECASE.getType();
	}

	public String getIncludesUC() {
		return includesUC;
	}

	public void setIncludesUC(String includesUC) {
		this.includesUC = includesUC;
	}

	public String getExtendsUC() {
		return extendsUC;
	}

	public void setExtendsUC(String extendsUC) {
		this.extendsUC = extendsUC;
	}

	public String getDependentsUC() {
		return dependentsUC;
	}

	public void setDependentsUC(String dependentsUC) {
		this.dependentsUC = dependentsUC;
	}

	public String getBusinessRulesRel() {
		return businessRulesRel;
	}

	public void setBusinessRulesRel(String businessRulesRel) {
		this.businessRulesRel = businessRulesRel;
	}

}
