package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_code")
@PrimaryKeyJoinColumn(name="identifier")
public class UCode extends UItem{
	
	@Column(name = "useCases")
	private String useCases;
	
	@Column(name = "businessRules")
	private String businessRules;
	
	@Column(name = "testCases")
	private String testCases;
	

	public String getUseCases() {
		return useCases;
	}

	public void setUseCases(String useCases) {
		this.useCases = useCases;
	}

	public String getBusinessRules() {
		return businessRules;
	}

	public void setBusinessRules(String businessRules) {
		this.businessRules = businessRules;
	}

	public String getTestCases() {
		return testCases;
	}

	public void setTestCases(String testCases) {
		this.testCases = testCases;
	}

	@Override
	public String getType() {
		return UItemTypeEnum.CODE.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.CODE.getType();;
	}
}
