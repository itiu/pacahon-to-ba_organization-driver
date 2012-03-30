
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAdjacency complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAdjacency">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adjType" type="{http://organization.zdms_component.mndsc.ru/}AdjacencyTypeEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdjacency", propOrder = {
    "uid",
    "relationName",
    "adjType"
})
public class GetAdjacency {

    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected String uid;
    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected String relationName;
    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected AdjacencyTypeEnum adjType;

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the relationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationName(String value) {
        this.relationName = value;
    }

    /**
     * Gets the value of the adjType property.
     * 
     * @return
     *     possible object is
     *     {@link AdjacencyTypeEnum }
     *     
     */
    public AdjacencyTypeEnum getAdjType() {
        return adjType;
    }

    /**
     * Sets the value of the adjType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdjacencyTypeEnum }
     *     
     */
    public void setAdjType(AdjacencyTypeEnum value) {
        this.adjType = value;
    }

}
