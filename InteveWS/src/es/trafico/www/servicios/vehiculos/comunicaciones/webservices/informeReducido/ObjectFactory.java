
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido package. 
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

    private final static QName _ObtenerInformeReducido_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido", "obtenerInformeReducido");
    private final static QName _ObtenerInformeReducidoResponse_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido", "obtenerInformeReducidoResponse");
    private final static QName _ResultadoInformeReducido_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido/respuesta", "ResultadoInformeReducido");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerInformeReducido }
     * 
     */
    public ObtenerInformeReducido createObtenerInformeReducido() {
        return new ObtenerInformeReducido();
    }

    /**
     * Create an instance of {@link ObtenerInformeReducidoResponse }
     * 
     */
    public ObtenerInformeReducidoResponse createObtenerInformeReducidoResponse() {
        return new ObtenerInformeReducidoResponse();
    }

    /**
     * Create an instance of {@link InInformeReducido }
     * 
     */
    public InInformeReducido createInInformeReducido() {
        return new InInformeReducido();
    }

    /**
     * Create an instance of {@link OutInformeReducido }
     * 
     */
    public OutInformeReducido createOutInformeReducido() {
        return new OutInformeReducido();
    }

    /**
     * Create an instance of {@link ResultadoComunicacion }
     * 
     */
    public ResultadoComunicacion createResultadoComunicacion() {
        return new ResultadoComunicacion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInformeReducido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido", name = "obtenerInformeReducido")
    public JAXBElement<ObtenerInformeReducido> createObtenerInformeReducido(ObtenerInformeReducido value) {
        return new JAXBElement<ObtenerInformeReducido>(_ObtenerInformeReducido_QNAME, ObtenerInformeReducido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInformeReducidoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido", name = "obtenerInformeReducidoResponse")
    public JAXBElement<ObtenerInformeReducidoResponse> createObtenerInformeReducidoResponse(ObtenerInformeReducidoResponse value) {
        return new JAXBElement<ObtenerInformeReducidoResponse>(_ObtenerInformeReducidoResponse_QNAME, ObtenerInformeReducidoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutInformeReducido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido/respuesta", name = "ResultadoInformeReducido")
    public JAXBElement<OutInformeReducido> createResultadoInformeReducido(OutInformeReducido value) {
        return new JAXBElement<OutInformeReducido>(_ResultadoInformeReducido_QNAME, OutInformeReducido.class, null, value);
    }

}
