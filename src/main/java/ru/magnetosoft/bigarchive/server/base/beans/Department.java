/*
 * Department.java
 *
 * Created on 1 ������ 2007 �., 14:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ru.magnetosoft.bigarchive.server.base.beans;

import java.io.Serializable;

/**
 * Подразделение.
 * 
 * @author YasinskyV, KodanevY
 */
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String previousId;
	private String name;
	private String nameEn;
	private boolean active;
	private boolean chosen;


	/**
	 * Конструктор.
	 */
	public Department() {
	}
	
	
	public String getId() {
		return id;
	} // end getId()

	public void setId(String id) {
		this.id = id;
	} // end setId()

	public String getPreviousId() {
		return previousId;
	} // end getPreviousId()

	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	} // end setPreviousId()

	public String getName() {
		return name;
	} // end getName()

	public void setName(String name) {
		this.name = name;
	} // end setName()

	public String getNameEn() {
		return nameEn;
	} // end getName_en()

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	} // end setName_en()

	public boolean getActive() {
		return active;
	} // end getActive()

	public void setActive(boolean active) {
		this.active = active;
	} // end setActive()

	public boolean isChosen() {
		return chosen;
	} // end isChosen()

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	} // end setChosen()
} // end Department

