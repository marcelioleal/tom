package org.tom.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.tom.util.DateFormat;

@Entity
@Table(name = "artifact")
public class Artifact {
	
	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "dateTime")
	private String dateTime;

	public Artifact(){
	}
	
	public Artifact(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime() {
		this.dateTime = DateFormat.todayNow();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
