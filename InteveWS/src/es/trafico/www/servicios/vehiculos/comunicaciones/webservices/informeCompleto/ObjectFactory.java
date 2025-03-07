
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.trafico.servicios.vehiculos.comunicaciones.webservices.informecompleto package. 
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

    private final static QName _ObtenerInformeCompleto_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto", "obtenerInformeCompleto");
    private final static QName _ObtenerInformeCompletoResponse_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto", "obtenerInformeCompletoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.trafico.servicios.vehiculos.comunicaciones.webservices.informecompleto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerInformeCompleto }
     * 
     */
    public ObtenerInformeCompleto createObtenerInformeCompleto() {
        return new ObtenerInformeCompleto();
    }

    /**
     * Create an instance of {@link ObtenerInformeCompletoResponse }
     * 
     */
    public ObtenerInformeCompletoResponse createObtenerInformeCompletoResponse() {
        return new ObtenerInformeCompletoResponse();
    }

    /**
     * Create an instance of {@link InInformeCompleto }
     * 
     */
    public InInformeCompleto createInInformeCompleto() {
        return new InInformeCompleto();
    }

    /**
     * Create an instance of {@link OutInformeCompleto }
     * 
     */
    public OutInformeCompleto createOutInformeCompleto() {
        return new OutInformeCompleto();
    }

    /**
     * Create an instance of {@link ResultadoComunicacion }
     * 
     */
    public ResultadoComunicacion createResultadoComunicacion() {
        return new ResultadoComunicacion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInformeCompleto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto", name = "obtenerInformeCompleto")
    public JAXBElement<ObtenerInformeCompleto> createObtenerInformeCompleto(ObtenerInformeCompleto value) {
        return new JAXBElement<ObtenerInformeCompleto>(_ObtenerInformeCompleto_QNAME, ObtenerInformeCompleto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInformeCompletoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto", name = "obtenerInformeCompletoResponse")
    public JAXBElement<ObtenerInformeCompletoResponse> createObtenerInformeCompletoResponse(ObtenerInformeCompletoResponse value) {
        return new JAXBElement<ObtenerInformeCompletoResponse>(_ObtenerInformeCompletoResponse_QNAME, ObtenerInformeCompletoResponse.class, null, value);
    }

}
