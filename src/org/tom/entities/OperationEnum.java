package org.tom.entities;

public enum OperationEnum {
	
	INFORMATION_EXTRACTION("Unstructured Informations Extracted."),
	UNST_INFO_ERASE("All Unstructured Informations Erased."),
	STR_ITEM_ERASE("Structured Item Erased."),
	LINK_NOT_FINDED("Link not finded in Analisys."),
	LINK_FINDED("Link(s) finded in Analisys."),
	ITEM_DUPLICATED("Item is duplicated"),
	MANUAL_LINK_CREATION("Manual Link Created.");
	
	private String desc;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	OperationEnum(String operation){
		this.desc = operation;
	}

}
