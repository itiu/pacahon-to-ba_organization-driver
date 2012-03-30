
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdjacencyTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdjacencyTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FORWARD"/>
 *     &lt;enumeration value="BACKWARD"/>
 *     &lt;enumeration value="ANY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdjacencyTypeEnum")
@XmlEnum
public enum AdjacencyTypeEnum {

    FORWARD,
    BACKWARD,
    ANY;

    public String value() {
        return name();
    }

    public static AdjacencyTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
