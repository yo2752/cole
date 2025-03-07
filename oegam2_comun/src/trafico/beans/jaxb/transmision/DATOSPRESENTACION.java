//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}CODIGO_ELECTRONICO_TRANSFERENCIA"/>
 *         &lt;element ref="{}TIPO_TASA"/>
 *         &lt;element ref="{}CODIGO_TASA"/>
 *         &lt;element ref="{}TIPO_TASA_INFORME" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_TASA_INFORME" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_TASA_CAMBIO" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_TASA_CAMBIO" minOccurs="0"/>
 *         &lt;element ref="{}MODELO_ITP" minOccurs="0"/>
 *         &lt;element ref="{}EXENTO_ITP" minOccurs="0"/>
 *         &lt;element ref="{}NO_SUJETO_ITP" minOccurs="0"/>
 *         &lt;element ref="{}NUM_AUTO_ITP" minOccurs="0"/>
 *         &lt;element ref="{}NUM_ADJUDICACION_ISD" minOccurs="0"/>
 *         &lt;element ref="{}MODELO_ISD" minOccurs="0"/>
 *         &lt;element ref="{}EXENTO_ISD" minOccurs="0"/>
 *         &lt;element ref="{}NO_SUJETO_ISD" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_ELECTRONICO_MATRICULACION" minOccurs="0"/>
 *         &lt;element ref="{}EXENTO_CEM" minOccurs="0"/>
 *         &lt;element ref="{}MODELO_IEDMT" minOccurs="0"/>
 *         &lt;element ref="{}EXENTO_IEDMT" minOccurs="0"/>
 *         &lt;element ref="{}NO_SUJETO_IEDMT" minOccurs="0"/>
 *         &lt;element ref="{}ID_REDUCCION_05" minOccurs="0"/>
 *         &lt;element ref="{}ID_NO_SUJECCION_06" minOccurs="0"/>
 *         &lt;element ref="{}ALTA_IVTM" minOccurs="0"/>
 *         &lt;element ref="{}VALOR_REAL" minOccurs="0"/>
 *         &lt;element ref="{}DUA" minOccurs="0"/>
 *         &lt;element ref="{}CEMA" minOccurs="0"/>
 *         &lt;element ref="{}NUMERO_FACTURA" minOccurs="0"/>
 *         &lt;element ref="{}JEFATURA_PROVINCIAL"/>
 *         &lt;element ref="{}FECHA_PRESENTACION"/>
 *         &lt;element ref="{}PROVINCIA_CET" minOccurs="0"/>
 *         &lt;element ref="{}PROVINCIA_CEM" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_FACTURA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CONTRATO" minOccurs="0"/>
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
@XmlRootElement(name = "DATOS_PRESENTACION")
public class DATOSPRESENTACION {

    @XmlElement(name = "CODIGO_ELECTRONICO_TRANSFERENCIA", required = true)
    protected String codigoelectronicotransferencia;
    @XmlElement(name = "TIPO_TASA", required = true)
    protected String tipotasa;
    @XmlElement(name = "CODIGO_TASA", required = true)
    protected String codigotasa;
    @XmlElement(name = "TIPO_TASA_INFORME")
    protected String tipotasainforme;
    @XmlElement(name = "CODIGO_TASA_INFORME")
    protected String codigotasainforme;
    @XmlElement(name = "TIPO_TASA_CAMBIO")
    protected String tipotasacambio;
    @XmlElement(name = "CODIGO_TASA_CAMBIO")
    protected String codigotasacambio;
    @XmlElement(name = "MODELO_ITP")
    protected String modeloitp;
    @XmlElement(name = "EXENTO_ITP")
    protected String exentoitp;
    @XmlElement(name = "NO_SUJETO_ITP")
    protected String nosujetoitp;
    @XmlElement(name = "NUM_AUTO_ITP")
    protected String numautoitp;
    @XmlElement(name = "NUM_ADJUDICACION_ISD")
    protected String numadjudicacionisd;
    @XmlElement(name = "MODELO_ISD")
    protected String modeloisd;
    @XmlElement(name = "EXENTO_ISD")
    protected String exentoisd;
    @XmlElement(name = "NO_SUJETO_ISD")
    protected String nosujetoisd;
    @XmlElement(name = "CODIGO_ELECTRONICO_MATRICULACION")
    protected String codigoelectronicomatriculacion;
    @XmlElement(name = "EXENTO_CEM")
    protected String exentocem;
    @XmlElement(name = "MODELO_IEDMT")
    protected String modeloiedmt;
    @XmlElement(name = "EXENTO_IEDMT")
    protected String exentoiedmt;
    @XmlElement(name = "NO_SUJETO_IEDMT")
    protected String nosujetoiedmt;
    @XmlElement(name = "ID_REDUCCION_05")
    protected String idreduccion05;
    @XmlElement(name = "ID_NO_SUJECCION_06")
    protected String idnosujeccion06;
    @XmlElement(name = "ALTA_IVTM")
    protected String altaivtm;
    @XmlElement(name = "VALOR_REAL")
    protected BigDecimal valorreal;
    @XmlElement(name = "DUA")
    protected String dua;
    @XmlElement(name = "CEMA")
    protected String cema;
    @XmlElement(name = "NUMERO_FACTURA")
    protected String numerofactura;
    @XmlElement(name = "JEFATURA_PROVINCIAL", required = true)
    protected String jefaturaprovincial;
    @XmlElement(name = "FECHA_PRESENTACION", required = true)
    protected String fechapresentacion;
    @XmlElement(name = "PROVINCIA_CET")
    protected String provinciacet;
    @XmlElement(name = "PROVINCIA_CEM")
    protected String provinciacem;
    @XmlElement(name = "FECHA_FACTURA")
    protected String fechafactura;
    @XmlElement(name = "FECHA_CONTRATO")
    protected String fechacontrato;

    /**
     * Obtiene el valor de la propiedad codigoelectronicotransferencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOELECTRONICOTRANSFERENCIA() {
        return codigoelectronicotransferencia;
    }

    /**
     * Define el valor de la propiedad codigoelectronicotransferencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOELECTRONICOTRANSFERENCIA(String value) {
        this.codigoelectronicotransferencia = value;
    }

    /**
     * Obtiene el valor de la propiedad tipotasa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOTASA() {
        return tipotasa;
    }

    /**
     * Define el valor de la propiedad tipotasa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOTASA(String value) {
        this.tipotasa = value;
    }

    /**
     * Obtiene el valor de la propiedad codigotasa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOTASA() {
        return codigotasa;
    }

    /**
     * Define el valor de la propiedad codigotasa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOTASA(String value) {
        this.codigotasa = value;
    }

    /**
     * Obtiene el valor de la propiedad tipotasainforme.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOTASAINFORME() {
        return tipotasainforme;
    }

    /**
     * Define el valor de la propiedad tipotasainforme.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOTASAINFORME(String value) {
        this.tipotasainforme = value;
    }

    /**
     * Obtiene el valor de la propiedad codigotasainforme.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOTASAINFORME() {
        return codigotasainforme;
    }

    /**
     * Define el valor de la propiedad codigotasainforme.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOTASAINFORME(String value) {
        this.codigotasainforme = value;
    }

    /**
     * Obtiene el valor de la propiedad tipotasacambio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOTASACAMBIO() {
        return tipotasacambio;
    }

    /**
     * Define el valor de la propiedad tipotasacambio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOTASACAMBIO(String value) {
        this.tipotasacambio = value;
    }

    /**
     * Obtiene el valor de la propiedad codigotasacambio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOTASACAMBIO() {
        return codigotasacambio;
    }

    /**
     * Define el valor de la propiedad codigotasacambio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOTASACAMBIO(String value) {
        this.codigotasacambio = value;
    }

    /**
     * Obtiene el valor de la propiedad modeloitp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELOITP() {
        return modeloitp;
    }

    /**
     * Define el valor de la propiedad modeloitp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELOITP(String value) {
        this.modeloitp = value;
    }

    /**
     * Obtiene el valor de la propiedad exentoitp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXENTOITP() {
        return exentoitp;
    }

    /**
     * Define el valor de la propiedad exentoitp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXENTOITP(String value) {
        this.exentoitp = value;
    }

    /**
     * Obtiene el valor de la propiedad nosujetoitp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOSUJETOITP() {
        return nosujetoitp;
    }

    /**
     * Define el valor de la propiedad nosujetoitp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOSUJETOITP(String value) {
        this.nosujetoitp = value;
    }

    /**
     * Obtiene el valor de la propiedad numautoitp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMAUTOITP() {
        return numautoitp;
    }

    /**
     * Define el valor de la propiedad numautoitp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMAUTOITP(String value) {
        this.numautoitp = value;
    }

    /**
     * Obtiene el valor de la propiedad numadjudicacionisd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMADJUDICACIONISD() {
        return numadjudicacionisd;
    }

    /**
     * Define el valor de la propiedad numadjudicacionisd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMADJUDICACIONISD(String value) {
        this.numadjudicacionisd = value;
    }

    /**
     * Obtiene el valor de la propiedad modeloisd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELOISD() {
        return modeloisd;
    }

    /**
     * Define el valor de la propiedad modeloisd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELOISD(String value) {
        this.modeloisd = value;
    }

    /**
     * Obtiene el valor de la propiedad exentoisd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXENTOISD() {
        return exentoisd;
    }

    /**
     * Define el valor de la propiedad exentoisd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXENTOISD(String value) {
        this.exentoisd = value;
    }

    /**
     * Obtiene el valor de la propiedad nosujetoisd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOSUJETOISD() {
        return nosujetoisd;
    }

    /**
     * Define el valor de la propiedad nosujetoisd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOSUJETOISD(String value) {
        this.nosujetoisd = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoelectronicomatriculacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOELECTRONICOMATRICULACION() {
        return codigoelectronicomatriculacion;
    }

    /**
     * Define el valor de la propiedad codigoelectronicomatriculacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOELECTRONICOMATRICULACION(String value) {
        this.codigoelectronicomatriculacion = value;
    }

    /**
     * Obtiene el valor de la propiedad exentocem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXENTOCEM() {
        return exentocem;
    }

    /**
     * Define el valor de la propiedad exentocem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXENTOCEM(String value) {
        this.exentocem = value;
    }

    /**
     * Obtiene el valor de la propiedad modeloiedmt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELOIEDMT() {
        return modeloiedmt;
    }

    /**
     * Define el valor de la propiedad modeloiedmt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELOIEDMT(String value) {
        this.modeloiedmt = value;
    }

    /**
     * Obtiene el valor de la propiedad exentoiedmt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXENTOIEDMT() {
        return exentoiedmt;
    }

    /**
     * Define el valor de la propiedad exentoiedmt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXENTOIEDMT(String value) {
        this.exentoiedmt = value;
    }

    /**
     * Obtiene el valor de la propiedad nosujetoiedmt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOSUJETOIEDMT() {
        return nosujetoiedmt;
    }

    /**
     * Define el valor de la propiedad nosujetoiedmt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOSUJETOIEDMT(String value) {
        this.nosujetoiedmt = value;
    }

    /**
     * Obtiene el valor de la propiedad idreduccion05.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDREDUCCION05() {
        return idreduccion05;
    }

    /**
     * Define el valor de la propiedad idreduccion05.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDREDUCCION05(String value) {
        this.idreduccion05 = value;
    }

    /**
     * Obtiene el valor de la propiedad idnosujeccion06.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDNOSUJECCION06() {
        return idnosujeccion06;
    }

    /**
     * Define el valor de la propiedad idnosujeccion06.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDNOSUJECCION06(String value) {
        this.idnosujeccion06 = value;
    }

    /**
     * Obtiene el valor de la propiedad altaivtm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALTAIVTM() {
        return altaivtm;
    }

    /**
     * Define el valor de la propiedad altaivtm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALTAIVTM(String value) {
        this.altaivtm = value;
    }

    /**
     * Obtiene el valor de la propiedad valorreal.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVALORREAL() {
        return valorreal;
    }

    /**
     * Define el valor de la propiedad valorreal.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVALORREAL(BigDecimal value) {
        this.valorreal = value;
    }

    /**
     * Obtiene el valor de la propiedad dua.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDUA() {
        return dua;
    }

    /**
     * Define el valor de la propiedad dua.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDUA(String value) {
        this.dua = value;
    }

    /**
     * Obtiene el valor de la propiedad cema.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCEMA() {
        return cema;
    }

    /**
     * Define el valor de la propiedad cema.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCEMA(String value) {
        this.cema = value;
    }

    /**
     * Obtiene el valor de la propiedad numerofactura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROFACTURA() {
        return numerofactura;
    }

    /**
     * Define el valor de la propiedad numerofactura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROFACTURA(String value) {
        this.numerofactura = value;
    }

    /**
     * Obtiene el valor de la propiedad jefaturaprovincial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJEFATURAPROVINCIAL() {
        return jefaturaprovincial;
    }

    /**
     * Define el valor de la propiedad jefaturaprovincial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJEFATURAPROVINCIAL(String value) {
        this.jefaturaprovincial = value;
    }

    /**
     * Obtiene el valor de la propiedad fechapresentacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAPRESENTACION() {
        return fechapresentacion;
    }

    /**
     * Define el valor de la propiedad fechapresentacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAPRESENTACION(String value) {
        this.fechapresentacion = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciacet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIACET() {
        return provinciacet;
    }

    /**
     * Define el valor de la propiedad provinciacet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIACET(String value) {
        this.provinciacet = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciacem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIACEM() {
        return provinciacem;
    }

    /**
     * Define el valor de la propiedad provinciacem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIACEM(String value) {
        this.provinciacem = value;
    }

    /**
     * Obtiene el valor de la propiedad fechafactura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFACTURA() {
        return fechafactura;
    }

    /**
     * Define el valor de la propiedad fechafactura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFACTURA(String value) {
        this.fechafactura = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacontrato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACONTRATO() {
        return fechacontrato;
    }

    /**
     * Define el valor de la propiedad fechacontrato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACONTRATO(String value) {
        this.fechacontrato = value;
    }

}
