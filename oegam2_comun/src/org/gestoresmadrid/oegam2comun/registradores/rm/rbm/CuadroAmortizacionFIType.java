//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.02 at 11:42:58 AM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.rm.rbm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Cuadro_Amortizacion_FIType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Cuadro_Amortizacion_FIType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Plazo_FI" type="{}Plazo_FIType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cuadro_Amortizacion_FIType", propOrder = {
    "plazoFI"
})
public class CuadroAmortizacionFIType {

    @XmlElement(name = "Plazo_FI", required = true)
    protected List<PlazoFIType> plazoFI;

    /**
     * Gets the value of the plazoFI property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plazoFI property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlazoFI().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlazoFIType }
     * 
     * 
     */
    public List<PlazoFIType> getPlazoFI() {
        if (plazoFI == null) {
            plazoFI = new ArrayList<PlazoFIType>();
        }
        return this.plazoFI;
    }

}
