package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlRegistry;


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
	
	/**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }


    /**
     * Create an instance of {@link Declaracion }
     * 
     */
    public Declaracion createDeclaracion() {
        return new Declaracion();
    }

    /**
     * Create an instance of {@link SolicitudPresentacionModelos }
     * 
     */
    public SolicitudPresentacionModelos createSolicitudPresentacionModelos() {
        return new SolicitudPresentacionModelos();
    }
    
    /**
     * Create an instance of {@link Detalle }
     * 
     */
    public Detalle createDetalle() {
        return new Detalle();
    }
    
    /**
     * Create an instance of {@link Liquidacion }
     * 
     */
    public Liquidacion createLiquidacion() {
        return new Liquidacion();
    }
    
    /**
     * Create an instance of {@link BienUrbano }
     * 
     */
    public BienUrbano createBienesUrbanos() {
        return new BienUrbano();
    }
    
    
    /**
     * Create an instance of {@link BienRustico }
     * 
     */
    public BienRustico createBienesRusticos() {
        return new BienRustico();
    }
    
    /**
     * Create an instance of {@link Interviniente }
     * 
     */
    public Interviniente createIntervinientes() {
        return new Interviniente();
    }

}
