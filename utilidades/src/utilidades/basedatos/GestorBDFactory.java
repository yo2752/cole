package utilidades.basedatos;


import javax.naming.NamingException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.GestorFicherosPropiedades;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;


/**
 * 
 * @author César Sánchez 
 * Debe leer archivos de properties de base de datos una sola vez, para generar objetos tipo GestorBD para cada proyecto
 *
 */
public class GestorBDFactory {

	private static GestorBDFactory instance=null; 
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorBDFactory.class);
	//atributos para cargar los valores de los properties

	//atributos Bean que almacenarán los valores de los properties. 
	private ConexionBDBean conexionBDBean; //genérico	


	public static GestorBDFactory getInstance()  {
		log.trace("getInstance");
		if (instance == null)
			try {
				instance = new GestorBDFactory();
			} catch (NamingException e) {				
				log.error(e);
			} catch (OegamExcepcion e) {				
				log.error(e);
			}
			log.trace("devolvemos instancia de GestorBDFactory");		
			return instance;
	}	


	/**
	 * Se ejecuta la primera vez que se solicita una instancia de la clase. 
	 * Nos permite tener en memoria los valores de los properties de acceso a base de datos, para poder generar objetos tipo GestorBD.
	 * 
	 * @throws OegamExcepcion
	 * @throws NamingException
	 */

	public GestorBDFactory() throws OegamExcepcion, NamingException {
		log.trace("creación GestorBDFactory");

		try {

			GestorFicherosPropiedades gestorBDProp = new GestorFicherosPropiedades(ConstantesBD.RUTA_BD_PROPERTIES);
			if(gestorBDProp.getFicheroPropiedades()!=null){
				setConexionBDBean(new ConexionBDBean(gestorBDProp));
			}
			log.trace("properties de base de datos cargados"); 	
		} catch (OegamExcepcion e) {
			throw new OegamExcepcion(EnumError.error_00027,e.getMessage());
		}	

	}
	
	public GestorBD getGestorBDGenerico() throws OegamExcepcion
	{		log.trace("damos gestorBDGenerico"); 	
			return new GestorBD(conexionBDBean);
	}

	private void setConexionBDBean(ConexionBDBean conexionBDBean) {
		this.conexionBDBean = conexionBDBean;
	}		
	
}
	
	
	

