
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.respuesta;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.OutInformeCompleto;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.trafico.servicios.vehiculos.comunicaciones.webservices.informecompleto.respuesta package. 
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

    private final static QName _ResultadoInformeCompleto_QNAME = new QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto/respuesta", "ResultadoInformeCompleto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.trafico.servicios.vehiculos.comunicaciones.webservices.informecompleto.respuesta
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutInformeCompleto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto/respuesta", name = "ResultadoInformeCompleto")
    public JAXBElement<OutInformeCompleto> createResultadoInformeCompleto(OutInformeCompleto value) {
        return new JAXBElement<OutInformeCompleto>(_ResultadoInformeCompleto_QNAME, OutInformeCompleto.class, null, value);
    }

}
