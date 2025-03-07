//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

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
 *         &lt;element ref="{}OFICINA_LIQUIDADORA"/>
 *         &lt;element ref="{}TIPO_VEHICULO"/>
 *         &lt;element ref="{}FECHA_PRIMERA_MATRICULACION"/>
 *         &lt;element ref="{}FECHA_DEVENGO"/>
 *         &lt;element ref="{}MARCA_620"/>
 *         &lt;element ref="{}MARCA_620_DESC"/>
 *         &lt;element ref="{}MODELO_620"/>
 *         &lt;element ref="{}MODELO_620_DESC"/>
 *         &lt;element ref="{}POTENCIA_FISCAL"/>
 *         &lt;element ref="{}CILINDRADA"/>
 *         &lt;element ref="{}NUM_CILINDROS"/>
 *         &lt;element ref="{}CARACTERISTICAS"/>
 *         &lt;element ref="{}ANIO_FABRICACION"/>
 *         &lt;element ref="{}CARBURANTE"/>
 *         &lt;element ref="{}EXENTO"/>
 *         &lt;element ref="{}NO_SUJETO"/>
 *         &lt;element ref="{}FUNDAMENTO_EXENCION"/>
 *         &lt;element ref="{}FUNDAMENTO_NO_SUJETO"/>
 *         &lt;element ref="{}PORCENTAJE_REDUCCION_ANUAL"/>
 *         &lt;element ref="{}VALOR_DECLARADO"/>
 *         &lt;element ref="{}PORCENTAJE_ADQUISICION"/>
 *         &lt;element ref="{}BASE_IMPONIBLE"/>
 *         &lt;element ref="{}TIPO_GRAVAMEN"/>
 *         &lt;element ref="{}CUOTA_TRIBUTARIA"/>
 *         &lt;element ref="{}DIRECCION_DISTINTA_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}ESTADO_620" minOccurs="0"/>
 *         &lt;element ref="{}REDUCCION_CODIGO" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_MOTOR" minOccurs="0"/>
 *         &lt;element ref="{}PROCEDENCIA_620" minOccurs="0"/>
 *         &lt;element ref="{}SUBASTA" minOccurs="0"/>
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
@XmlRootElement(name = "DATOS_620")
public class DATOS620 {

    @XmlElement(name = "OFICINA_LIQUIDADORA", required = true)
    protected String oficinaliquidadora;
    @XmlElement(name = "TIPO_VEHICULO", required = true)
    protected String tipovehiculo;
    @XmlElement(name = "FECHA_PRIMERA_MATRICULACION", required = true)
    protected String fechaprimeramatriculacion;
    @XmlElement(name = "FECHA_DEVENGO", required = true)
    protected String fechadevengo;
    @XmlElement(name = "MARCA_620", required = true)
    protected String marca620;
    @XmlElement(name = "MARCA_620_DESC", required = true)
    protected String marca620DESC;
    @XmlElement(name = "MODELO_620", required = true)
    protected String modelo620;
    @XmlElement(name = "MODELO_620_DESC", required = true)
    protected String modelo620DESC;
    @XmlElement(name = "POTENCIA_FISCAL", required = true)
    protected String potenciafiscal;
    @XmlElement(name = "CILINDRADA", required = true)
    protected String cilindrada;
    @XmlElement(name = "NUM_CILINDROS", required = true)
    protected String numcilindros;
    @XmlElement(name = "CARACTERISTICAS", required = true)
    protected String caracteristicas;
    @XmlElement(name = "ANIO_FABRICACION", required = true)
    protected String aniofabricacion;
    @XmlElement(name = "CARBURANTE", required = true)
    protected String carburante;
    @XmlElement(name = "EXENTO", required = true)
    protected String exento;
    @XmlElement(name = "NO_SUJETO", required = true)
    protected String nosujeto;
    @XmlElement(name = "FUNDAMENTO_EXENCION", required = true)
    protected String fundamentoexencion;
    @XmlElement(name = "FUNDAMENTO_NO_SUJETO", required = true)
    protected String fundamentonosujeto;
    @XmlElement(name = "PORCENTAJE_REDUCCION_ANUAL", required = true)
    protected String porcentajereduccionanual;
    @XmlElement(name = "VALOR_DECLARADO", required = true)
    protected String valordeclarado;
    @XmlElement(name = "PORCENTAJE_ADQUISICION", required = true)
    protected String porcentajeadquisicion;
    @XmlElement(name = "BASE_IMPONIBLE", required = true)
    protected String baseimponible;
    @XmlElement(name = "TIPO_GRAVAMEN", required = true)
    protected String tipogravamen;
    @XmlElement(name = "CUOTA_TRIBUTARIA", required = true)
    protected String cuotatributaria;
    @XmlElement(name = "DIRECCION_DISTINTA_ADQUIRIENTE")
    protected DIRECCIONDISTINTAADQUIRIENTE direcciondistintaadquiriente;
    @XmlElement(name = "ESTADO_620")
    protected String estado620;
    @XmlElement(name = "REDUCCION_CODIGO")
    protected String reduccioncodigo;
    @XmlElement(name = "TIPO_MOTOR")
    protected String tipomotor;
    @XmlElement(name = "PROCEDENCIA_620")
    protected String procedencia620;
    @XmlElement(name = "SUBASTA")
    protected String subasta;

    /**
     * Obtiene el valor de la propiedad oficinaliquidadora.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOFICINALIQUIDADORA() {
        return oficinaliquidadora;
    }

    /**
     * Define el valor de la propiedad oficinaliquidadora.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOFICINALIQUIDADORA(String value) {
        this.oficinaliquidadora = value;
    }

    /**
     * Obtiene el valor de la propiedad tipovehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOVEHICULO() {
        return tipovehiculo;
    }

    /**
     * Define el valor de la propiedad tipovehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOVEHICULO(String value) {
        this.tipovehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaprimeramatriculacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAPRIMERAMATRICULACION() {
        return fechaprimeramatriculacion;
    }

    /**
     * Define el valor de la propiedad fechaprimeramatriculacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAPRIMERAMATRICULACION(String value) {
        this.fechaprimeramatriculacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechadevengo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHADEVENGO() {
        return fechadevengo;
    }

    /**
     * Define el valor de la propiedad fechadevengo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHADEVENGO(String value) {
        this.fechadevengo = value;
    }

    /**
     * Obtiene el valor de la propiedad marca620.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARCA620() {
        return marca620;
    }

    /**
     * Define el valor de la propiedad marca620.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARCA620(String value) {
        this.marca620 = value;
    }

    /**
     * Obtiene el valor de la propiedad marca620DESC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARCA620DESC() {
        return marca620DESC;
    }

    /**
     * Define el valor de la propiedad marca620DESC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARCA620DESC(String value) {
        this.marca620DESC = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo620.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELO620() {
        return modelo620;
    }

    /**
     * Define el valor de la propiedad modelo620.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELO620(String value) {
        this.modelo620 = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo620DESC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELO620DESC() {
        return modelo620DESC;
    }

    /**
     * Define el valor de la propiedad modelo620DESC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELO620DESC(String value) {
        this.modelo620DESC = value;
    }

    /**
     * Obtiene el valor de la propiedad potenciafiscal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOTENCIAFISCAL() {
        return potenciafiscal;
    }

    /**
     * Define el valor de la propiedad potenciafiscal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOTENCIAFISCAL(String value) {
        this.potenciafiscal = value;
    }

    /**
     * Obtiene el valor de la propiedad cilindrada.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCILINDRADA() {
        return cilindrada;
    }

    /**
     * Define el valor de la propiedad cilindrada.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCILINDRADA(String value) {
        this.cilindrada = value;
    }

    /**
     * Obtiene el valor de la propiedad numcilindros.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMCILINDROS() {
        return numcilindros;
    }

    /**
     * Define el valor de la propiedad numcilindros.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMCILINDROS(String value) {
        this.numcilindros = value;
    }

    /**
     * Obtiene el valor de la propiedad caracteristicas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARACTERISTICAS() {
        return caracteristicas;
    }

    /**
     * Define el valor de la propiedad caracteristicas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARACTERISTICAS(String value) {
        this.caracteristicas = value;
    }

    /**
     * Obtiene el valor de la propiedad aniofabricacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANIOFABRICACION() {
        return aniofabricacion;
    }

    /**
     * Define el valor de la propiedad aniofabricacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANIOFABRICACION(String value) {
        this.aniofabricacion = value;
    }

    /**
     * Obtiene el valor de la propiedad carburante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARBURANTE() {
        return carburante;
    }

    /**
     * Define el valor de la propiedad carburante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARBURANTE(String value) {
        this.carburante = value;
    }

    /**
     * Obtiene el valor de la propiedad exento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXENTO() {
        return exento;
    }

    /**
     * Define el valor de la propiedad exento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXENTO(String value) {
        this.exento = value;
    }

    /**
     * Obtiene el valor de la propiedad nosujeto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOSUJETO() {
        return nosujeto;
    }

    /**
     * Define el valor de la propiedad nosujeto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOSUJETO(String value) {
        this.nosujeto = value;
    }

    /**
     * Obtiene el valor de la propiedad fundamentoexencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFUNDAMENTOEXENCION() {
        return fundamentoexencion;
    }

    /**
     * Define el valor de la propiedad fundamentoexencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFUNDAMENTOEXENCION(String value) {
        this.fundamentoexencion = value;
    }

    /**
     * Obtiene el valor de la propiedad fundamentonosujeto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFUNDAMENTONOSUJETO() {
        return fundamentonosujeto;
    }

    /**
     * Define el valor de la propiedad fundamentonosujeto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFUNDAMENTONOSUJETO(String value) {
        this.fundamentonosujeto = value;
    }

    /**
     * Obtiene el valor de la propiedad porcentajereduccionanual.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPORCENTAJEREDUCCIONANUAL() {
        return porcentajereduccionanual;
    }

    /**
     * Define el valor de la propiedad porcentajereduccionanual.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPORCENTAJEREDUCCIONANUAL(String value) {
        this.porcentajereduccionanual = value;
    }

    /**
     * Obtiene el valor de la propiedad valordeclarado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALORDECLARADO() {
        return valordeclarado;
    }

    /**
     * Define el valor de la propiedad valordeclarado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALORDECLARADO(String value) {
        this.valordeclarado = value;
    }

    /**
     * Obtiene el valor de la propiedad porcentajeadquisicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPORCENTAJEADQUISICION() {
        return porcentajeadquisicion;
    }

    /**
     * Define el valor de la propiedad porcentajeadquisicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPORCENTAJEADQUISICION(String value) {
        this.porcentajeadquisicion = value;
    }

    /**
     * Obtiene el valor de la propiedad baseimponible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBASEIMPONIBLE() {
        return baseimponible;
    }

    /**
     * Define el valor de la propiedad baseimponible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBASEIMPONIBLE(String value) {
        this.baseimponible = value;
    }

    /**
     * Obtiene el valor de la propiedad tipogravamen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOGRAVAMEN() {
        return tipogravamen;
    }

    /**
     * Define el valor de la propiedad tipogravamen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOGRAVAMEN(String value) {
        this.tipogravamen = value;
    }

    /**
     * Obtiene el valor de la propiedad cuotatributaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUOTATRIBUTARIA() {
        return cuotatributaria;
    }

    /**
     * Define el valor de la propiedad cuotatributaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUOTATRIBUTARIA(String value) {
        this.cuotatributaria = value;
    }

    /**
     * Obtiene el valor de la propiedad direcciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link DIRECCIONDISTINTAADQUIRIENTE }
     *     
     */
    public DIRECCIONDISTINTAADQUIRIENTE getDIRECCIONDISTINTAADQUIRIENTE() {
        return direcciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad direcciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link DIRECCIONDISTINTAADQUIRIENTE }
     *     
     */
    public void setDIRECCIONDISTINTAADQUIRIENTE(DIRECCIONDISTINTAADQUIRIENTE value) {
        this.direcciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad estado620.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESTADO620() {
        return estado620;
    }

    /**
     * Define el valor de la propiedad estado620.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESTADO620(String value) {
        this.estado620 = value;
    }

    /**
     * Obtiene el valor de la propiedad reduccioncodigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREDUCCIONCODIGO() {
        return reduccioncodigo;
    }

    /**
     * Define el valor de la propiedad reduccioncodigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREDUCCIONCODIGO(String value) {
        this.reduccioncodigo = value;
    }

    /**
     * Obtiene el valor de la propiedad tipomotor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOMOTOR() {
        return tipomotor;
    }

    /**
     * Define el valor de la propiedad tipomotor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOMOTOR(String value) {
        this.tipomotor = value;
    }

    /**
     * Obtiene el valor de la propiedad procedencia620.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROCEDENCIA620() {
        return procedencia620;
    }

    /**
     * Define el valor de la propiedad procedencia620.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROCEDENCIA620(String value) {
        this.procedencia620 = value;
    }

    /**
     * Obtiene el valor de la propiedad subasta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBASTA() {
        return subasta;
    }

    /**
     * Define el valor de la propiedad subasta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBASTA(String value) {
        this.subasta = value;
    }

}
