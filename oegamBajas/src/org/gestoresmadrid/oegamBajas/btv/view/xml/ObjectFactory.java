package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlRegistry;

import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Solicitante;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Titular;


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
     * Create an instance of {@link DatosFirmados }
     * 
     */
    public DatosFirmados createDatosFirmados() {
        return new DatosFirmados();
    }

    /**
     * Create an instance of {@link SolicitudRegistroEntrada }
     * 
     */
    public SolicitudRegistroEntrada createSolicitudRegistroEntrada() {
        return new SolicitudRegistroEntrada();
    }
    
    /**
     * Create an instance of {@link DatosGenericos }
     * 
     */
    public DatosGenericos createDatosGenericos() {
        return new DatosGenericos();
    }
    
    /**
     * Create an instance of {@link DatosRemitente }
     * 
     */
    public DatosRemitente createDatosRemitente() {
        return new DatosRemitente();
    }

    
    /**
     * Create an instance of {@link DatosInteresado }
     * 
     */
    public DatosInteresado createDatosInteresado() {
        return new DatosInteresado();
    }
    
    /**
     * Create an instance of {@link Asunto }
     * 
     */
    public Asunto createAsunto() {
    	Asunto asunto = new Asunto();
    	asunto.setCodigoAsunto(CodigoAsunto.BTETC.value());
    	asunto.setDescripcionAsunto(DescripcionAsunto.DESCRIPCION.value());
        return asunto;
    }
    
    /**
     * Create an instance of {@link Destino }
     * 
     */
    public Destino createDestino() {
    	Destino destino = new Destino();
    	destino.setCodigoDestino(CodigoDestino.CODIGO.value());
    	destino.setDescripcionDestino(DescripcionDestino.DESCRIPCION.value());
        return destino;
    }
    
    /**
     * Create an instance of {@link DatosEspecificos }
     * 
     */
    public DatosEspecificos createDatosEspecificos() {
        return new DatosEspecificos();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.DatosColegio }
     * 
     */
    public DatosEspecificos.DatosColegio createDatosEspecificosDatosColegio() {
        return new DatosEspecificos.DatosColegio();
    }

    
    /**
     * Create an instance of {@link DatosEspecificos.DatosGestoria }
     * 
     */
    public DatosEspecificos.DatosGestoria createDatosEspecificosDatosGestoria() {
        return new DatosEspecificos.DatosGestoria();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.DatosGestor }
     * 
     */
    public DatosEspecificos.DatosGestor createDatosEspecificosDatosGestor() {
        return new DatosEspecificos.DatosGestor();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.DatosExpediente }
     * 
     */
    public DatosEspecificos.DatosExpediente createDatosEspecificosDatosExpediente() {
        return new DatosEspecificos.DatosExpediente();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.Tasas }
     * 
     */
    public DatosEspecificos.Tasas createDatosEspecificosTasas() {
        return new DatosEspecificos.Tasas();
    }
    
    /**
     * Create an instance of {@link DatosVehiculo }
     * 
     */
    public DatosVehiculo createDatosVehiculo() {
        return new DatosVehiculo();
    }

    
    /**
     * Create an instance of {@link Titular }
     * 
     */
    public DatosEspecificos.Titular createDatosTitular() {
        return new DatosEspecificos.Titular();
    }
    
    /**
     * Create an instance of {@link Solicitante }
     * 
     */
    public DatosEspecificos.Solicitante createDatosSolicitante() {
        return new DatosEspecificos.Solicitante();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.Indicadores }
     * 
     */
    public DatosEspecificos.Indicadores createDatosEspecificosIndicadores() {
        return new DatosEspecificos.Indicadores();
    }
    
    /**
     * Create an instance of {@link DatosInteresado.Interesado }
     * 
     */
    public DatosInteresado.Interesado createInteresado(){
    	return new  DatosInteresado.Interesado();
    }
    
    /**
     * Create an instance of {@link DatosEspecificos.DatosRepresentante }
     * 
     */
    public DatosEspecificos.DatosRepresentante createDatosRepresentante(){
    	return new DatosEspecificos.DatosRepresentante();
    }
    
    /**
     * Create an instance of {@link DatosVehiculo.DatosMatriculacion }
     * 
     */
    public DatosVehiculo.DatosMatriculacion createDatosMatriculacion(){
    	return new DatosVehiculo.DatosMatriculacion();
    }
    
    /**
     * Create an instance of {@link DatosInteresado.Interesado.DocumentoIdentificacion }
     * 
     */
    public DatosInteresado.Interesado.DocumentoIdentificacion createDocumentoIdentificacionInteresado(){
    	return new DatosInteresado.Interesado.DocumentoIdentificacion();
    }
    
    /**
     * Create an instance of {@link DatosRemitente.DocumentoIdentificacion }
     * 
     */
    public DatosRemitente.DocumentoIdentificacion createDocumentoIdentificacionRemitente(){
    	return new DatosRemitente.DocumentoIdentificacion();
    }
}
