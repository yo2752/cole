//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.06 at 09:29:59 AM CET 
//


package org.gestoresmadrid.oegamConversiones.jaxb.ctit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.gestoresmadrid.oegamConversiones.jaxb.matw.TipoMotivoTutela;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}DNI_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}SEXO_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}CONCEPTO_REPRESENTANTE_ADQUIRIENTE" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "DATOS_REPRESENTANTE_ADQUIRIENTE")
public class DATOSREPRESENTANTEADQUIRIENTE {

    @XmlElement(name = "DNI_REPRESENTANTE_ADQUIRIENTE")
    protected String dnirepresentanteadquiriente;
    @XmlElement(name = "SEXO_REPRESENTANTE_ADQUIRIENTE")
    protected String sexorepresentanteadquiriente;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_ADQUIRIENTE")
    protected String apellido1RAZONSOCIALREPRESENTANTEADQUIRIENTE;
    @XmlElement(name = "APELLIDO2_REPRESENTANTE_ADQUIRIENTE")
    protected String apellido2REPRESENTANTEADQUIRIENTE;
    @XmlElement(name = "NOMBRE_REPRESENTANTE_ADQUIRIENTE")
    protected String nombrerepresentanteadquiriente;
    @XmlElement(name = "CONCEPTO_REPRESENTANTE_ADQUIRIENTE")
    protected String conceptorepresentanteadquiriente;
    @XmlElement(name = "DOCUMENTOS_REPRESENTANTE_ADQUIRIENTE")
    protected String documentosrepresentanteadquiriente;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_REPRESENTANTE_ADQUIRIENTE")
    protected String fechacaducidadnifrepresentanteadquiriente;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ADQUIRIENTE")
    protected String tipodocumentosustitutivorepresentanteadquiriente;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ADQUIRIENTE")
    protected String fechacaducidaddocumentosustitutivorepresentanteadquiriente;
    @XmlElement(name = "INDEFINIDO_REPRESENTANTE_ADQUIRIENTE")
    protected String indefinidorepresentanteadquiriente;
    @XmlElement(name = "MOTIVO_TUTELA_REPRESENTANTE_ADQUIRIENTE")
    protected TipoMotivoTutela motivotutelarepresentanteadquiriente;
    @XmlElement(name = "FECHA_INICIO_TUTELA_ADQUIERIENTE")
    protected String fechainiciotutelaadquieriente;
    @XmlElement(name = "FECHA_FIN_TUTELA_ADQUIERIENTE")
    protected String fechafintutelaadquieriente;
    /**
     * Gets the value of the dnirepresentanteadquiriente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIREPRESENTANTEADQUIRIENTE() {
        return dnirepresentanteadquiriente;
    }

    /**
     * Sets the value of the dnirepresentanteadquiriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIREPRESENTANTEADQUIRIENTE(String value) {
        this.dnirepresentanteadquiriente = value;
    }

    /**
     * Gets the value of the sexorepresentanteadquiriente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOREPRESENTANTEADQUIRIENTE() {
        return sexorepresentanteadquiriente;
    }

    /**
     * Sets the value of the sexorepresentanteadquiriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOREPRESENTANTEADQUIRIENTE(String value) {
        this.sexorepresentanteadquiriente = value;
    }

    /**
     * Gets the value of the apellido1RAZONSOCIALREPRESENTANTEADQUIRIENTE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE() {
        return apellido1RAZONSOCIALREPRESENTANTEADQUIRIENTE;
    }

    /**
     * Sets the value of the apellido1RAZONSOCIALREPRESENTANTEADQUIRIENTE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE(String value) {
        this.apellido1RAZONSOCIALREPRESENTANTEADQUIRIENTE = value;
    }

    /**
     * Gets the value of the apellido2REPRESENTANTEADQUIRIENTE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2REPRESENTANTEADQUIRIENTE() {
        return apellido2REPRESENTANTEADQUIRIENTE;
    }

    /**
     * Sets the value of the apellido2REPRESENTANTEADQUIRIENTE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2REPRESENTANTEADQUIRIENTE(String value) {
        this.apellido2REPRESENTANTEADQUIRIENTE = value;
    }

    /**
     * Gets the value of the nombrerepresentanteadquiriente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREREPRESENTANTEADQUIRIENTE() {
        return nombrerepresentanteadquiriente;
    }

    /**
     * Sets the value of the nombrerepresentanteadquiriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREREPRESENTANTEADQUIRIENTE(String value) {
        this.nombrerepresentanteadquiriente = value;
    }

    /**
     * Gets the value of the conceptorepresentanteadquiriente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTOREPRESENTANTEADQUIRIENTE() {
        return conceptorepresentanteadquiriente;
    }

    /**
     * Sets the value of the conceptorepresentanteadquiriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTOREPRESENTANTEADQUIRIENTE(String value) {
        this.conceptorepresentanteadquiriente = value;
    }

  
  
   
   /**
    * Gets the value of the motivorepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public TipoMotivoTutela getMOTIVOTUTELAREPRESENTANTEADQUIRIENTE() {
       return motivotutelarepresentanteadquiriente;
   }

   /**
    * Sets the value of the motivorepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setMOTIVOTUTELAREPRESENTANTEADQUIRIENTE(TipoMotivoTutela value) {
       this.motivotutelarepresentanteadquiriente = value;
   }

   /**
    * Gets the value of the fechainiciotutelaadquieriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getFECHAINICIOTUTELAADQUIERIENTE() {
       return fechainiciotutelaadquieriente;
   }

   /**
    * Sets the value of the fechainiciotutelaadquieriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setFECHAINICIOTUTELAADQUIERIENTE(String value) {
       this.fechainiciotutelaadquieriente = value;
   }

   /**
    * Gets the value of the fechafintutelaadquieriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getFECHAFINTUTELAADQUIERIENTE() {
       return fechafintutelaadquieriente;
   }

   /**
    * Sets the value of the fechafintutelaadquieriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setFECHAFINTUTELAADQUIERIENTE(String value) {
       this.fechafintutelaadquieriente = value;
   }

   /**
    * Gets the value of the documentosrepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getDOCUMENTOSREPRESENTANTEADQUIRIENTE() {
       return documentosrepresentanteadquiriente;
   }

   /**
    * Sets the value of the documentosrepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setDOCUMENTOSREPRESENTANTEADQUIRIENTE(String value) {
       this.documentosrepresentanteadquiriente = value;
   }

   /**
    * Gets the value of the fechacaducidadnifrepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE() {
       return fechacaducidadnifrepresentanteadquiriente;
   }

   /**
    * Sets the value of the fechacaducidadnifrepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE(String value) {
       this.fechacaducidadnifrepresentanteadquiriente = value;
   }

   /**
    * Gets the value of the tipodocumentosustitutivorepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE() {
       return tipodocumentosustitutivorepresentanteadquiriente;
   }

   /**
    * Sets the value of the tipodocumentosustitutivorepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE(String value) {
       this.tipodocumentosustitutivorepresentanteadquiriente = value;
   }

   /**
    * Gets the value of the fechacaducidaddocumentosustitutivorepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE() {
       return fechacaducidaddocumentosustitutivorepresentanteadquiriente;
   }

   /**
    * Sets the value of the fechacaducidaddocumentosustitutivorepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE(String value) {
       this.fechacaducidaddocumentosustitutivorepresentanteadquiriente = value;
   }

   /**
    * Gets the value of the indefinidorepresentanteadquiriente property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getINDEFINIDOREPRESENTANTEADQUIRIENTE() {
       return indefinidorepresentanteadquiriente;
   }

   /**
    * Sets the value of the indefinidorepresentanteadquiriente property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setINDEFINIDOREPRESENTANTEADQUIRIENTE(String value) {
       this.indefinidorepresentanteadquiriente = value;
   }
    
}
