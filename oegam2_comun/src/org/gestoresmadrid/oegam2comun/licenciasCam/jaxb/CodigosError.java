//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:31 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for codigos_error complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="codigos_error">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://licencias/peticionGenerica}codigo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codigos_error", namespace = "http://licencias/peticionGenerica", propOrder = {
    "codigo"
})
public class CodigosError {

    protected List<Codigo> codigo;

    /**
     * Gets the value of the codigo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codigo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodigo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Codigo }
     * 
     * 
     */
    public List<Codigo> getCodigo() {
        if (codigo == null) {
            codigo = new ArrayList<Codigo>();
        }
        return this.codigo;
    }

}
