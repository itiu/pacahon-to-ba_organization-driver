
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for writeEntityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="writeEntityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="context" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="container" type="{http://organization.zdms_component.mndsc.ru/}EntityContainerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "writeEntityContainer", propOrder = {
    "context",
    "container"
})
public class WriteEntityContainer {

    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected String context;
    @XmlElement(namespace = "http://organization.zdms_component.mndsc.ru/")
    protected EntityContainerType container;

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

    /**
     * Gets the value of the container property.
     * 
     * @return
     *     possible object is
     *     {@link EntityContainerType }
     *     
     */
    public EntityContainerType getContainer() {
        return container;
    }

    /**
     * Sets the value of the container property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityContainerType }
     *     
     */
    public void setContainer(EntityContainerType value) {
        this.container = value;
    }

}
