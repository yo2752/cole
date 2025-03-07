//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 11:05:31 AM CEST 
//


package org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datosTramites complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datosTramites">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaBajas" type="{}bajas" minOccurs="0"/>
 *         &lt;element name="listaDuplicados" type="{}duplicados" minOccurs="0"/>
 *         &lt;element name="listaProrrogas" type="{}prorrogas" minOccurs="0"/>
 *         &lt;element name="listaRematriculaciones" type="{}rematriculaciones" minOccurs="0"/>
 *         &lt;element name="listaTransferencias" type="{}transferencias" minOccurs="0"/>
 *         &lt;element name="matriculacionTemporal" type="{}matriculacionTemporal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosTramites", propOrder = {
    "listaBajas",
    "listaDuplicados",
    "listaProrrogas",
    "listaRematriculaciones",
    "listaTransferencias",
    "matriculacionTemporal"
})
public class DatosTramites {

    @XmlElementRef(name = "listaBajas", type = JAXBElement.class, required = false)
    protected JAXBElement<Bajas> listaBajas;
    @XmlElementRef(name = "listaDuplicados", type = JAXBElement.class, required = false)
    protected JAXBElement<Duplicados> listaDuplicados;
    @XmlElementRef(name = "listaProrrogas", type = JAXBElement.class, required = false)
    protected JAXBElement<Prorrogas> listaProrrogas;
    @XmlElementRef(name = "listaRematriculaciones", type = JAXBElement.class, required = false)
    protected JAXBElement<Rematriculaciones> listaRematriculaciones;
    @XmlElementRef(name = "listaTransferencias", type = JAXBElement.class, required = false)
    protected JAXBElement<Transferencias> listaTransferencias;
    protected MatriculacionTemporal matriculacionTemporal;

    /**
     * Gets the value of the listaBajas property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Bajas }{@code >}
     *     
     */
    public JAXBElement<Bajas> getListaBajas() {
        return listaBajas;
    }

    /**
     * Sets the value of the listaBajas property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Bajas }{@code >}
     *     
     */
    public void setListaBajas(JAXBElement<Bajas> value) {
        this.listaBajas = value;
    }

    /**
     * Gets the value of the listaDuplicados property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Duplicados }{@code >}
     *     
     */
    public JAXBElement<Duplicados> getListaDuplicados() {
        return listaDuplicados;
    }

    /**
     * Sets the value of the listaDuplicados property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Duplicados }{@code >}
     *     
     */
    public void setListaDuplicados(JAXBElement<Duplicados> value) {
        this.listaDuplicados = value;
    }

    /**
     * Gets the value of the listaProrrogas property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Prorrogas }{@code >}
     *     
     */
    public JAXBElement<Prorrogas> getListaProrrogas() {
        return listaProrrogas;
    }

    /**
     * Sets the value of the listaProrrogas property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Prorrogas }{@code >}
     *     
     */
    public void setListaProrrogas(JAXBElement<Prorrogas> value) {
        this.listaProrrogas = value;
    }

    /**
     * Gets the value of the listaRematriculaciones property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Rematriculaciones }{@code >}
     *     
     */
    public JAXBElement<Rematriculaciones> getListaRematriculaciones() {
        return listaRematriculaciones;
    }

    /**
     * Sets the value of the listaRematriculaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Rematriculaciones }{@code >}
     *     
     */
    public void setListaRematriculaciones(JAXBElement<Rematriculaciones> value) {
        this.listaRematriculaciones = value;
    }

    /**
     * Gets the value of the listaTransferencias property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Transferencias }{@code >}
     *     
     */
    public JAXBElement<Transferencias> getListaTransferencias() {
        return listaTransferencias;
    }

    /**
     * Sets the value of the listaTransferencias property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Transferencias }{@code >}
     *     
     */
    public void setListaTransferencias(JAXBElement<Transferencias> value) {
        this.listaTransferencias = value;
    }

    /**
     * Gets the value of the matriculacionTemporal property.
     * 
     * @return
     *     possible object is
     *     {@link MatriculacionTemporal }
     *     
     */
    public MatriculacionTemporal getMatriculacionTemporal() {
        return matriculacionTemporal;
    }

    /**
     * Sets the value of the matriculacionTemporal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatriculacionTemporal }
     *     
     */
    public void setMatriculacionTemporal(MatriculacionTemporal value) {
        this.matriculacionTemporal = value;
    }

}
