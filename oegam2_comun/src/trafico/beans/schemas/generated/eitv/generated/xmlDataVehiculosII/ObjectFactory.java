//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.13 at 02:30:57 PM CET 
//


package trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculosII;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Trafico_QNAME = new QName("", "trafico");
    private final static QName _Opciones_QNAME = new QName("", "opciones");
    private final static QName _Caracteristicas_QNAME = new QName("", "caracteristicas");
    private final static QName _Observaciones_QNAME = new QName("", "observaciones");
    private final static QName _Comunidad_QNAME = new QName("", "comunidad");
    private final static QName _Fabricante_QNAME = new QName("", "fabricante");
    private final static QName _General_QNAME = new QName("", "general");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TipoFabricante }
     * 
     */
    public TipoFabricante createTipoFabricante() {
        return new TipoFabricante();
    }

    /**
     * Create an instance of {@link TipoObservaciones }
     * 
     */
    public TipoObservaciones createTipoObservaciones() {
        return new TipoObservaciones();
    }

    /**
     * Create an instance of {@link TipoGeneral }
     * 
     */
    public TipoGeneral createTipoGeneral() {
        return new TipoGeneral();
    }

    /**
     * Create an instance of {@link TipoComunidad }
     * 
     */
    public TipoComunidad createTipoComunidad() {
        return new TipoComunidad();
    }

    /**
     * Create an instance of {@link Vehiculo }
     * 
     */
    public Vehiculo createVehiculo() {
        return new Vehiculo();
    }

    /**
     * Create an instance of {@link TipoTrafico }
     * 
     */
    public TipoTrafico createTipoTrafico() {
        return new TipoTrafico();
    }

    /**
     * Create an instance of {@link TipoCaracteristicas }
     * 
     */
    public TipoCaracteristicas createTipoCaracteristicas() {
        return new TipoCaracteristicas();
    }

    /**
     * Create an instance of {@link TipoOpciones }
     * 
     */
    public TipoOpciones createTipoOpciones() {
        return new TipoOpciones();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoTrafico }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "trafico")
    public JAXBElement<TipoTrafico> createTrafico(TipoTrafico value) {
        return new JAXBElement<TipoTrafico>(_Trafico_QNAME, TipoTrafico.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoOpciones }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "opciones")
    public JAXBElement<TipoOpciones> createOpciones(TipoOpciones value) {
        return new JAXBElement<TipoOpciones>(_Opciones_QNAME, TipoOpciones.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoCaracteristicas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "caracteristicas")
    public JAXBElement<TipoCaracteristicas> createCaracteristicas(TipoCaracteristicas value) {
        return new JAXBElement<TipoCaracteristicas>(_Caracteristicas_QNAME, TipoCaracteristicas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoObservaciones }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "observaciones")
    public JAXBElement<TipoObservaciones> createObservaciones(TipoObservaciones value) {
        return new JAXBElement<TipoObservaciones>(_Observaciones_QNAME, TipoObservaciones.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoComunidad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comunidad")
    public JAXBElement<TipoComunidad> createComunidad(TipoComunidad value) {
        return new JAXBElement<TipoComunidad>(_Comunidad_QNAME, TipoComunidad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoFabricante }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fabricante")
    public JAXBElement<TipoFabricante> createFabricante(TipoFabricante value) {
        return new JAXBElement<TipoFabricante>(_Fabricante_QNAME, TipoFabricante.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoGeneral }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "general")
    public JAXBElement<TipoGeneral> createGeneral(TipoGeneral value) {
        return new JAXBElement<TipoGeneral>(_General_QNAME, TipoGeneral.class, null, value);
    }

}
