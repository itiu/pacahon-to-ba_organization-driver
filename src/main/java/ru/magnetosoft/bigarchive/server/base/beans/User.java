/*
 * User.java
 *
 * Created on 23.10.2007, 12:00:26
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.magnetosoft.bigarchive.server.base.beans;

import java.io.Serializable;

/**
 * Пользователь.
 * 
 * @author KlyuchnikovE, KodanevY
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private boolean active = true;
    private String domainName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String post;
    private String departmentId;

    /**
     * Конструктор.
     * @param id идентификатор
     * @param domainName доменное имя
     * @param firstName имя
     * @param lastName фамилия
     * @param email электронная почта
     * @param post должность
     * @param departmentId идентификатор подразделения
     */
    public User(String id, String domainName, String firstName,
            String lastName, String email, String post, String departmentId, String middleName) {
        super();
        this.id = id;
        this.domainName = domainName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.post = post;
        this.departmentId = departmentId;
    }

    public String getRepresentatiom() {
        String name = "";
        if (lastName != null && firstName != null && lastName.length() > 0 &&
                firstName.length() > 0) {
            if (middleName != null && middleName.length() > 0) {
                name = lastName + " " + firstName.toUpperCase().charAt(0) + "." + middleName.toUpperCase().charAt(0) + ".";
            } else {
                name = lastName + " " + firstName.toUpperCase().charAt(0) + ".";
            }
        } else if (post != null && post.length() > 0) {
            name = post;
        } else {
            name = domainName;
        }
        return name;
    }

    /**
     * Конструктор.
     */
    public User() {
    }

    /**
     * Конструктор.
     * @param input прототип
     */
    protected User(User input) {
        copy(input);
    } // end User()

    /**
     * Создание атрибутов на основе прототипа.
     * @param input прототип
     */
    protected void copy(User input) {
        this.active = input.active;
        this.domainName = input.domainName;
        this.email = input.email;
        this.firstName = input.firstName;
        this.middleName = input.middleName;
        this.id = input.id;
        this.lastName = input.lastName;
        this.post = input.post;
        this.departmentId = input.departmentId;
    }

    public boolean getActive() {
        return active;
    } // end getActive()

    public void setActive(boolean active) {
        this.active = active;
    } // end setActive()

    public String getDomainName() {
        return domainName;
    } // end getDomainName()

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    } // end setDomainName()

    public String getEmail() {
        return email;
    } // end getEmail()

    public void setEmail(String email) {
        this.email = email;
    } // end setEmail()

    public String getFirstName() {
        return firstName;
    } // end getFirstName()

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } // end setFirstName()

    public String getId() {
        return id;
    } // end getId()

    public void setId(String id) {
        this.id = id;
    } // end setId()

    public String getLastName() {
        return lastName;
    } // end getLastName()

    public void setLastName(String lastName) {
        this.lastName = lastName;
    } // end setLastName()

    public String getPost() {
        return post;
    } // end getPost()

    public void setPost(String post) {
        this.post = post;
    } // end setPost()

    public String getDepartmentId() {
        return departmentId;
    } // end getDepartmentId()

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    } // end setDepartmentId()

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
} // end User

