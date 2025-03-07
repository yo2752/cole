
package es.iam.sbae.sbintopexterna.latest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.iam.sbae.sbintopexterna.latest package. 
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

    private final static QName _PeticionExterna_QNAME = new QName("http://sbintopexterna.sbae.iam.es", "peticionExterna");
    private final static QName _ResultadoPeticion_QNAME = new QName("http://sbintopexterna.sbae.iam.es", "resultadoPeticion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.iam.sbae.sbintopexterna.latest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PeticionExterna }
     * 
     */
    public PeticionExterna createPeticionExterna() {
        return new PeticionExterna();
    }

    /**
     * Create an instance of {@link RespuestaPeticion }
     * 
     */
    public RespuestaPeticion createRespuestaPeticion() {
        return new RespuestaPeticion();
    }

    /**
     * Create an instance of {@link ResultadoServicio }
     * 
     */
    public ResultadoServicio createResultadoServicio() {
        return new ResultadoServicio();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PeticionExterna }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sbintopexterna.sbae.iam.es", name = "peticionExterna")
    public JAXBElement<PeticionExterna> createPeticionExterna(PeticionExterna value) {
        return new JAXBElement<PeticionExterna>(_PeticionExterna_QNAME, PeticionExterna.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaPeticion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sbintopexterna.sbae.iam.es", name = "resultadoPeticion")
    public JAXBElement<RespuestaPeticion> createResultadoPeticion(RespuestaPeticion value) {
        return new JAXBElement<RespuestaPeticion>(_ResultadoPeticion_QNAME, RespuestaPeticion.class, null, value);
    }

}
