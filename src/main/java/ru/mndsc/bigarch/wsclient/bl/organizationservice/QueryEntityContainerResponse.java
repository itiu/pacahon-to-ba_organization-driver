
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryEntityContainerResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryEntityContainerResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://organization.zdms_component.mndsc.ru/}EntityContainerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryEntityContainerResponse", propOrder = {
    "_return"
})
public class QueryEntityContainerResponse {

    @XmlElement(name = "return")
    protected EntityContainerType _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EntityContainerType }
     *     
     */
    public EntityContainerType getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityContainerType }
     *     
     */
    public void setReturn(EntityContainerType value) {
        this._return = value;
    }

}
