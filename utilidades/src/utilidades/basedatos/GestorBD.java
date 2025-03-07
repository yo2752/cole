package utilidades.basedatos;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;

import utilidades.basedatos.ParametroProcedimientoAlmacenado.EnumTipoUsoParametro;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;


/**Clase GestorBD.
 * 
 * Contiene las funciones de gestión de datos almacenados en base de datos.
 * 
 */


public class GestorBD {
	
	private static final String SELECT2 = "select\n";
	private static final String SELECT = "select ";
	private static final String SENTENCIA_CERRADA = "sentencia cerrada";
	private static final String RESULTSET_CERRADO = "resultset cerrado";
	private java.sql.Connection _conexion=null;		
	private boolean valorAutocommit=true; 	
	private ConexionBDBean _conexionBean=null;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorBD.class);	
	
	/**Constructor
	 * @param conexionBean Bean que contiene las propiedades de conexión a una base de datos.
	 * 
	 */

	public GestorBD(ConexionBDBean conexionBean) throws OegamExcepcion{
		if(conexionBean==null){
			throw new OegamExcepcion(EnumError.error_00030);
		}
		_conexionBean=conexionBean;
	}
	

	public ConexionBDBean getConexionBean() {
		return _conexionBean;
	}
	
	/**
	 * @return Objeto de conexión a base de datos.
	 * 
	 */
	public java.sql.Connection getConnection() throws Throwable{

		log.debug("INICIO DE getConnection() en GestorBD.java");
		if(null==getConexionBean()){
			throw new OegamExcepcion(EnumError.error_00031);
		}
		String strTipoConexion=getConexionBean().getTipoConexion();

		if(strTipoConexion.equals(ConstantesBD.tipoConexionJNDI))
		{
			String conexionJndi = getConexionBean().getJndi();
			ConexionBD conexionBD = ConexionBD.getInstance();
			Connection connection = conexionBD.getJNDIConexion(conexionJndi);
			log.debug("FIN DE getConnection() en GestorBD.java");
			return connection;	
		}
		else if(strTipoConexion.equals(ConstantesBD.tipoConexionJDBC)){
			return ConexionBD.getInstance().getJDBCConexion(getConexionBean());
		}
		else{
			throw new OegamExcepcion(EnumError.error_00032);
		}
	}

	/**
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @param claseRegistro Clase del bean donde se guardarán los registros que 
	 * se obtengan de la base de datos en caso de obtenerse alguno. 
	 * @param con Objeto de conexión a base de datos.
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */
	public List <Object> ejecutarSQL_SELECT(
			String strSQL, 
			Class<?> claseRegistro,
			Connection con) throws OegamExcepcion{
		return ejecutarSQL_SELECT(strSQL, claseRegistro, false, con);
	}
	
	/**
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @param claseRegistro Clase del bean donde se guardarán los registros que 
	 * se obtengan de la base de datos en caso de obtenerse alguno. 
	 * @param con Objeto de conexión a base de datos.
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */
	public List <Object> ejecutarSQL_SELECT(
			String strSQL, 
			Class<?> claseRegistro, boolean cambiarGuionBajo,
			Connection con) throws OegamExcepcion{
		
		List <Object> lista=null;
		
		try{
			
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
				lista=ejecutarSQL_SELECT_ORACLE10(strSQL,claseRegistro,cambiarGuionBajo,con); 
			}
			else{
				throw new OegamExcepcion(EnumError.error_00033);
			}
	    	
		}catch(OegamExcepcion e){
			throw e;
		}
		
		return lista;
	}
	
	
	
	
	/** Ejecuta un prepared statement construyendolo con el string y con el array de parametros, que pueden
	 * ser de 3 tipos: BigDecimal, String o Timestamp
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @param claseRegistro Clase del bean donde se guardarán los registros que 
	 * se obtengan de la base de datos en caso de obtenerse alguno. 
	 * @param con Objeto de conexión a base de datos.
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */
	public List <Object> ejecutarPS_SQL_SELECT(
			String strSQL, 
			Class<?> claseRegistro, boolean cambiarGuionBajo,ArrayList <Object> params) throws OegamExcepcion{
		
		List <Object> lista=null;
		
		try{
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
				lista=ejecutarPS_SQL_SELECT_ORACLE10(strSQL, claseRegistro, cambiarGuionBajo, params);  
			}
			else{
				throw new OegamExcepcion(EnumError.error_00033);
			}
		}catch(OegamExcepcion e){
			throw e;
		}
		
		return lista;
	}
	
	/**
	 * Metodo para llenar el ps
	 * @param ps
	 * @param obj
	 * @return el PreparedStatement con los parametros rellenos. 
	 * @throws SQLException
	 */
	
	private PreparedStatement rellenaPS (PreparedStatement ps , ArrayList<Object> obj) throws SQLException{
		
		for (int i = 0; i < obj.size(); i++) {
			if (null==obj.get(i)) 
				ps.setObject(i+1, null);
			else {
				if (obj.get(i).getClass().equals(String.class))
					ps.setString(i+1, obj.get(i).toString());
				
				if (obj.get(i).getClass().equals(BigDecimal.class))
					ps.setBigDecimal(i+1, (BigDecimal)(obj.get(i)));
				
				if (obj.get(i).getClass().equals(Timestamp.class))
					ps.setTimestamp(i+1, (Timestamp)obj.get(i));
				
				if (obj.get(i).getClass().equals(Date.class))
					ps.setDate(i+1, (Date)obj.get(i));
				
				if (obj.get(i).getClass().equals(Long.class))
					ps.setLong(i+1, (Long)obj.get(i)); 
			}
		}
		return ps; 
		
		
	}
	

	/**
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @param claseRegistro Clase del bean donde se guardarán los registros que 
	 * se obtengan de la base de datos en caso de obtenerse alguno. 
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */
	public List <Object> ejecutarSQL_SELECT(
			String strSQL, 
			Class<?> claseRegistro) throws Throwable{

		Connection con = null;
		List <Object> lista=null;
		con = getConnection();
		lista = ejecutarSQL_SELECT(strSQL,claseRegistro,con); 	
		cerrarConexion(con);
		return lista;
	}
	
	public void cerrarConexion(){
		if (_conexion != null)
			try {
				_conexion.close();
			} catch (SQLException e) {
				log.error(e);
			} 
	}

	private void cerrarConexion(Connection con) {
		try{
			if(null!=con){
				con.close();
				log.debug("cerramos conexion"); 
			}
		}catch(Throwable e){}
	}
	
	public List <Object> ejecutarSQL_SELECT(
			String strSQL, 
			Class<?> claseRegistro, boolean cambiarGuionBajo) throws OegamExcepcion{

		Connection con = null;
		List <Object> lista=null;

		try{
			con = getConnection();
			lista = ejecutarSQL_SELECT(strSQL,claseRegistro,cambiarGuionBajo,con);
		}catch(OegamExcepcion e){
			log.error(e);
			throw e;
		}catch(Throwable e){
			log.error(e);
		}finally{
			try{
				if(null!=con){
					con.close();
					log.debug("cerramos Conexion"); 
				}
			}catch(Throwable e){}
		}
		return lista;
	}

	
	/**
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */
	public List <Object> ejecutarSQL_SELECT(
			String strSQL) throws OegamExcepcion{

		Connection con = null;
		List <Object> lista=null;

		try{
			con = getConnection();
			lista = ejecutarSQL_SELECT(strSQL,con); 	
		}catch(OegamExcepcion e){
			log.error(e);
			throw e;
		}catch(Throwable e){
			log.error(e);
		}finally{
			cerrarConexion(con);
		}
		return lista;
	}
	
	
	public void ejecutarSQL_INSERT(
			String strSQL) throws OegamExcepcion{

		Connection con = null;

		try{
			con = getConnection();
			ejecutarSQL_INSERT(strSQL,con); 	
		}catch(OegamExcepcion e){
			log.error(e);
			throw e;
		}catch(Throwable e){
			log.error(e);
		}finally{
			cerrarConexion(con);
		}
		
	}
	
	public List <Object> ejecutarSQL_SELECT(
			String strSQL, 
			Connection con) throws OegamExcepcion{
		return ejecutarSQL_SELECT_ORACLE10(strSQL, con);
	}
	
	public void ejecutarSQL_INSERT(
			String strSQL, 
			Connection con) throws OegamExcepcion{
		ejecutarSQL_INSERT_ORACLE10(strSQL, con);
	}
	
	public void ejecutarSQL_UPDATE(
			String strSQL) throws OegamExcepcion{
		ejecutarSQL_ACTUALIZAR(strSQL);
	}
	
	/**
	 * @param strSQL Sentencia SQL a ser ejecutada por la base de datos.
	 * @param claseRegistro Clase del bean donde se guardarán los registros que 
	 * se obtengan de la base de datos en caso de obtenerse alguno. 
	 * @param con Objeto de conexión a base de datos.
	 * @param autoCommit Si es true, se ejecutará commit automáticamente al
	 * finalizar la ejecución de la sentencia SQL.
	 * @return Lista de registros obtenidos de la base de datos en caso de 
	 * obtenerse registros.
	 * 
	 */	
	private int ejecutarSQL_ACTUALIZAR(
			String strSQL, 
			Connection con,
			boolean autoCommit) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		

		int filasAfectadas = 0; 
	    java.sql.Statement sentencia =null;
		
	    	    
		try{
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
			    try {
			    	
			    	con.setAutoCommit(autoCommit);
			    	
			    	sentencia = con.createStatement();
			    	filasAfectadas = sentencia.executeUpdate(strSQL);
		    	
			    }
			    catch(Throwable e){

			    	throw new OegamExcepcion(EnumError.error_00040);
			    }
			    
			    finally{
				    if (sentencia != null) {
				       try { 
				    	   sentencia.close();
				    	   log.debug("cerramos sentencia"); 
				       } catch (Throwable sqle) {}
				    }
			    }
			}
			else{
				throw new OegamExcepcion(EnumError.error_00039);
			}
	    	
		}catch(OegamExcepcion e){

			throw e;
			
		}
		
		return filasAfectadas;
	}
	
	private int ejecutarSQL_ACTUALIZAR(
			String strSQL) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		
		Connection con = null;
		int filasAfectadas = 0; 
		
		try{
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
			    try {
			    	con = getConnection();
			    	filasAfectadas = ejecutarSQL_ACTUALIZAR(strSQL,con, valorAutocommit);
			    	if(!valorAutocommit) 
			    		con.commit();
			    }
			    catch(OegamExcepcion e){
			    	try{
			    		if (!valorAutocommit)
			    		con.rollback();
			    	}catch(Throwable e2){
			    		log.error(e2);
			    	}
			    	throw e;
			    }
			    catch(Throwable e){
			    	log.error(e);
			    }
			}
			else{
				throw new OegamExcepcion(EnumError.error_00033);
			}
	    	
		}catch(OegamExcepcion e){
			throw new OegamExcepcion(e, EnumError.error_00001);
		}finally{
			cerrarConexion(con);
		}
		return filasAfectadas;
	}

	public List <Object> ejecutarFuncion(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			Connection con
			
		) throws OegamExcepcion{
		
		return ejecutarFuncion(nombreProcedimiento,claseRegistro,mapaParametros,false,con);
		
	}

	public List <Object> ejecutarFuncion(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			boolean cambiarGuionBajo,
			Connection con
			
		) throws OegamExcepcion{
		
		return ejecutarProc_Func(nombreProcedimiento,claseRegistro,mapaParametros,con,EnumTipoProcedimiento.Funcion, cambiarGuionBajo);
		
	}

	public List <Object> ejecutarProcedimientoAlmacenado(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			Connection con
			
		) throws OegamExcepcion{
		
		return ejecutarProcedimientoAlmacenado(nombreProcedimiento,claseRegistro,mapaParametros,false,con);
		
	}
	
	public List <Object> ejecutarProcedimientoAlmacenado(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			boolean cambiarGuionBajo,
			Connection con
			
		) throws OegamExcepcion{
		
		return ejecutarProc_Func(nombreProcedimiento,claseRegistro,mapaParametros,con,EnumTipoProcedimiento.Procedimiento_almacenado, cambiarGuionBajo);
		
	}
	

	
	public List <Object> ejecutarFuncion(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros
			
		) throws OegamExcepcion{
		
		return ejecutarFuncion(nombreProcedimiento,claseRegistro,mapaParametros, false);
	}
	
	public List <Object> ejecutarFuncion(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros, boolean cambiarGuionBajo
			
		) throws OegamExcepcion{
		
		return ejecutarProc_Func(nombreProcedimiento,claseRegistro,mapaParametros,EnumTipoProcedimiento.Funcion, cambiarGuionBajo);
	}

	public List <Object> ejecutarProcedimientoAlmacenado(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros
			
		) throws OegamExcepcion{
		
		return ejecutarProcedimientoAlmacenado(nombreProcedimiento, claseRegistro, mapaParametros, false);
	}
	
	public List <Object> ejecutarProcedimientoAlmacenado(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros, boolean cambiarGuionBajo
			
		) throws OegamExcepcion{
		
		return ejecutarProc_Func(nombreProcedimiento,claseRegistro,mapaParametros,EnumTipoProcedimiento.Procedimiento_almacenado, cambiarGuionBajo);
	}
	
//	private int ejecutarSQL_ACTUALIZAR(String strSQL, List<CampoTabla> valores) throws OegamExcepcion{
//		
//		Connection con = null;
//		int filasAfectadas = 0; 
//		
//		try{
//			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
//			    try {
//			    	con = getConnection();
//			    	
//			    	filasAfectadas = ejecutarSQL_ACTUALIZAR(strSQL,valores,con,valorAutocommit);
//			    	if(!valorAutocommit) con.commit();
//			    }
//			    catch(Throwable e){
//			    	
//			    	try{
//			    		if(!valorAutocommit) 	con.rollback();
//			    	}catch(Throwable e2){}
//			    	
//			    	throw e;
//			    }
//			}
//			else{
//				throw new OegamExcepcion(EnumError.error_00048);
//			}
//	    	
//		}catch(Throwable e){
//			throw new OegamExcepcion("fallo a actualizar los valores de bd", EnumError.error_00001);
//		}finally{
//			cerrarConexion(con);
//		}
//		
//		return filasAfectadas;
//	}
	
//	private int ejecutarSQL_ACTUALIZAR(
//			String strSQL,
//			List<CampoTabla> valores,
//			Connection con,
//			boolean autoCommit) throws OegamExcepcion{
//		
//
//		if(null==valores || valores.size()==0){
//			throw new OegamExcepcion(EnumError.error_00049);
//		}
//
//		int filasAfectadas = 0; 
//
//	    java.sql.PreparedStatement sentencia = null;
//	    	    
//		try{
//			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
//			    try {
//			    	con.setAutoCommit(autoCommit);
//			    	
//			        //String query = "insert into dept(deptnum, deptname, deptloc) values(?, ?, ?)";
//		    		log.debug("inicio prepare"); 
//			        sentencia = con.prepareStatement(strSQL);
//			        log.debug("fin prepare"); 
//			        Object valor=null;
//			        int orden=0;
//			        
//			        for(CampoTabla campo:valores){
//			        	
//			        	valor=campo.getValor();
//			        	orden=campo.getOrden().intValue();
//			        	
//			        	if(null==valor){
//			        		sentencia.setNull(orden, convertirJava_Sql(campo.getClase()));
//			        	}
//			        	else if(valor instanceof java.lang.String){
//			        		sentencia.setString(orden, (java.lang.String)campo.getValor());
//						}
//						else if(valor instanceof java.math.BigDecimal){
//			        		sentencia.setFloat(orden, ((java.math.BigDecimal)campo.getValor()).floatValue()  );
//						}
//						else if(valor  instanceof java.lang.Integer){
//			        		sentencia.setInt(orden, ((java.lang.Integer)campo.getValor()).intValue()  );	
//			        	}
//						else if(valor  instanceof java.lang.Boolean){
//			        		sentencia.setBoolean(orden, ((java.lang.Boolean)campo.getValor()).booleanValue()  );	
//						}
//						else if(valor instanceof java.sql.Timestamp){
//			        		sentencia.setTimestamp(orden, (( java.sql.Timestamp)campo.getValor()) );	
//						}
//						else if(valor instanceof java.sql.Date){
//			        		sentencia.setDate(orden, (( java.sql.Date)campo.getValor()) );	
//						}
//						else{
//							throw new OegamExcepcion(EnumError.error_00042);						
//						}			        	
//			        	
//			        }
//
//			    	filasAfectadas = sentencia.executeUpdate();
//		    	
//			    }
//			    catch(Throwable e){
//		    	
//			    	throw e;
//			    }
//			    
//			    finally{
//				    if (sentencia != null) {
//				       try { 
//				    	   sentencia.close();
//				    	   log.debug("cerramos sentencia"); 
//				       } catch (Throwable sqle) {}
//				    }
//			    }
//			}
//			else{
//				throw new OegamExcepcion(EnumError.error_00048);
//			}
//	    	
//		}catch(Throwable e){
//			throw new OegamExcepcion(e, EnumError.error_00001);
//		}
//		
//		return filasAfectadas;
//	}
	
	public boolean isValidConnection(Connection con) throws OegamExcepcion {
		
		//devolvemos true porque usamos pool de conexiones gestionado por el tomcat, que es el encargado de validar las conexiones.
		return true; 
		
		
		
		/*boolean valida= true;
		
			
		if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
			
			try{
				valida=isValidConnection_ORACLE (con);
			}catch(Throwable e){
				valida=false;
			}
			
			if(!valida){
				try{
					if(null!=con && !con.isClosed()){
						con.close();
					}
					
				}catch(Throwable e){
					
				}
				finally{
					try{
						con=null;
					}catch(Throwable e){}
				}
			}
			
			return valida; 
		}
		else{
			throw new OegamExcepcion(EnumError.error_00045);
		}
				*/
		
		
	}
	
	public java.sql.Connection getConexion(){
		try{
		_conexion = getConnection(); 
		return _conexion;
		}catch(Throwable e){
			log.error(e);
			return null;
		}
	}


	public void setConexion(java.sql.Connection conexion) {
		_conexion = conexion;
	}
	
	/*
	 * 
	 * MÉTODOS PRIVADOS
	 * 
	 */

	private List <Object> ejecutarProc_Func(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			Connection con,
			EnumTipoProcedimiento tipoProcedimiento, boolean cambiarGuionBajo
			
		) throws OegamExcepcion{
		
		List <Object> lista=null;
		
		try{
			
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
				lista=ejecutarProcedimientoAlmacenado_ORACLE10(
						nombreProcedimiento,
						claseRegistro,
						mapaParametros, 
						con,
						tipoProcedimiento,
						cambiarGuionBajo); 
			}
			else{
				throw new OegamExcepcion(EnumError.error_00039);
			}
	    	
		}catch(OegamExcepcion e){
			throw e;
		}finally{
		}

		return lista;
	}
		
			
	private List<Object> ejecutarProc_Func(
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			EnumTipoProcedimiento tipoProcedimiento, boolean cambiarGuionBajo) throws OegamExcepcion{
		
		List <Object> lista=null;
		Connection con = null;
		
		try{
			
			con = getConnection();
			if(getConexionBean().getTipoBD().equalsIgnoreCase(ConstantesBD.BD_ORACLE10)){
				lista=ejecutarProcedimientoAlmacenado_ORACLE10(
						nombreProcedimiento,
						claseRegistro,
						mapaParametros, 
						con,
						tipoProcedimiento, 
						cambiarGuionBajo); 
			}
			else{
				throw new OegamExcepcion(EnumError.error_00048);
			}
	    	
		}catch(OegamExcepcion e){
			log.error(e);
			throw e;
//			throw new OegamExcepcion("fallo al ejecutar el proceso almacenado", EnumError.error_00001);
		}catch(Throwable e){
			log.error(e);
		}finally{
			cerrarConexion(con);
		}

		return lista;		
	}
	
	private List <Object> llenarLista(
			ResultSet resultados,
			Class<?> claseRegistro, boolean cambiarGuionBajo) throws Throwable{
		
	    Object resultado=null;
		List<Object> listaDatos = new ArrayList<Object>();
		HashMap<String,Method> mapaMetodos=new HashMap<String,Method>();
		
		List<String> mapaColumnasDevueltas=new ArrayList<String>();	
		
		try{
		    for(int i=1;i<resultados.getMetaData().getColumnCount()+1;i++){
		    	mapaColumnasDevueltas.add(resultados.getMetaData().getColumnName(i));	
		    }
		}catch(Throwable e){
			throw new OegamExcepcion(EnumError.error_00046);
		}
	    
		try{
			for(Method metodo: claseRegistro.getDeclaredMethods()){
		    	if(metodo.getName().startsWith("set")){
		    		mapaMetodos.put(metodo.getName().toLowerCase(), metodo);
		    	}
		    }
		}catch(Throwable e){
			throw new OegamExcepcion(EnumError.error_00047);
		}
		
	    Object registro=null;
	    String tipoArgumento=null;	    


	    try{
		    while (resultados.next()) {
		    	
		    	resultado=null;
		    	registro = claseRegistro.newInstance();
	    		
		    	
		    	for(String campo:mapaColumnasDevueltas){
		    		
		    		if(mapaMetodos.containsKey("set"+campo.toLowerCase())){
		    			
		    			Method metodo = mapaMetodos.get("set"+campo.toLowerCase());
		    			Class<?>[] argumentos=metodo.getParameterTypes();
		    			tipoArgumento=argumentos[0].getName();
		    			
			    		if(tipoArgumento.equals(java.math.BigDecimal.class.getName())){
			    			resultado=resultados.getBigDecimal(campo);
			    		}	    			
			    		else if(tipoArgumento.equals(java.lang.String.class.getName())){
			    			resultado=resultados.getString(campo);
			    		}
			    		else if(tipoArgumento.equals(java.sql.Timestamp.class.getName())){
			    			resultado=resultados.getTimestamp(campo);
			    		}
			    		else if(tipoArgumento.equals(java.sql.Date.class.getName())){
			    			resultado=resultados.getDate(campo);
			    		}
			    		else if(tipoArgumento.equals(java.lang.Boolean.class.getName())){
			    			resultado=resultados.getBoolean(campo);
			    		}
			    		else if(tipoArgumento.equals(java.lang.Integer.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalAInteger(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(java.lang.Long.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalALong(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(java.lang.Float.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalAFloat(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(utilidades.estructuras.Fecha.class.getName())){
			    			resultado=UtilesFecha.getInstance().getFechaFracionada(resultados.getDate(campo), resultados.getTime(campo));
			    		}
			    		
			    		//...agregar más posibilidades cuando sea necesario
			    		
			    		else {
			    			resultado=resultados.getObject(campo);
			    		}
	
			    		mapaMetodos.get("set"+campo.toLowerCase()).invoke(registro, resultado);
	
		    		}
		    		else if(cambiarGuionBajo && mapaMetodos.containsKey("set"+campo.toLowerCase().replaceAll("_", ""))){
		    			
		    			String nombreCampoSinGuionBajo = "set"+campo.toLowerCase().replaceAll("_", "");
		    			Method metodo = mapaMetodos.get(nombreCampoSinGuionBajo);
		    			Class<?>[] argumentos=metodo.getParameterTypes();
		    			tipoArgumento=argumentos[0].getName();
		    			
			    		if(tipoArgumento.equals(java.math.BigDecimal.class.getName())){
			    			resultado=resultados.getBigDecimal(campo);
			    		}	    			
			    		else if(tipoArgumento.equals(java.lang.String.class.getName())){
			    			resultado=resultados.getString(campo);
			    		}
			    		else if(tipoArgumento.equals(java.sql.Timestamp.class.getName())){
			    			resultado=resultados.getTimestamp(campo);
			    		}
			    		else if(tipoArgumento.equals(java.sql.Date.class.getName())){
			    			resultado=resultados.getDate(campo);
			    		}
			    		else if(tipoArgumento.equals(java.lang.Boolean.class.getName())){
			    			resultado=resultados.getBoolean(campo);
			    		}
			    		else if(tipoArgumento.equals(java.lang.Integer.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalAInteger(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(java.lang.Long.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalALong(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(java.lang.Float.class.getName())){
			    			resultado=Utiles.getInstance().convertirBigDecimalAFloat(resultados.getBigDecimal(campo));
			    		}
			    		else if(tipoArgumento.equals(utilidades.estructuras.Fecha.class.getName())){
			    			resultado=UtilesFecha.getInstance().getFechaFracionada(resultados.getDate(campo), resultados.getTime(campo));
			    		}
			    		else {
			    			resultado=resultados.getObject(campo);
			    		}
	
			    		mapaMetodos.get(nombreCampoSinGuionBajo).invoke(registro, resultado);
		    			
		    		}
		    	}
	
		    	listaDatos.add(registro);
		    }
	    }catch(Throwable e){
	    	log.error(e);
	    	throw new Throwable("No se han mapeado correctamente los campos");
	    }
	    
	    
	    return listaDatos;
	}
	
	private List <Object> ejecutarSQL_SELECT_ORACLE10(
			String strSQL, 
			Class<?> claseRegistro, boolean cambiarGuionBajo,
			Connection con) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		
		if(
				!strSQL.toLowerCase().startsWith(SELECT) && 
				!strSQL.toLowerCase().startsWith(SELECT2) && 
				!strSQL.toLowerCase().startsWith("select\t")){
			
			throw new OegamExcepcion(EnumError.error_00036);
		}

		List<Object> listaDatos = null; 
	    java.sql.Statement sentencia =null;
	    ResultSet resultados=null;
	    
	    try {
	    	
	    	sentencia = con.createStatement();
		    resultados = sentencia.executeQuery(strSQL);
		    listaDatos=llenarLista(resultados,claseRegistro,cambiarGuionBajo);
	    }
	    catch(Throwable e){
	    	throw new OegamExcepcion(EnumError.error_00037);
	    }
	    finally{
	    	cerrarRecursosTransaccion(con, sentencia, resultados);
	    }

		return listaDatos;
	}

	
/**
 * Procedimiento para ejecutar un prepared statement	
 * @param strSQL
 * @param claseRegistro
 * @param cambiarGuionBajo
 * @param con
 * @return
 * @throws OegamExcepcion
 */
	
	private List <Object> ejecutarPS_SQL_SELECT_ORACLE10(
			String strSQL, 
			Class<?> claseRegistro, boolean cambiarGuionBajo, ArrayList<Object> params) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		
		if(
				!strSQL.toLowerCase().startsWith(SELECT) && 
				!strSQL.toLowerCase().startsWith(SELECT2) && 
				!strSQL.toLowerCase().startsWith("select\t")){
			
			throw new OegamExcepcion(EnumError.error_00036);
		}
		Connection con = null;
		java.sql.PreparedStatement ps = null;
	    ResultSet resultados=null;
	    List<Object> listaDatos = null;
	    try {
		    con = getConnection(); 
	    	ps = con.prepareStatement(strSQL);
	    	ps = rellenaPS(ps, params); 
	    	
		    resultados = ps.executeQuery();
		    listaDatos=llenarLista(resultados,claseRegistro,cambiarGuionBajo);
	    	}
	    catch(Throwable e){
	    	log.error(e);
	    	throw new OegamExcepcion(EnumError.error_00037);
	    	
	    }
	    finally{
	    	cerrarRecursosTransaccion(con, ps, resultados);
	    }

		return listaDatos;
	}

	
	
	private void cerrarRecursosTransaccion(Connection con,
			java.sql.Statement sentencia, ResultSet resultados) {
		
		cerrarResultSet(resultados);
		cerrarSentencia(sentencia);
		cerrarConexion(con); 
	}


	public void cerrarResultSet(ResultSet resultados) {
		if (null!=resultados) {
			try { 
//				if (!resultados.isClosed())
				resultados.close();
				log.debug(RESULTSET_CERRADO); 
			}catch (Throwable e) {
				log.error(e); 
			}
		}
	}


	public void cerrarSentencia(java.sql.Statement sentencia) {
		if (sentencia != null) {
		   try { 
//			   if (!sentencia.isClosed())
			   sentencia.close(); 
			   log.debug(SENTENCIA_CERRADA); 
		   } catch (Throwable sqle) {
			   log.error(sqle); 
		   }
		}
	}
	
	private List <Object> ejecutarSQL_SELECT_ORACLE10(
			String strSQL, 
			Connection con) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		
		if(
				!strSQL.toLowerCase().startsWith(SELECT) && 
				!strSQL.toLowerCase().startsWith(SELECT2) && 
				!strSQL.toLowerCase().startsWith("select\t")){
			
			throw new OegamExcepcion(EnumError.error_00036);
		}

		List<Object> listaDatos = new Vector<Object>(); 
	    java.sql.Statement sentencia =null;
	    ResultSet resultados=null;
	    
	    try {
	    	
	    	sentencia = con.createStatement();
		    resultados = sentencia.executeQuery(strSQL);
		    
		    int i = 1;
		    while (resultados.next()) {
		    	
		    	listaDatos.add(resultados.getObject(i));
		    	i++;
		    	
		    }
	    }catch(Throwable e){
	    	log.error(e);
	    	throw new OegamExcepcion(EnumError.error_00037);
	    }
	    finally{
	    	
	    cerrarRecursosTransaccion(con, sentencia, resultados);
	    }

		return listaDatos;
	}
	
	
	private void ejecutarSQL_INSERT_ORACLE10(
			String strSQL, 
			Connection con) throws OegamExcepcion{
		
		strSQL=validarSentencia(strSQL);
		
		if(
				!strSQL.toLowerCase().startsWith("insert") && 
				!strSQL.toLowerCase().startsWith("insert2") && 
				!strSQL.toLowerCase().startsWith("insert\t")){
			
			throw new OegamExcepcion(EnumError.error_00036);
		}

		List<Object> listaDatos = new Vector<Object>(); 
	    java.sql.Statement sentencia =null;
	    ResultSet resultados=null;
	    
	    try {
	    	
	    	sentencia = con.createStatement();
		    resultados = sentencia.executeQuery(strSQL);
		    
		    int i = 1;
		    while (resultados.next()) {
		    	
		    	listaDatos.add(resultados.getObject(i));
		    	i++;
		    	
		    }
	    }catch(Throwable e){
	    	log.error(e);
	    	throw new OegamExcepcion(EnumError.error_00037);
	    }
	    finally{
	    	
	    cerrarRecursosTransaccion(con, sentencia, resultados);
	    }

		
	}
	
	private List <Object> ejecutarProcedimientoAlmacenado_ORACLE10(
			
			String nombreProcedimiento,
			Class<?> claseRegistro,
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros,
			Connection con,
			EnumTipoProcedimiento tipoProcedimiento, boolean cambiarGuionBajo
			
		) throws OegamExcepcion{
		
		if(null==nombreProcedimiento || nombreProcedimiento.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00038);
		}
		
		int indiceParametroCursor=-1;
		ResultSet cursor=null;
		CallableStatement cstmt=null;
		List <Object> lista=null;
		
		try{
			String sentencia="";
			
			switch(tipoProcedimiento.getValor()){
			case 0: //PROCEDIMIENTO

				sentencia="{call " + nombreProcedimiento + "(";

				if(null==mapaParametros || mapaParametros.size()==0){
					sentencia=sentencia+")}";
				}
				else{
					for(int i=1;i<=mapaParametros.size();i++){
						sentencia=sentencia+"?,";
					}
					sentencia=sentencia.substring(0, sentencia.length()-1)+")}";
				}	
				
				break;
				
			case 1: //FUNCION
				
				if(null==mapaParametros || mapaParametros.size()==0){
					throw new OegamExcepcion(EnumError.error_00041);
				}
				
				sentencia="{? = call " + nombreProcedimiento + "(";
				
				
				for(int i=1;i<mapaParametros.size();i++){
					sentencia=sentencia+"?,";
				}
				sentencia=sentencia.substring(0, sentencia.length()-1)+")}";
					

				
				break;
			}
			
			
			cstmt = con.prepareCall(sentencia);
			Class<?> tipoDatoJava=null;
			
			//oracle.sql.ArrayDescriptor arrayDescriptor=null;
			
			if(null!=mapaParametros && mapaParametros.size()>0){

				for(Integer param:mapaParametros.keySet()){
					
					if(mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.IN || mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.INOUT){
						
						tipoDatoJava=mapaParametros.get(param).getTipoDatoJava();
						
						if(null==mapaParametros.get(param).getValor()){
							cstmt.setNull(param.intValue(), convertirJava_Sql(mapaParametros.get(param).getTipoDatoJava()));								
						}
						
						else if (tipoDatoJava==java.lang.String.class){
							if("".equals((String)mapaParametros.get(param).getValor()))
								cstmt.setNull(param.intValue(), convertirJava_Sql(mapaParametros.get(param).getTipoDatoJava()));
							else
								cstmt.setString(param.intValue(),(String)mapaParametros.get(param).getValor());
							
						}
						else if(tipoDatoJava==java.math.BigDecimal.class){
							cstmt.setBigDecimal(param.intValue(),(java.math.BigDecimal)mapaParametros.get(param).getValor());
						}
						else if(tipoDatoJava==java.lang.Integer.class){
							cstmt.setInt(param.intValue(),(java.lang.Integer)mapaParametros.get(param).getValor());
						}
						else if(tipoDatoJava==java.lang.Long.class){
							cstmt.setLong(param.intValue(),(java.lang.Long)mapaParametros.get(param).getValor());
						}
						else if(tipoDatoJava==java.lang.Boolean.class){
							cstmt.setBoolean(param.intValue(),(java.lang.Boolean)mapaParametros.get(param).getValor());
						}
						else if(tipoDatoJava==java.sql.Timestamp.class){
							cstmt.setTimestamp(param.intValue(),(java.sql.Timestamp)mapaParametros.get(param).getValor());
						}
						else if(tipoDatoJava==java.sql.Date.class){
							cstmt.setDate(param.intValue(),(java.sql.Date)mapaParametros.get(param).getValor());
						}
						else{
							throw new  OegamExcepcion(EnumError.error_00042);					
						}
					}

					if  (mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.OUT || mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.INOUT){
						
						if(mapaParametros.get(param).getTipoDatoSQL()==oracle.jdbc.OracleTypes.CURSOR){
							indiceParametroCursor=param.intValue();
						}
		
						cstmt.registerOutParameter(param.intValue(),mapaParametros.get(param).getTipoDatoSQL());

					}

				}			
			}		
	    
			cstmt.execute();
			
			if(indiceParametroCursor!=-1){
				
//				cursor = ((OracleCallableStatement)cstmt).getCursor(indiceParametroCursor);
				cursor =(ResultSet)cstmt.getObject(indiceParametroCursor);
//				cursor = cstmt.getCursor(indiceParametroCursor);
				if(null!=cursor){
					lista= llenarLista(cursor, claseRegistro, cambiarGuionBajo);
				}
			}

			
			
			for(Integer param:mapaParametros.keySet()){
				if(
						(mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.OUT || mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.INOUT)&&
						mapaParametros.get(param).getTipoDatoSQL()!=oracle.jdbc.OracleTypes.CURSOR){
					
					mapaParametros.get(param).setValor(cstmt.getObject(param.intValue()));
				
				}
			}
			
		}catch(Throwable e){
			log.error(e);
			throw new OegamExcepcion(e.getMessage() , EnumError.error_00038);
			
		}finally{
			try{
				if(null!=cursor){
					cursor.close();
					log.debug("cerramos cursor"); 
				}
			}catch(Throwable e){}

			try{
				if(null!=cstmt){
					cstmt.close();
					log.debug("cerramos sentencia"); 
				}
			}catch(Throwable e){}
		}
		
		return lista;
		
	}
	
	private String validarSentencia(String strSQL) throws OegamExcepcion{
		
		if(null==strSQL){
			throw new OegamExcepcion(EnumError.error_00034);
		}
		
		strSQL=strSQL.trim();
		
		if(strSQL.length()==0){
			throw new OegamExcepcion(EnumError.error_00035);
		}
		
		return strSQL;
	}
	
//	private boolean isValidConnection_ORACLE(Connection con) throws OegamExcepcion, SQLException {
//		//Obtenida de http://www.java2s.com/Code/Java/Database-SQL-JDBC/CheckJDBCInstallationforOracle.htm
//		//Esta función se usa en sustitución de Connection.isValid(int timeout) porque no funciona.
//		
//		if (con == null || con.isClosed()) {
//			return false;
//	    }
//		else return true;
//
//	 /*   ResultSet rs = null;
//	    Statement stmt = null;
//	    try {
//	    	stmt = con.createStatement();
//	    	if (stmt == null) {
//	    		return false;
//	    	}
//
//	    	rs = stmt.executeQuery("select 1 from dual");
//	    	if (rs == null) {
//	    		return false;
//	    	}
//
//	    	if (rs.next()) {
//	    		return true;
//	    	}
//	    	
//	    	return false;
//
//	    } catch (Throwable e) {
//	    	return false;
//	    } finally {
//	    	
//	    	try {
//	    		rs.close();
//	    		stmt.close();
//	    	} catch (Throwable e) {}
//	    }	    */
//	}
	
	private int convertirJava_Sql(Class<?> clase) throws OegamExcepcion{
		
		if(null==clase){
			throw new OegamExcepcion(EnumError.error_00043);
		}
		
		String nombreClase = clase.getName();
		
		
		if(nombreClase.equals(java.lang.String.class.getName())){
			return java.sql.Types.VARCHAR;
		}
		
		else if(nombreClase.equals(java.math.BigDecimal.class.getName())){
			return java.sql.Types.NUMERIC;
		}		
		
		else if(nombreClase.equals(java.lang.Boolean.class.getName())){
			return java.sql.Types.BOOLEAN;
		}		
		
		else if(nombreClase.equals(java.lang.Integer.class.getName())){
			return java.sql.Types.NUMERIC;
		}
		
		else if(nombreClase.equals(java.lang.Long.class.getName())){
			return java.sql.Types.NUMERIC;
		}
		
		else if(nombreClase.equals(java.sql.Timestamp.class.getName())){
			return java.sql.Types.TIMESTAMP;
		}
		
		else if(nombreClase.equals(java.sql.Date.class.getName())){
			return java.sql.Types.DATE;
		}

		//... agregar más
		
		else{
			throw new OegamExcepcion(EnumError.error_00044);
		}
	}

	private static enum EnumTipoProcedimiento {
		
		Procedimiento_almacenado	(0),
		Funcion						(1);

	    private final int _valor;
	    
	    EnumTipoProcedimiento(int valor) {
	        _valor = valor;
	    }

		public int getValor() {
			return _valor;
		}
	}
	

}


/*
if(mapa.get(campo)==java.sql.Types.TINYINT){
	resultado=resultados.getByte(campo);
}
else if(mapa.get(campo)==java.sql.Types.SMALLINT){
	resultado=resultados.getShort(campo);
}
else if(mapa.get(campo)==java.sql.Types.INTEGER){
	resultado=resultados.getInt(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.BIGINT){
	resultado=resultados.getLong(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.REAL){
	resultado=resultados.getFloat(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.FLOAT){
	resultado=resultados.getDouble(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.DOUBLE){
	resultado=resultados.getDouble(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.NUMERIC){
	resultado=resultados.getBigDecimal(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.BIT){
	resultado=resultados.getBoolean(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.CHAR){
	resultado=resultados.getString(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.VARCHAR){
	resultado=resultados.getString(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.LONGVARCHAR){
	resultado=resultados.getString(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.BINARY){
	resultado=resultados.getBytes(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.VARBINARY){
	resultado=resultados.getBytes(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.LONGVARBINARY){
	resultado=resultados.getBytes(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.DATE){
	resultado=resultados.getDate(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.TIME){
	resultados.getTime(campo);	    		
}
else if(mapa.get(campo)==java.sql.Types.TIMESTAMP){
	resultados.getTimestamp(campo);	    		
}
*/  	