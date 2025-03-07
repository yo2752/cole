package general.modelo;

import general.beans.RespuestaGenerica;
import general.utiles.ParametrosUtiles;

import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oegam.constantes.ConstantesPQ;
import oegam.constantes.NombresParametrosBusqueda;
import trafico.beans.daos.BeanPQGeneral;
import utilidades.basedatos.ConstantesTiposDatos;
import utilidades.basedatos.GestorBD;
import utilidades.basedatos.GestorBDFactory;
import utilidades.basedatos.ParametroProcedimientoAlmacenado;
import utilidades.basedatos.ParametroProcedimientoAlmacenado.EnumTipoUsoParametro;
import utilidades.basedatos.metadata.GestorMetaData;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

/**
 * 
 * @author 
 * Clase creada para automatizar la carga de parametros en la llamada a procedimientos almacenados desde los modelos.
 */
public class ModeloGenerico {
	private static final String SCALE = "scale";
	private static final String PROCEDIMIENTO_EJECUTADO = "procedimiento ejecutado";
	private static final String SQL_TYPE_NAME = "sqlTypeName";
	private static final String TIPO_IN_OUT_PARAMETRO = "tipoInOutParametro";
	private static final String LONGITUD = "longitud";
	private static final String NOMBRE_PARAMETRO = "nombreParametro";
	private static final String MAPA_PARAMETROS = "mapaParametros";
	private static final String LISTA_CURSOR = "listaCursor";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloGenerico.class);

	public String vari = null;

	public String getVari() {
		return vari;
	}

	public HashMap<String,Object> ejecutarGenericoParamObjectConCache(HashMap<String,Object>parametrosBusqueda, BeanPQGeneral beanGeneral){
		log.debug("Iniciar ejecutarParamObjectConCache");
		String nombrePaquete = "";
		String nombreProcedure = "";
		try{
			String schema = "";
			Class<?> claseBeanRecuperar = null;

			if (parametrosBusqueda!=null) {
				nombrePaquete =(String)parametrosBusqueda.get(NombresParametrosBusqueda.CATALOG);
				schema = (String)parametrosBusqueda.get(NombresParametrosBusqueda.SCHEMA);
				nombreProcedure = (String)parametrosBusqueda.get(NombresParametrosBusqueda.PROCEDURE);
				claseBeanRecuperar = (Class<?>)parametrosBusqueda.get(NombresParametrosBusqueda.CLASEBEANCURSOR);
			}

			GestorBD gestorBD = GestorBDFactory.getInstance().getGestorBDGenerico();

			ArrayList<String> listaParametros = dameListaParametrosCache(null,nombrePaquete,schema,nombreProcedure); //contiene la lista de los nombres de los parametros del proc almacenado
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros = new HashMap<Integer,ParametroProcedimientoAlmacenado>(); // Va guardando los ParametroProcedimientoAlmacenado
			BeanPQGeneral beanPQ = beanGeneral;
			// Para 1 campo del bean se corresponde 1 parámetro del procedimiento almacenado
			for (int i = 0; i < listaParametros.size(); i++) {
				HashMap<String,Object> mapaInfoParametro = dameInfoParametros(listaParametros, nombrePaquete, schema, nombreProcedure, listaParametros.get(i), null); {
					String nombreParametro = (String)mapaInfoParametro.get(NOMBRE_PARAMETRO);
					int tipoInOutParametro = Integer.parseInt(mapaInfoParametro.get(TIPO_IN_OUT_PARAMETRO).toString()); // Entrada o salida
					String sqlTypeName =(String) mapaInfoParametro.get(SQL_TYPE_NAME);
					int longitud = (Integer) mapaInfoParametro.get(LONGITUD);
					short scale = Short.parseShort(mapaInfoParametro.get(SCALE).toString());

					// Control de tipos
					int tipoSql = asignarTipoSql(sqlTypeName);

					Class<?> clase = ParametrosUtiles.dameClasedeParametro(tipoSql);
					ParametroProcedimientoAlmacenado pa;
					if(null!=clase && !"C_".startsWith(nombreParametro.toUpperCase())) {
						Object valor = ParametrosUtiles.dameValordeParametro(beanPQ, nombreParametro, tipoSql,nombrePaquete);

						EnumTipoUsoParametro tipoEntradaOSalida = determinaInOut(tipoInOutParametro);
						if (EnumTipoUsoParametro.OUT==tipoEntradaOSalida) valor=null;

						if (tipoSql==java.sql.Types.VARCHAR&&valor!=null) {
							valor = valor.toString().trim();
							if (valor.equals("") || "\n".equals(valor))
								valor=null;
						}

						valor = comprobarLongitudes(nombreParametro, longitud, scale, clase, valor);

						// Se carga el valor en parametroProcAlmacenado
						pa = new ParametroProcedimientoAlmacenado(
							nombreParametro,
							tipoSql, //para oracle
							clase, //tipo java
							!"".equals(valor)?valor:null, //valor del campo del bean
							tipoEntradaOSalida //enumerado
							);
					} else { // Si clase es null, es decir, si no existe un nombre de campo de bean que se corresponda con el parámetro del proc almacenado que hemos recuperado
						pa = new ParametroProcedimientoAlmacenado(
								nombreParametro,
								oracle.jdbc.OracleTypes.CURSOR
								);
					}
					mapaParametros.put(i+1, pa);
				} //fin de recorrer la info de los parametros
			} // Fin de recorrer array de nombres de campos

			// Ejecución normal del procedimiento almacenado.
			List<Object> listaResultados = new ArrayList<>();
			if(gestorBD!=null&&mapaParametros!=null) {
				listaResultados = gestorBD.ejecutarProcedimientoAlmacenado(
					nombrePaquete+"."+nombreProcedure,
					claseBeanRecuperar,
					mapaParametros, true);
			}
			log.debug(PROCEDIMIENTO_EJECUTADO);

			//Guardamos en la respuesta el mapa de parámetros, y la lista del cursor
			HashMap<String,Object>respuestas=new HashMap<String,Object>();
			respuestas.put(MAPA_PARAMETROS,generarHashMapNombreResultado(mapaParametros));
			respuestas.put(LISTA_CURSOR,listaResultados);
			mapaParametros.clear();
			return respuestas;
		} catch (OegamExcepcion e) {
			log.logarOegamExcepcion(Claves.CLAVE_LOG_MODELO_GENERICO_ERROR + e.getMessage(), e);
		} catch (Throwable e) {
			log.error("Procedimiento: " + nombreProcedure);
			log.error("Paquete: " + nombrePaquete);
			log.fatal(Claves.CLAVE_LOG_MODELO_GENERICO_ERROR + e.getMessage(), e);
		}

		return null;
	}

	private static Object comprobarLongitudes(String nombreParametro, int longitud, short scale, Class<?> clase, Object valor) {
		if (valor != null) {
			if (clase.equals(java.lang.String.class) && longitud>0 && valor.toString().length()>longitud) {
				/*
				 * En los tipos de campo cadena de longitud mayor de
				 * 100 (mensajes y respuestas), comprobamos que no
				 * se sobrepase la longitud maxima de la columna
				 */
				if (longitud > 100) {
					log.error("Valor del campo " + nombreParametro + " demasiado grande y se cortara. Valor original:\n" + valor);
					valor = valor.toString().substring(0, longitud - 1);
				} else {
					log.warn("Longitud de campo del bean " + nombreParametro + "=" + valor.toString().length() + ", excede la esperada en el parametro del proc = " + longitud);
				}
			}

			if (clase.equals(java.math.BigDecimal.class)) {
				BigDecimal auxvalor = (BigDecimal)valor;
				if (auxvalor.scale()!=scale) {
					log.warn("numero de decimales no encajan en " + nombreParametro);
				}
			}
		}
		return valor;
	}

	/**
	 * 
	 * @param dbmd
	 * @param nombrePaquete
	 * @param schema
	 * @param nombreProcedure
	 * @return un ArrayList con los nombres de los parametros de un procedimiento almacenado.
	 * @throws SQLException
	 */
	private ArrayList<String> dameListaParametrosCache(DatabaseMetaData dbmd, String nombrePaquete, String schema, String nombreProcedure) throws SQLException {
		return GestorMetaData.getInstance().getListaParametros(nombrePaquete, schema, nombreProcedure, dbmd);
	}

	/**
	 * 
	 * @param listaParametros
	 * @param nombrePaquete
	 * @param schema
	 * @param nombreProcedure
	 * @param nombreParametro
	 * @param dbmd
	 * @return un arrayList con 3 valores , nombre de parametro, tipo in/out y tipoSql
	 * @throws SQLException
	 */
	private HashMap<String,Object> dameInfoParametros(ArrayList<String> listaParametros,String nombrePaquete, String schema, String nombreProcedure, String nombreParametro, DatabaseMetaData dbmd) throws SQLException {
		return GestorMetaData.getInstance().getInfoParametro(nombrePaquete, schema, nombreProcedure, nombreParametro, dbmd);
	}

	private static int asignarTipoSql(String sqlTypeName) {
		int tipoSql = 0; // Almacena el type referente al parámetro de Oracle.

		// Asigna a partir del tipo que informa Oracle

		if (sqlTypeName.equals(ConstantesTiposDatos.UROWID))
			tipoSql = java.sql.Types.VARCHAR;

		if (sqlTypeName.equals(ConstantesTiposDatos.DATE))
			tipoSql = java.sql.Types.TIMESTAMP;

		if (sqlTypeName.equals(ConstantesTiposDatos.TIMESTAMP))
			tipoSql = java.sql.Types.TIMESTAMP;

		if (sqlTypeName.equals(ConstantesTiposDatos.NUMBER))
			tipoSql = java.sql.Types.NUMERIC;
 
		if (sqlTypeName.equals(ConstantesTiposDatos.CHAR))
			tipoSql = java.sql.Types.VARCHAR; 

		if (sqlTypeName.equals(ConstantesTiposDatos.VARCHAR))
			tipoSql = java.sql.Types.VARCHAR;

		if (sqlTypeName.equals(ConstantesTiposDatos.VARCHAR2))
			tipoSql = java.sql.Types.VARCHAR;

		if (sqlTypeName.equals(ConstantesTiposDatos.NVARCHAR))
			tipoSql = java.sql.Types.VARCHAR;

		if (sqlTypeName.equals(ConstantesTiposDatos.NVARCHAR2))
			tipoSql = java.sql.Types.VARCHAR;

		return tipoSql;
	}

	private static EnumTipoUsoParametro determinaInOut(int tipoInOutParametro) {
		// Determina si el parametro es de entrada, salida o entrada salida.
		EnumTipoUsoParametro tipoEntradaOSalida = null; // Si el parámetro será de entrada salida o entrada/salida

		switch (tipoInOutParametro) {
			case 1:
				tipoEntradaOSalida = EnumTipoUsoParametro.IN;
				break;
			case 2:
				tipoEntradaOSalida = EnumTipoUsoParametro.INOUT;
				break;
			case 4:
				tipoEntradaOSalida = EnumTipoUsoParametro.OUT;
				break;
			default:
				break;
		}
		return tipoEntradaOSalida;
	}

	public static HashMap<String,Object> generarHashMapNombreResultado(HashMap<Integer,ParametroProcedimientoAlmacenado> mapaI) {
		HashMap<String,Object> resultado = new HashMap<String,Object>();
		Iterator<?> it = mapaI.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			resultado.put(((ParametroProcedimientoAlmacenado)e.getValue()).getNombre(), ((ParametroProcedimientoAlmacenado)e.getValue()).getValor());
		}
		return resultado;
	}

	/**
	 * Nos devuelve un parámetro concreto de la respuesta del procedimiento, hay que hacerle el casting luego
	 * @param respuestaProcedimiento
	 * @param nombreParametro
	 * @return
	 */
	public static Object getParametroRespuesta(HashMap<String,Object> respuestaProcedimiento,String nombreParametro){
		return ((HashMap<String,Object>)respuestaProcedimiento.get(MAPA_PARAMETROS)).get(nombreParametro);
	}

	/**
	 * Nos devuelve la List<Object> de la respuesta del procedimiento almacenado
	 * @param respuestaProcedimiento
	 * @return
	 */
	public static List<Object> getCursorRespuesta(HashMap<String,Object> respuestaProcedimiento) {
		return (List<Object>)respuestaProcedimiento.get(LISTA_CURSOR);
	}

	/**
	 * Metodo que llama a cualquier procedimiento, y devuelve todos sus valores, y el cursor.
	 * @param beanGuardarVehiculo
	 * @return
	 */ 
	public RespuestaGenerica ejecutarProc(BeanPQGeneral bean,String schema,String catalog,String procedure,Class claseBeanCursor) {
		//log.info("Inicio ejecutarProc: " + schema + " " + catalog + " " + procedure); 
		//Completamos algunos parámetros para la llamada al procedimiento personalizada
		HashMap<String,Object> parametrosBusqueda = new HashMap<String,Object>();
		parametrosBusqueda.put(NombresParametrosBusqueda.CATALOG,catalog);
		parametrosBusqueda.put(NombresParametrosBusqueda.SCHEMA,schema);
		parametrosBusqueda.put(NombresParametrosBusqueda.PROCEDURE,procedure);
		parametrosBusqueda.put(NombresParametrosBusqueda.CLASEBEANCURSOR, claseBeanCursor); //Si no vamos a tener cursor, metemos cualquiera

		// LLAMAMOS AL PROCEDIMIENTO ALMACENADO DE FORMA GENERICA
//		HashMap<String,Object> respuestaP = ejecutarGenerico(parametrosBusqueda,bean);
//		HashMap<String,Object> respuestaP = ejecutarGenericoParamObject(parametrosBusqueda, bean);
		HashMap<String,Object> respuestaP = ejecutarGenericoParamObjectConCache(parametrosBusqueda, bean);
		RespuestaGenerica respuesta = new RespuestaGenerica();		
		if (respuestaP!=null) { //Recuperamos el mapa de parámetros, y la lista del cursor
			respuesta.setMapaParametros((HashMap<String,Object>)respuestaP.get(ConstantesPQ.MAPAPARAMETROS));
			respuesta.setListaCursor((List<Object>)respuestaP.get(ConstantesPQ.LISTACURSOR));
		} else {
			respuesta.setMapaParametros((new HashMap<String,Object>()));
			respuesta.setListaCursor((new ArrayList<Object>()));
		}
		//log.info("fin de ejecutarProc: " + schema + " " + catalog + " " + procedure);
		return respuesta;
	}

	/**
	 * 
	 * @param bean beanPQ que hereda del BeanPQGeneral que carga los parametros.
	 * @param schema nombre del usuario de base de datos.
	 * @param catalog nombre del directorio del procedimiento almacenado.
	 * @param procedure nombre del procedimiento. 
	 * @param claseBeanCursor Clase del bean cursor si es necesario. 
	 * @return un objeto respuesta generica con el beanPQ almacenando los datos de salida del proc, y la hashmap vacía.
	 */	
	public RespuestaGenerica ejecutarProc(BeanPQGeneral bean, String schema, String catalog, String procedure, Class claseBeanCursor, boolean beanPQRellenoEnRespuestaGenerica){
		RespuestaGenerica respuesta = ejecutarProc(bean, schema, catalog, procedure, claseBeanCursor);

		if (beanPQRellenoEnRespuestaGenerica)
			respuesta.setBeanPQGeneral(ParametrosUtiles.getBeanPQRelleno(bean, respuesta.getMapaParametros()));

		bean = ParametrosUtiles.getBeanPQRelleno(bean, respuesta.getMapaParametros());
		respuesta.getMapaParametros().clear(); //si devolvemos los valores en el beanpq, se puede vaciar la hashmap.
		return respuesta;
	}
}