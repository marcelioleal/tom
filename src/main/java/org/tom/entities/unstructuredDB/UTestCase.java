package org.tom.entities.unstructuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "u_testCase")
@PrimaryKeyJoinColumn(name="identifier")
public class UTestCase extends UItem{
	
	@Column(name = "useCase")
	private String useCase;
	
	public String getUseCase() {
		return useCase;
	}

	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}

	@Override
	public String getType() {
		return UItemTypeEnum.TESTCASE.getType();
	}
	
	public void setType() {
		this.type = UItemTypeEnum.TESTCASE.getType();;
	}
}
