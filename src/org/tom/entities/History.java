/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tom.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Marcelio Leal
 */
@Entity
@Table(name = "history")
public class History implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistory")
    private Integer idHistory;
    
    @Column(name = "operation", nullable = false)
    private String operation;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "date", nullable = false)
    private String date;
    
    @Column(name = "time", nullable = false)
    private String time;
    
    @Column(name = "idBaseline")
    private Integer idBaseline;
    
    @Column(name = "user")
    private String user;

	public Integer getIdHistory() {
		return idHistory;
	}

	public void setIdHistory(Integer idHistory) {
		this.idHistory = idHistory;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getIdBaseline() {
		return idBaseline;
	}

	public void setIdBaseline(Integer idBaseline) {
		this.idBaseline = idBaseline;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
    
    

}
