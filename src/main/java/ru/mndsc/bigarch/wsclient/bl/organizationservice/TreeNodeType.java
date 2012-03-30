
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreeNodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreeNodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="children" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="childList" type="{http://organization.zdms_component.mndsc.ru/}TreeNodeType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mark" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreeNodeType", propOrder = {
    "children"
})
public class TreeNodeType {

    protected TreeNodeType.Children children;
    @XmlAttribute
    protected String uid;
    @XmlAttribute(required = true)
    protected boolean mark;

    /**
     * Gets the value of the children property.
     * 
     * @return
     *     possible object is
     *     {@link TreeNodeType.Children }
     *     
     */
    public TreeNodeType.Children getChildren() {
        return children;
    }

    /**
     * Sets the value of the children property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreeNodeType.Children }
     *     
     */
    public void setChildren(TreeNodeType.Children value) {
        this.children = value;
    }

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
     * Gets the value of the mark property.
     * 
     */
    public boolean isMark() {
        return mark;
    }

    /**
     * Sets the value of the mark property.
     * 
     */
    public void setMark(boolean value) {
        this.mark = value;
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
     *         &lt;element name="childList" type="{http://organization.zdms_component.mndsc.ru/}TreeNodeType" maxOccurs="unbounded" minOccurs="0"/>
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
        "childList"
    })
    public static class Children {

        @XmlElement(nillable = true)
        protected List<TreeNodeType> childList;

        /**
         * Gets the value of the childList property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the childList property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getChildList().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TreeNodeType }
         * 
         * 
         */
        public List<TreeNodeType> getChildList() {
            if (childList == null) {
                childList = new ArrayList<TreeNodeType>();
            }
            return this.childList;
        }

    }

}
