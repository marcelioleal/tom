package org.tom.entities.structuredDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "collaboration")
public class Collaboration {

	 @Id
	 @Column(name = "login")
	 private String login;
	 
	 @Column(name = "idItem", nullable = false)
	 private Integer idItem;
	 
	 @Column(name = "dateTime")
	 private String dataHora;
	 
	 @Column(name = "version")
	 private String version;

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String loginCVS) {
		this.login = loginCVS;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
