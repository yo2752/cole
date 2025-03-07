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
 *         &lt;element ref="{}NUMERO_BASTIDOR" minOccurs="0"/>
 *         &lt;element ref="{}MARCA"/>
 *         &lt;element ref="{}MODELO"/>
 *         &lt;element ref="{}FECHA_MATRICULACION"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_ITV"/>
 *         &lt;element ref="{}SERVICIO_DESTINO"/>
 *         &lt;element ref="{}MODO_ADJUDICACION"/>
 *         &lt;element ref="{}TIPO_TRANSFERENCIA"/>
 *         &lt;element ref="{}MOTIVO_ITV"/>
 *         &lt;element ref="{}CONCEPTO_ITV"/>
 *         &lt;element ref="{}FECHA_ITV"/>
 *         &lt;element ref="{}ESTACION_ITV"/>
 *         &lt;element ref="{}CALLE_DIRECCION_VEHICULO"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_VEHICULO"/>
 *         &lt;element ref="{}PROVINCIA_VEHICULO"/>
 *         &lt;element ref="{}PUEBLO_VEHICULO"/>
 *         &lt;element ref="{}DECLARACION_RESPONSABILIDAD"/>
 *         &lt;element ref="{}TIPO_ID_VEHICULO" minOccurs="0"/>
 *         &lt;element ref="{}SERVICIO_ANTERIOR" minOccurs="0"/>
 *         &lt;element ref="{}SERVICIO" minOccurs="0"/>
 *         &lt;element ref="{}VEHICULO_AGRICOLA" minOccurs="0"/>
 *         &lt;element ref="{}TARA" minOccurs="0"/>
 *         &lt;element ref="{}PESO_MMA" minOccurs="0"/>
 *         &lt;element ref="{}PLAZAS" minOccurs="0"/>
 *         &lt;element ref="{}KILOMETRAJE" minOccurs="0"/>
 *         &lt;element ref="{}CHECK_CADUCIDAD_ITV" minOccurs="0"/>
 *         &lt;element ref="{}PROVINCIA_PRIMERA_MATRICULA" minOccurs="0"/>
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
@XmlRootElement(name = "DATOS_VEHICULO")
public class DATOSVEHICULO {

    @XmlElement(name = "NUMERO_BASTIDOR")
    protected String numerobastidor;
    @XmlElement(name = "MARCA", required = true)
    protected String marca;
    @XmlElement(name = "MODELO", required = true)
    protected String modelo;
    @XmlElement(name = "FECHA_MATRICULACION", required = true)
    protected String fechamatriculacion;
    @XmlElement(name = "FECHA_CADUCIDAD_ITV", required = true)
    protected String fechacaducidaditv;
    @XmlElement(name = "SERVICIO_DESTINO", required = true)
    protected String serviciodestino;
    @XmlElement(name = "MODO_ADJUDICACION", required = true)
    protected String modoadjudicacion;
    @XmlElement(name = "TIPO_TRANSFERENCIA", required = true)
    protected String tipotransferencia;
    @XmlElement(name = "MOTIVO_ITV", required = true)
    protected String motivoitv;
    @XmlElement(name = "CONCEPTO_ITV", required = true)
    protected String conceptoitv;
    @XmlElement(name = "FECHA_ITV", required = true)
    protected String fechaitv;
    @XmlElement(name = "ESTACION_ITV", required = true)
    protected String estacionitv;
    @XmlElement(name = "CALLE_DIRECCION_VEHICULO", required = true)
    protected String calledireccionvehiculo;
    @XmlElement(name = "NUMERO_DIRECCION_VEHICULO", required = true)
    protected String numerodireccionvehiculo;
    @XmlElement(name = "PROVINCIA_VEHICULO", required = true)
    protected String provinciavehiculo;
    @XmlElement(name = "PUEBLO_VEHICULO", required = true)
    protected String pueblovehiculo;
    @XmlElement(name = "DECLARACION_RESPONSABILIDAD", required = true)
    protected String declaracionresponsabilidad;
    @XmlElement(name = "TIPO_ID_VEHICULO")
    protected String tipoidvehiculo;
    @XmlElement(name = "SERVICIO_ANTERIOR")
    protected String servicioanterior;
    @XmlElement(name = "SERVICIO")
    protected String servicio;
    @XmlElement(name = "VEHICULO_AGRICOLA")
    protected String vehiculoagricola;
    @XmlElement(name = "TARA")
    protected String tara;
    @XmlElement(name = "PESO_MMA")
    protected String pesomma;
    @XmlElement(name = "PLAZAS")
    protected String plazas;
    @XmlElement(name = "KILOMETRAJE")
    protected String kilometraje;
    @XmlElement(name = "CHECK_CADUCIDAD_ITV")
    protected String checkcaducidaditv;
    @XmlElement(name = "PROVINCIA_PRIMERA_MATRICULA")
    protected String provinciaprimeramatricula;

    /**
     * Obtiene el valor de la propiedad numerobastidor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROBASTIDOR() {
        return numerobastidor;
    }

    /**
     * Define el valor de la propiedad numerobastidor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROBASTIDOR(String value) {
        this.numerobastidor = value;
    }

    /**
     * Obtiene el valor de la propiedad marca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARCA() {
        return marca;
    }

    /**
     * Define el valor de la propiedad marca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARCA(String value) {
        this.marca = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELO() {
        return modelo;
    }

    /**
     * Define el valor de la propiedad modelo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELO(String value) {
        this.modelo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechamatriculacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAMATRICULACION() {
        return fechamatriculacion;
    }

    /**
     * Define el valor de la propiedad fechamatriculacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAMATRICULACION(String value) {
        this.fechamatriculacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaditv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADITV() {
        return fechacaducidaditv;
    }

    /**
     * Define el valor de la propiedad fechacaducidaditv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADITV(String value) {
        this.fechacaducidaditv = value;
    }

    /**
     * Obtiene el valor de la propiedad serviciodestino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICIODESTINO() {
        return serviciodestino;
    }

    /**
     * Define el valor de la propiedad serviciodestino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICIODESTINO(String value) {
        this.serviciodestino = value;
    }

    /**
     * Obtiene el valor de la propiedad modoadjudicacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODOADJUDICACION() {
        return modoadjudicacion;
    }

    /**
     * Define el valor de la propiedad modoadjudicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODOADJUDICACION(String value) {
        this.modoadjudicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipotransferencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOTRANSFERENCIA() {
        return tipotransferencia;
    }

    /**
     * Define el valor de la propiedad tipotransferencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOTRANSFERENCIA(String value) {
        this.tipotransferencia = value;
    }

    /**
     * Obtiene el valor de la propiedad motivoitv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVOITV() {
        return motivoitv;
    }

    /**
     * Define el valor de la propiedad motivoitv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVOITV(String value) {
        this.motivoitv = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptoitv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTOITV() {
        return conceptoitv;
    }

    /**
     * Define el valor de la propiedad conceptoitv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTOITV(String value) {
        this.conceptoitv = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaitv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAITV() {
        return fechaitv;
    }

    /**
     * Define el valor de la propiedad fechaitv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAITV(String value) {
        this.fechaitv = value;
    }

    /**
     * Obtiene el valor de la propiedad estacionitv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESTACIONITV() {
        return estacionitv;
    }

    /**
     * Define el valor de la propiedad estacionitv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESTACIONITV(String value) {
        this.estacionitv = value;
    }

    /**
     * Obtiene el valor de la propiedad calledireccionvehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCALLEDIRECCIONVEHICULO() {
        return calledireccionvehiculo;
    }

    /**
     * Define el valor de la propiedad calledireccionvehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCALLEDIRECCIONVEHICULO(String value) {
        this.calledireccionvehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccionvehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONVEHICULO() {
        return numerodireccionvehiculo;
    }

    /**
     * Define el valor de la propiedad numerodireccionvehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONVEHICULO(String value) {
        this.numerodireccionvehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciavehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAVEHICULO() {
        return provinciavehiculo;
    }

    /**
     * Define el valor de la propiedad provinciavehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAVEHICULO(String value) {
        this.provinciavehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblovehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLOVEHICULO() {
        return pueblovehiculo;
    }

    /**
     * Define el valor de la propiedad pueblovehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLOVEHICULO(String value) {
        this.pueblovehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad declaracionresponsabilidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDECLARACIONRESPONSABILIDAD() {
        return declaracionresponsabilidad;
    }

    /**
     * Define el valor de la propiedad declaracionresponsabilidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDECLARACIONRESPONSABILIDAD(String value) {
        this.declaracionresponsabilidad = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoidvehiculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOIDVEHICULO() {
        return tipoidvehiculo;
    }

    /**
     * Define el valor de la propiedad tipoidvehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOIDVEHICULO(String value) {
        this.tipoidvehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad servicioanterior.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICIOANTERIOR() {
        return servicioanterior;
    }

    /**
     * Define el valor de la propiedad servicioanterior.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICIOANTERIOR(String value) {
        this.servicioanterior = value;
    }

    /**
     * Obtiene el valor de la propiedad servicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICIO() {
        return servicio;
    }

    /**
     * Define el valor de la propiedad servicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICIO(String value) {
        this.servicio = value;
    }

    /**
     * Obtiene el valor de la propiedad vehiculoagricola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVEHICULOAGRICOLA() {
        return vehiculoagricola;
    }

    /**
     * Define el valor de la propiedad vehiculoagricola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVEHICULOAGRICOLA(String value) {
        this.vehiculoagricola = value;
    }

    /**
     * Obtiene el valor de la propiedad tara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARA() {
        return tara;
    }

    /**
     * Define el valor de la propiedad tara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARA(String value) {
        this.tara = value;
    }

    /**
     * Obtiene el valor de la propiedad pesomma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPESOMMA() {
        return pesomma;
    }

    /**
     * Define el valor de la propiedad pesomma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPESOMMA(String value) {
        this.pesomma = value;
    }

    /**
     * Obtiene el valor de la propiedad plazas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLAZAS() {
        return plazas;
    }

    /**
     * Define el valor de la propiedad plazas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLAZAS(String value) {
        this.plazas = value;
    }

    /**
     * Obtiene el valor de la propiedad kilometraje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKILOMETRAJE() {
        return kilometraje;
    }

    /**
     * Define el valor de la propiedad kilometraje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKILOMETRAJE(String value) {
        this.kilometraje = value;
    }

    /**
     * Obtiene el valor de la propiedad checkcaducidaditv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHECKCADUCIDADITV() {
        return checkcaducidaditv;
    }

    /**
     * Define el valor de la propiedad checkcaducidaditv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHECKCADUCIDADITV(String value) {
        this.checkcaducidaditv = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciaprimeramatricula.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAPRIMERAMATRICULA() {
        return provinciaprimeramatricula;
    }

    /**
     * Define el valor de la propiedad provinciaprimeramatricula.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAPRIMERAMATRICULA(String value) {
        this.provinciaprimeramatricula = value;
    }

}
