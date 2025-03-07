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
 * <p>Java class for Datos_Financieros_LeasingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Datos_Financieros_LeasingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Unidad_Cuenta" type="{}Tipificacion_Unidad_CuentaType"/>
 *         &lt;element name="Importe_Neto_Material" type="{}ImporteType"/>
 *         &lt;element name="Importe_Total_Arrendamiento" type="{}ImporteType"/>
 *         &lt;element name="Importe_Opcion_Compra" type="{}ImporteType"/>
 *         &lt;element name="Importe_Entrega_Cuenta" type="{}ImporteType"/>
 *         &lt;element name="Importe_Fianza" type="{}ImporteType"/>
 *         &lt;element name="Tipo_Interes_Nominal_Anual" type="{}PorcentajeType"/>
 *         &lt;element name="Tasa_Anual_Equivalente" type="{}PorcentajeType"/>
 *         &lt;element name="Numero_Meses" type="{}NumericoType"/>
 *         &lt;element name="Fecha_Ultimo_Vencimiento" type="{}FechaType"/>
 *         &lt;element name="Comisiones" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Comision" type="{}ComisionType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Otros_Importes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Otro_Importe" type="{}Otro_ImporteType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Variabilidad_Tipo_Interes" type="{}Variabilidad_Tipo_InteresType" minOccurs="0"/>
 *         &lt;element name="Reconocimiento_Deuda" type="{}Reconocimiento_Deuda_LeasingType" maxOccurs="unbounded"/>
 *         &lt;element name="Domicilio_Pago" type="{}Domicilio_Pago_LeasingType" minOccurs="0"/>
 *         &lt;element name="Cuadro_Amortizacion" type="{}Cuadro_Amortizacion_LeasingType"/>
 *         &lt;element name="Financiacion_Impuesto_Anticipado" type="{}Financiacion_Impuesto_AnticipadoType" minOccurs="0"/>
 *         &lt;element name="Financiacion_Seguro" type="{}Financiacion_SeguroType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datos_Financieros_LeasingType", propOrder = {
    "unidadCuenta",
    "importeNetoMaterial",
    "importeTotalArrendamiento",
    "importeOpcionCompra",
    "importeEntregaCuenta",
    "importeFianza",
    "tipoInteresNominalAnual",
    "tasaAnualEquivalente",
    "numeroMeses",
    "fechaUltimoVencimiento",
    "comisiones",
    "otrosImportes",
    "variabilidadTipoInteres",
    "reconocimientoDeuda",
    "domicilioPago",
    "cuadroAmortizacion",
    "financiacionImpuestoAnticipado",
    "financiacionSeguro"
})
public class DatosFinancierosLeasingType {

    @XmlElement(name = "Unidad_Cuenta", required = true)
    protected String unidadCuenta;
    @XmlElement(name = "Importe_Neto_Material", required = true)
    protected String importeNetoMaterial;
    @XmlElement(name = "Importe_Total_Arrendamiento", required = true)
    protected String importeTotalArrendamiento;
    @XmlElement(name = "Importe_Opcion_Compra", required = true)
    protected String importeOpcionCompra;
    @XmlElement(name = "Importe_Entrega_Cuenta", required = true)
    protected String importeEntregaCuenta;
    @XmlElement(name = "Importe_Fianza", required = true)
    protected String importeFianza;
    @XmlElement(name = "Tipo_Interes_Nominal_Anual", required = true)
    protected String tipoInteresNominalAnual;
    @XmlElement(name = "Tasa_Anual_Equivalente", required = true)
    protected String tasaAnualEquivalente;
    @XmlElement(name = "Numero_Meses", required = true)
    protected String numeroMeses;
    @XmlElement(name = "Fecha_Ultimo_Vencimiento", required = true)
    protected String fechaUltimoVencimiento;
    @XmlElement(name = "Comisiones")
    protected DatosFinancierosLeasingType.Comisiones comisiones;
    @XmlElement(name = "Otros_Importes")
    protected DatosFinancierosLeasingType.OtrosImportes otrosImportes;
    @XmlElement(name = "Variabilidad_Tipo_Interes")
    protected VariabilidadTipoInteresType variabilidadTipoInteres;
    @XmlElement(name = "Reconocimiento_Deuda", required = true)
    protected List<ReconocimientoDeudaLeasingType> reconocimientoDeuda;
    @XmlElement(name = "Domicilio_Pago")
    protected DomicilioPagoLeasingType domicilioPago;
    @XmlElement(name = "Cuadro_Amortizacion", required = true)
    protected CuadroAmortizacionLeasingType cuadroAmortizacion;
    @XmlElement(name = "Financiacion_Impuesto_Anticipado")
    protected FinanciacionImpuestoAnticipadoType financiacionImpuestoAnticipado;
    @XmlElement(name = "Financiacion_Seguro")
    protected FinanciacionSeguroType financiacionSeguro;

    /**
     * Gets the value of the unidadCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidadCuenta() {
        return unidadCuenta;
    }

    /**
     * Sets the value of the unidadCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidadCuenta(String value) {
        this.unidadCuenta = value;
    }

    /**
     * Gets the value of the importeNetoMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteNetoMaterial() {
        return importeNetoMaterial;
    }

    /**
     * Sets the value of the importeNetoMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteNetoMaterial(String value) {
        this.importeNetoMaterial = value;
    }

    /**
     * Gets the value of the importeTotalArrendamiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteTotalArrendamiento() {
        return importeTotalArrendamiento;
    }

    /**
     * Sets the value of the importeTotalArrendamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteTotalArrendamiento(String value) {
        this.importeTotalArrendamiento = value;
    }

    /**
     * Gets the value of the importeOpcionCompra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteOpcionCompra() {
        return importeOpcionCompra;
    }

    /**
     * Sets the value of the importeOpcionCompra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteOpcionCompra(String value) {
        this.importeOpcionCompra = value;
    }

    /**
     * Gets the value of the importeEntregaCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteEntregaCuenta() {
        return importeEntregaCuenta;
    }

    /**
     * Sets the value of the importeEntregaCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteEntregaCuenta(String value) {
        this.importeEntregaCuenta = value;
    }

    /**
     * Gets the value of the importeFianza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteFianza() {
        return importeFianza;
    }

    /**
     * Sets the value of the importeFianza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteFianza(String value) {
        this.importeFianza = value;
    }

    /**
     * Gets the value of the tipoInteresNominalAnual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInteresNominalAnual() {
        return tipoInteresNominalAnual;
    }

    /**
     * Sets the value of the tipoInteresNominalAnual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInteresNominalAnual(String value) {
        this.tipoInteresNominalAnual = value;
    }

    /**
     * Gets the value of the tasaAnualEquivalente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasaAnualEquivalente() {
        return tasaAnualEquivalente;
    }

    /**
     * Sets the value of the tasaAnualEquivalente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasaAnualEquivalente(String value) {
        this.tasaAnualEquivalente = value;
    }

    /**
     * Gets the value of the numeroMeses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroMeses() {
        return numeroMeses;
    }

    /**
     * Sets the value of the numeroMeses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroMeses(String value) {
        this.numeroMeses = value;
    }

    /**
     * Gets the value of the fechaUltimoVencimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaUltimoVencimiento() {
        return fechaUltimoVencimiento;
    }

    /**
     * Sets the value of the fechaUltimoVencimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaUltimoVencimiento(String value) {
        this.fechaUltimoVencimiento = value;
    }

    /**
     * Gets the value of the comisiones property.
     * 
     * @return
     *     possible object is
     *     {@link DatosFinancierosLeasingType.Comisiones }
     *     
     */
    public DatosFinancierosLeasingType.Comisiones getComisiones() {
        return comisiones;
    }

    /**
     * Sets the value of the comisiones property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosFinancierosLeasingType.Comisiones }
     *     
     */
    public void setComisiones(DatosFinancierosLeasingType.Comisiones value) {
        this.comisiones = value;
    }

    /**
     * Gets the value of the otrosImportes property.
     * 
     * @return
     *     possible object is
     *     {@link DatosFinancierosLeasingType.OtrosImportes }
     *     
     */
    public DatosFinancierosLeasingType.OtrosImportes getOtrosImportes() {
        return otrosImportes;
    }

    /**
     * Sets the value of the otrosImportes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosFinancierosLeasingType.OtrosImportes }
     *     
     */
    public void setOtrosImportes(DatosFinancierosLeasingType.OtrosImportes value) {
        this.otrosImportes = value;
    }

    /**
     * Gets the value of the variabilidadTipoInteres property.
     * 
     * @return
     *     possible object is
     *     {@link VariabilidadTipoInteresType }
     *     
     */
    public VariabilidadTipoInteresType getVariabilidadTipoInteres() {
        return variabilidadTipoInteres;
    }

    /**
     * Sets the value of the variabilidadTipoInteres property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariabilidadTipoInteresType }
     *     
     */
    public void setVariabilidadTipoInteres(VariabilidadTipoInteresType value) {
        this.variabilidadTipoInteres = value;
    }

    /**
     * Gets the value of the reconocimientoDeuda property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reconocimientoDeuda property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReconocimientoDeuda().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReconocimientoDeudaLeasingType }
     * 
     * 
     */
    public List<ReconocimientoDeudaLeasingType> getReconocimientoDeuda() {
        if (reconocimientoDeuda == null) {
            reconocimientoDeuda = new ArrayList<ReconocimientoDeudaLeasingType>();
        }
        return this.reconocimientoDeuda;
    }

    /**
     * Gets the value of the domicilioPago property.
     * 
     * @return
     *     possible object is
     *     {@link DomicilioPagoLeasingType }
     *     
     */
    public DomicilioPagoLeasingType getDomicilioPago() {
        return domicilioPago;
    }

    /**
     * Sets the value of the domicilioPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomicilioPagoLeasingType }
     *     
     */
    public void setDomicilioPago(DomicilioPagoLeasingType value) {
        this.domicilioPago = value;
    }

    /**
     * Gets the value of the cuadroAmortizacion property.
     * 
     * @return
     *     possible object is
     *     {@link CuadroAmortizacionLeasingType }
     *     
     */
    public CuadroAmortizacionLeasingType getCuadroAmortizacion() {
        return cuadroAmortizacion;
    }

    /**
     * Sets the value of the cuadroAmortizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CuadroAmortizacionLeasingType }
     *     
     */
    public void setCuadroAmortizacion(CuadroAmortizacionLeasingType value) {
        this.cuadroAmortizacion = value;
    }

    /**
     * Gets the value of the financiacionImpuestoAnticipado property.
     * 
     * @return
     *     possible object is
     *     {@link FinanciacionImpuestoAnticipadoType }
     *     
     */
    public FinanciacionImpuestoAnticipadoType getFinanciacionImpuestoAnticipado() {
        return financiacionImpuestoAnticipado;
    }

    /**
     * Sets the value of the financiacionImpuestoAnticipado property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinanciacionImpuestoAnticipadoType }
     *     
     */
    public void setFinanciacionImpuestoAnticipado(FinanciacionImpuestoAnticipadoType value) {
        this.financiacionImpuestoAnticipado = value;
    }

    /**
     * Gets the value of the financiacionSeguro property.
     * 
     * @return
     *     possible object is
     *     {@link FinanciacionSeguroType }
     *     
     */
    public FinanciacionSeguroType getFinanciacionSeguro() {
        return financiacionSeguro;
    }

    /**
     * Sets the value of the financiacionSeguro property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinanciacionSeguroType }
     *     
     */
    public void setFinanciacionSeguro(FinanciacionSeguroType value) {
        this.financiacionSeguro = value;
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
     *         &lt;element name="Comision" type="{}ComisionType" maxOccurs="unbounded"/>
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
        "comision"
    })
    public static class Comisiones {

        @XmlElement(name = "Comision", required = true)
        protected List<ComisionType> comision;

        /**
         * Gets the value of the comision property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the comision property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComision().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ComisionType }
         * 
         * 
         */
        public List<ComisionType> getComision() {
            if (comision == null) {
                comision = new ArrayList<ComisionType>();
            }
            return this.comision;
        }

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
     *         &lt;element name="Otro_Importe" type="{}Otro_ImporteType" maxOccurs="unbounded"/>
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
        "otroImporte"
    })
    public static class OtrosImportes {

        @XmlElement(name = "Otro_Importe", required = true)
        protected List<OtroImporteType> otroImporte;

        /**
         * Gets the value of the otroImporte property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otroImporte property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtroImporte().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OtroImporteType }
         * 
         * 
         */
        public List<OtroImporteType> getOtroImporte() {
            if (otroImporte == null) {
                otroImporte = new ArrayList<OtroImporteType>();
            }
            return this.otroImporte;
        }

    }

}
