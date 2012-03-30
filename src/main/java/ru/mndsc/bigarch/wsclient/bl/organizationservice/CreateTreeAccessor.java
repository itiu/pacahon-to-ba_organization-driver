
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createTreeAccessor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createTreeAccessor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rootUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationList" type="{http://organization.zdms_component.mndsc.ru/}RelationMappingType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createTreeAccessor", propOrder = {
    "name",
    "rootUid",
    "relationList"
})
public class CreateTreeAccessor {

    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected String name;
    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected String rootUid;
    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected List<RelationMappingType> relationList;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the rootUid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootUid() {
        return rootUid;
    }

    /**
     * Sets the value of the rootUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootUid(String value) {
        this.rootUid = value;
    }

    /**
     * Gets the value of the relationList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relationList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelationList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelationMappingType }
     * 
     * 
     */
    public List<RelationMappingType> getRelationList() {
        if (relationList == null) {
            relationList = new ArrayList<RelationMappingType>();
        }
        return this.relationList;
    }

}
