
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityContainerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntityContainerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entityList" type="{http://organization.zdms_component.mndsc.ru/}EntityType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntityContainerType", propOrder = {
    "entities"
})
public class EntityContainerType {

    protected EntityContainerType.Entities entities;

    /**
     * Gets the value of the entities property.
     * 
     * @return
     *     possible object is
     *     {@link EntityContainerType.Entities }
     *     
     */
    public EntityContainerType.Entities getEntities() {
        return entities;
    }

    /**
     * Sets the value of the entities property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityContainerType.Entities }
     *     
     */
    public void setEntities(EntityContainerType.Entities value) {
        this.entities = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entityList" type="{http://organization.zdms_component.mndsc.ru/}EntityType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entityList"
    })
    public static class Entities {

        @XmlElement(nillable = true)
        protected List<EntityType> entityList;

        /**
         * Gets the value of the entityList property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entityList property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntityList().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EntityType }
         * 
         * 
         */
        public List<EntityType> getEntityList() {
            if (entityList == null) {
                entityList = new ArrayList<EntityType>();
            }
            return this.entityList;
        }

    }

}
