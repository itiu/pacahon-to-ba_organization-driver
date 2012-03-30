
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityActionEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityActionEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="INSERT"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="DELETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityActionEnumType")
@XmlEnum
public enum EntityActionEnumType {

    NONE,
    INSERT,
    UPDATE,
    DELETE;

    public String value() {
        return name();
    }

    public static EntityActionEnumType fromValue(String v) {
        return valueOf(v);
    }

}
