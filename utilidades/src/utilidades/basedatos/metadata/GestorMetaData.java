package utilidades.basedatos.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import utilidades.basedatos.GestorBD;
import utilidades.basedatos.GestorBDFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class GestorMetaData {

	private static final String LONGITUD = "longitud";

	private static final String SCALE = "scale";

	private static final String PRECISION = "Precision";

	private static final String P_INFORMACION = "P_INFORMACION";

	private static final String P_SQLERRM = "P_SQLERRM";

	private static final String P_CODE = "P_CODE";

	private static final String INICIO_GET_LISTA_PARAMETROS = "Inicio getListaParametros";

	private static final String INICIO_DE_OBTENCION_DE_NOMBRES_DE_PARAMETROS_DEL_PROC = "inicio de obtencion de nombres de parametros del proc";

	private static final String FIN_DE_OBTENCION_DE_NOMBRES_DE_PARAMETROS_DEL_PROC = "fin de obtencion de nombres de parametros del proc";

	private static final String FIN_GET_LISTA_PARAMETROS = "Fin getListaParametros";

	private static final String INICIO_GET_INFO_PARAMETRO = "inicio getInfoParametro";

	private static final String INICIO_OBTENCION_DE_LA_INFO_DE_LOS_PARAMETROS_DEL_PROC = "inicio obtencion de la info de los parametros del proc  ";

	private static final String FIN_GET_INFO_PARAMETRO = "fin getInfoParametro";

	private static final String FIN_OBTENCION_DE_LA_INFO_DE_LOS_PARAMETROS_DEL_PROC = "fin obtencion de la info de los parametros del proc  ";

	private static final String SQL_TYPE_NAME = "sqlTypeName";

	private static final String TIPO_IN_OUT_PARAMETRO = "tipoInOutParametro";

	private static final String NOMBRE_PARAMETRO = "nombreParametro";

	private static final String CREACION_DE_GESTOR_METADATA = "creacion de gestor metadata";

	private static final String DEVOLVEMOS_INSTANCIA_DE_GESTOR_META_DATA = "devolvemos instancia de GestorMetaData";

	private static final String GET_INSTANCE_GESTOR_META_DATA = "getInstance GestorMetaData";

	private static GestorMetaData instance;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorMetaData.class);

	private static HashMap<String, ArrayList<String>> mapaNombresParametros; // Almacena los nombres de los parametros
																				// de cada procedimiento.

	private static HashMap<String, HashMap<String, Object>> mapaInfoParametros; // Almacena la informacion de tipos de
																				// cada parametro.

	public static GestorMetaData getInstance() {
		log.debug(GET_INSTANCE_GESTOR_META_DATA);
		if (instance == null)
			instance = new GestorMetaData();
		log.trace(DEVOLVEMOS_INSTANCIA_DE_GESTOR_META_DATA);
		return instance;
	}

	/**
	 * Deberia ser leida de un fichero de texto encriptado.
	 */
	private GestorMetaData() {
		log.info(CREACION_DE_GESTOR_METADATA);
		mapaNombresParametros = new HashMap<String, ArrayList<String>>(); // cache de los nombres de los parametros de
																			// cada procedimiento.
		mapaInfoParametros = new HashMap<String, HashMap<String, Object>>(); // caché de la informacion del metadata de
																				// cada parámetro.

//		mapaNombresProcedures = new HashMap<String, BeanMetadataProcedure>();//nombres de todos los procedures del schema de la conexion, 
		// nombre,schema y catalog

//		listaTablas = new ArrayList<String>();
//		mapaNombresProcedures= getListaProcedures(); 
//		 ArrayList<String> catalogs = dameCatalogos(mapaNombresProcedures); 
//		listaTablas = getTables();
//		arrayInfoColumnasDeTablas = getInfoColumns(listaTablas);
//		 crearBeansPQ (catalogs, OEGAM2); 
	}

	/**
	 * 
	 * @param nombrePaquete
	 * @param schema
	 * @param nombreProcedure
	 * @param dbmd
	 * @return Un arrayList con la lista de nombres de los parametros de cada
	 *         Procedimiento.
	 */

	public ArrayList<String> getListaParametros(String nombrePaquete, String schema, String nombreProcedure,
			DatabaseMetaData dbmd) {
		log.debug(INICIO_GET_LISTA_PARAMETROS);
		String nombreListaParametros = nombrePaquete + schema + nombreProcedure;

		// Si no existe el arrayList de nombres va a buscarlo a BBDD.
		if (mapaNombresParametros.get(nombreListaParametros) == null) {
			ResultSet rsParametros = null;
			ArrayList<String> listaParametrosProc = new ArrayList<String>();
			Connection con = null;
			GestorBD gestorBD = null;
			try {
				log.debug(INICIO_DE_OBTENCION_DE_NOMBRES_DE_PARAMETROS_DEL_PROC);
				gestorBD = GestorBDFactory.getInstance().getGestorBDGenerico();
				con = gestorBD.getConexion();
				if (dbmd == null)
					dbmd = con.getMetaData();
				log.info("Conexion creado con " + dbmd.getURL() + "; Usuario: " + dbmd.getUserName());
				log.info("Recuperando argumentos de: " + schema + " - " + nombrePaquete + "." + nombreProcedure);
				rsParametros = dbmd.getProcedureColumns(nombrePaquete, schema, nombreProcedure, null);
				while (rsParametros.next()) {
					listaParametrosProc.add(rsParametros.getString(4));
					mapaNombresParametros.put(nombreListaParametros, listaParametrosProc);
				}
				rsParametros.close();
				log.debug(FIN_DE_OBTENCION_DE_NOMBRES_DE_PARAMETROS_DEL_PROC);
			} catch (SQLException e1) {
				log.error("Error al obtener lista de parametros", e1);
			} catch (OegamExcepcion e) {
				log.error("Error al obtener lista de parametros", e);
			} catch (Throwable e) {
				log.error(e);
			} finally {

				dbmd = null;
				gestorBD.cerrarConexion();
			}
		}
		log.debug(FIN_GET_LISTA_PARAMETROS);
		return (ArrayList<String>) mapaNombresParametros.get(nombreListaParametros);
	}

	public HashMap<String, Object> getInfoParametro(String nombrePaquete, String schema, String nombreProcedure,
			String nombreParametro, DatabaseMetaData dbmd) {
		log.debug(INICIO_GET_INFO_PARAMETRO);
		String nombreInfoParametros = nombrePaquete + schema + nombreProcedure + nombreParametro;

		// si no existe el arrayList de info va a buscarlo a BBDD.
		if (mapaInfoParametros.get(nombreInfoParametros) == null) {
			log.debug(INICIO_OBTENCION_DE_LA_INFO_DE_LOS_PARAMETROS_DEL_PROC + nombreProcedure);
			Connection con = null;
			ResultSet rsInfoParametro = null;
			GestorBD gestorBD = null;
			try {
				gestorBD = GestorBDFactory.getInstance().getGestorBDGenerico();
				con = gestorBD.getConexion();
				if (dbmd == null)
					dbmd = con.getMetaData();
				rsInfoParametro = dbmd.getProcedureColumns(nombrePaquete, schema, nombreProcedure, nombreParametro);
				// el rs contiene la informacion de ese parametro
				// recorremos el resultset para llenar el arrayList.
				while (rsInfoParametro.next()) {

					// recogemos los valores necesarios del rs con la informacion del parametro.
					nombreParametro = rsInfoParametro.getString(4);

					// si es uno de los parametros del beanPQGeneral, no lo incluimos en los
					// beanpqhijos
					if (!nombreParametro.equals(P_CODE) || !nombreParametro.equals(P_SQLERRM)
							|| !nombreParametro.equals(P_INFORMACION)) {
						int tipoInOutParametro = rsInfoParametro.getShort(5); // entrada o salida
						String sqlTypeName = rsInfoParametro.getString(7);
						int precision = rsInfoParametro.getInt(8);
						int longitud = rsInfoParametro.getInt(9);
						short scale = rsInfoParametro.getShort(10);
						HashMap<String, Object> mapaInfoParametro = new HashMap<String, Object>();
						mapaInfoParametro.put(NOMBRE_PARAMETRO, nombreParametro);
						mapaInfoParametro.put(TIPO_IN_OUT_PARAMETRO, tipoInOutParametro);
						mapaInfoParametro.put(SQL_TYPE_NAME, sqlTypeName);
						mapaInfoParametro.put(PRECISION, precision);
						mapaInfoParametro.put(LONGITUD, longitud);
						mapaInfoParametro.put(SCALE, scale);

						mapaInfoParametros.put(nombreInfoParametros, mapaInfoParametro);
					}
				}
				rsInfoParametro.close();
				log.debug(FIN_OBTENCION_DE_LA_INFO_DE_LOS_PARAMETROS_DEL_PROC + nombreProcedure);

			} catch (SQLException e) {
				log.error(e);
			} catch (OegamExcepcion e) {
				log.error(e);
			} catch (Throwable e) {
				log.error(e);
			} finally {
				dbmd = null;
				gestorBD.cerrarConexion();
			}
		}
		log.debug(FIN_GET_INFO_PARAMETRO);
		return mapaInfoParametros.get(nombreInfoParametros);

	}

}