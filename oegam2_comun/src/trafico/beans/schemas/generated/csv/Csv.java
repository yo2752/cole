//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.01 at 10:25:43 AM CEST 
//


package trafico.beans.schemas.generated.csv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;choice>
 *           &lt;element name="matriculaTelematica" type="{http://www.gaa9.com/csv}matriculaTelematica"/>
 *           &lt;element name="matriculaPDF" type="{http://www.gaa9.com/csv}matriculaPDF"/>
 *           &lt;element name="transferenciaTelematica" type="{http://www.gaa9.com/csv}transferenciaTelematica"/>
 *           &lt;element name="transferenciaPDF" type="{http://www.gaa9.com/csv}transferenciaPDF"/>
 *         &lt;/choice>
 *         &lt;element name="firmas" type="{http://www.gaa9.com/csv}firmas"/>
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
    "matriculaTelematica",
    "matriculaPDF",
    "transferenciaTelematica",
    "transferenciaPDF",
    "firmas"
})
@XmlRootElement(name = "csv")
public class Csv {

    protected MatriculaTelematica matriculaTelematica;
    protected MatriculaPDF matriculaPDF;
    protected TransferenciaTelematica transferenciaTelematica;
    protected TransferenciaPDF transferenciaPDF;
    @XmlElement(required = true)
    protected Firmas firmas;

    /**
     * Gets the value of the matriculaTelematica property.
     * 
     * @return
     *     possible object is
     *     {@link MatriculaTelematica }
     *     
     */
    public MatriculaTelematica getMatriculaTelematica() {
        return matriculaTelematica;
    }

    /**
     * Sets the value of the matriculaTelematica property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatriculaTelematica }
     *     
     */
    public void setMatriculaTelematica(MatriculaTelematica value) {
        this.matriculaTelematica = value;
    }

    /**
     * Gets the value of the matriculaPDF property.
     * 
     * @return
     *     possible object is
     *     {@link MatriculaPDF }
     *     
     */
    public MatriculaPDF getMatriculaPDF() {
        return matriculaPDF;
    }

    /**
     * Sets the value of the matriculaPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatriculaPDF }
     *     
     */
    public void setMatriculaPDF(MatriculaPDF value) {
        this.matriculaPDF = value;
    }

    /**
     * Gets the value of the transferenciaTelematica property.
     * 
     * @return
     *     possible object is
     *     {@link TransferenciaTelematica }
     *     
     */
    public TransferenciaTelematica getTransferenciaTelematica() {
        return transferenciaTelematica;
    }

    /**
     * Sets the value of the transferenciaTelematica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferenciaTelematica }
     *     
     */
    public void setTransferenciaTelematica(TransferenciaTelematica value) {
        this.transferenciaTelematica = value;
    }

    /**
     * Gets the value of the transferenciaPDF property.
     * 
     * @return
     *     possible object is
     *     {@link TransferenciaPDF }
     *     
     */
    public TransferenciaPDF getTransferenciaPDF() {
        return transferenciaPDF;
    }

    /**
     * Sets the value of the transferenciaPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferenciaPDF }
     *     
     */
    public void setTransferenciaPDF(TransferenciaPDF value) {
        this.transferenciaPDF = value;
    }

    /**
     * Gets the value of the firmas property.
     * 
     * @return
     *     possible object is
     *     {@link Firmas }
     *     
     */
    public Firmas getFirmas() {
        return firmas;
    }

    /**
     * Sets the value of the firmas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Firmas }
     *     
     */
    public void setFirmas(Firmas value) {
        this.firmas = value;
    }

}
