//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.27 at 10:57:32 AM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Plazo_LeasingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Plazo_LeasingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fecha_Vencimiento" type="{}FechaType"/>
 *         &lt;element name="Importe_Recuperacion_Coste" type="{}ImporteType"/>
 *         &lt;element name="Importe_Recuperacion_Coste_Pendiente" type="{}ImporteType" minOccurs="0"/>
 *         &lt;element name="Importe_Carga_Financiera" type="{}ImporteType"/>
 *         &lt;element name="Importe_Cuota_Neta" type="{}ImporteType"/>
 *         &lt;element name="Importe_Impuesto" type="{}ImporteType"/>
 *         &lt;element name="Importe_Total_Cuota" type="{}ImporteType"/>
 *         &lt;element name="Importe_Distribucion_Entrega_Cuenta" type="{}ImporteType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Plazo_LeasingType", propOrder = {
    "fechaVencimiento",
    "importeRecuperacionCoste",
    "importeRecuperacionCostePendiente",
    "importeCargaFinanciera",
    "importeCuotaNeta",
    "importeImpuesto",
    "importeTotalCuota",
    "importeDistribucionEntregaCuenta"
})
public class PlazoLeasingType {

    @XmlElement(name = "Fecha_Vencimiento", required = true)
    protected String fechaVencimiento;
    @XmlElement(name = "Importe_Recuperacion_Coste", required = true)
    protected String importeRecuperacionCoste;
    @XmlElement(name = "Importe_Recuperacion_Coste_Pendiente")
    protected String importeRecuperacionCostePendiente;
    @XmlElement(name = "Importe_Carga_Financiera", required = true)
    protected String importeCargaFinanciera;
    @XmlElement(name = "Importe_Cuota_Neta", required = true)
    protected String importeCuotaNeta;
    @XmlElement(name = "Importe_Impuesto", required = true)
    protected String importeImpuesto;
    @XmlElement(name = "Importe_Total_Cuota", required = true)
    protected String importeTotalCuota;
    @XmlElement(name = "Importe_Distribucion_Entrega_Cuenta")
    protected String importeDistribucionEntregaCuenta;

    /**
     * Gets the value of the fechaVencimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Sets the value of the fechaVencimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaVencimiento(String value) {
        this.fechaVencimiento = value;
    }

    /**
     * Gets the value of the importeRecuperacionCoste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteRecuperacionCoste() {
        return importeRecuperacionCoste;
    }

    /**
     * Sets the value of the importeRecuperacionCoste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteRecuperacionCoste(String value) {
        this.importeRecuperacionCoste = value;
    }

    /**
     * Gets the value of the importeRecuperacionCostePendiente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteRecuperacionCostePendiente() {
        return importeRecuperacionCostePendiente;
    }

    /**
     * Sets the value of the importeRecuperacionCostePendiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteRecuperacionCostePendiente(String value) {
        this.importeRecuperacionCostePendiente = value;
    }

    /**
     * Gets the value of the importeCargaFinanciera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteCargaFinanciera() {
        return importeCargaFinanciera;
    }

    /**
     * Sets the value of the importeCargaFinanciera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteCargaFinanciera(String value) {
        this.importeCargaFinanciera = value;
    }

    /**
     * Gets the value of the importeCuotaNeta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteCuotaNeta() {
        return importeCuotaNeta;
    }

    /**
     * Sets the value of the importeCuotaNeta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteCuotaNeta(String value) {
        this.importeCuotaNeta = value;
    }

    /**
     * Gets the value of the importeImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteImpuesto() {
        return importeImpuesto;
    }

    /**
     * Sets the value of the importeImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteImpuesto(String value) {
        this.importeImpuesto = value;
    }

    /**
     * Gets the value of the importeTotalCuota property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteTotalCuota() {
        return importeTotalCuota;
    }

    /**
     * Sets the value of the importeTotalCuota property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteTotalCuota(String value) {
        this.importeTotalCuota = value;
    }

    /**
     * Gets the value of the importeDistribucionEntregaCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteDistribucionEntregaCuenta() {
        return importeDistribucionEntregaCuenta;
    }

    /**
     * Sets the value of the importeDistribucionEntregaCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteDistribucionEntregaCuenta(String value) {
        this.importeDistribucionEntregaCuenta = value;
    }

}
