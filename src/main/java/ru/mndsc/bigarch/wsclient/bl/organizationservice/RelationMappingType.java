
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationMappingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationMappingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="forwardDir" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationMappingType", propOrder = {
    "relationName",
    "forwardDir"
})
public class RelationMappingType {

    protected String relationName;
    protected boolean forwardDir;

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
     * Gets the value of the forwardDir property.
     * 
     */
    public boolean isForwardDir() {
        return forwardDir;
    }

    /**
     * Sets the value of the forwardDir property.
     * 
     */
    public void setForwardDir(boolean value) {
        this.forwardDir = value;
    }

}
