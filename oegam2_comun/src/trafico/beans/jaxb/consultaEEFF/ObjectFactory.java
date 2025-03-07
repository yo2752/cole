package trafico.beans.jaxb.consultaEEFF;

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

	public ObjectFactory() {
    }
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF }
	 */
	public RespuestaEEFF createRespuestaEEFF(){
		return new RespuestaEEFF();
	}
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF.datosHistoricosITV }
	 */
	public RespuestaEEFF.datossimpleeitv.datosHistoricosITV createRespuestaEEFFDatosHistoricosITV(){
		return new RespuestaEEFF.datossimpleeitv.datosHistoricosITV();
	}
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF.datossimpleeitv }
	 */
	public RespuestaEEFF.datossimpleeitv.baseeitvdto createRespuestaBaseeitvdto(){
		return new RespuestaEEFF.datossimpleeitv.baseeitvdto();		
	}
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF.direccionCliente }
	 */
	public RespuestaEEFF.datossimpleeitv.direccionCliente createRespuestaEEFFDireccionCliente(){
		return new RespuestaEEFF.datossimpleeitv.direccionCliente();
	}
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF.nombreApellidosClienteFinal }
	 */
	public RespuestaEEFF.datossimpleeitv.nombreApellidosClienteFinal createRespuestaEEFFApellidosClienteFinal(){
		return new RespuestaEEFF.datossimpleeitv.nombreApellidosClienteFinal();
	}
	
	/**
	 * 
	 * Create an instance of {@link RespuestaEEFF.accionesEITV }
	 */
	public RespuestaEEFF.accionesEITV createRespuestaEEFFAccionesEITV(){
		return new RespuestaEEFF.accionesEITV();
	}
	
	/**
	 * 
	 *  Create an instance of {@link RespuestaEEFF.infoErrores }
	 */
	public RespuestaEEFF.infoErrores createRespuestaEEFFInfoErrores(){
		return new RespuestaEEFF.infoErrores();
	}
	
	/**
	 * 
	 *  Create an instance of {@link RespuestaEEFF.infoErrorDTO }
	 */
	public RespuestaEEFF.infoErrores.infoErrorDTO createRespuestaEEFFInfoErroresInfoErrorDTO(){
		return new RespuestaEEFF.infoErrores.infoErrorDTO();
	}
	
}
