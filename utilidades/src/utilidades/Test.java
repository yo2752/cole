package utilidades;


import utilidades.mensajes.GestorFicherosMensajes;

public class Test {


	public static void main(String[] args) {
		
		//GestorFicherosXML gest = new GestorFicherosXML();
		
		String msj="";
		
		try {
			GestorFicherosMensajes gestor = new GestorFicherosMensajes("basedatos");
			msj=gestor.getMensaje("jdbc.driverClassName");
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
		System.out.println(msj);

		try {
			
			/*
			 * En una aplicación WEB deberíamos guardar el objeto creado
			 * con new GestorBD("basedatos.properties") 
			 * en el contexto de la sesión por ejemplo, en Struts2
			 * com.opensymphony.xwork2.ActionContext.getContext().getSession().put(key, value);
			 * Y obtenerlo desde cualquier punto de la aplicación con:
			 * com.opensymphony.xwork2.ActionContext.getContext().getSession().get(key);
			 * 
			 */
			//ConexionBDBean conBean=new ConexionBDBean("basedatos.properties");
			
//			ConexionBDBean conBean=new ConexionBDBean("basedatos.properties");
//			GestorBD gbd = new GestorBD(conBean);
			
			
			//GestorBD gbd = GestorBDFactory.getInstance().getGestorBDGenerico(); 
			
			//gbd.getConexionBean();
			
			

			/*
			 * EJEMPLO con ejecutarProcedimientoAlmacenado.
			 */
			/*
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros=new HashMap<Integer,ParametroProcedimientoAlmacenado>();
			ParametroProcedimientoAlmacenado pa=new ParametroProcedimientoAlmacenado(
					"p_id",
					java.sql.Types.NUMERIC,
					1,
					EnumTipoUsoParametro.IN
			);
			mapaParametros.put(1, pa);
			pa=new ParametroProcedimientoAlmacenado(
					"p_codigo_resultado",
					java.sql.Types.NUMERIC,
					null,
					EnumTipoUsoParametro.OUT
			);
			mapaParametros.put(2, pa);
			
			
			pa=new ParametroProcedimientoAlmacenado(
					"p_sqlmsg",
					java.sql.Types.VARCHAR,
					null,
					EnumTipoUsoParametro.OUT
			);
			mapaParametros.put(3, pa);	
			
			pa=new ParametroProcedimientoAlmacenado(
					"p_resultado",
					oracle.jdbc.OracleTypes.CURSOR,
					null,
					EnumTipoUsoParametro.OUT
			);
			mapaParametros.put(4, pa);
			
			gbd.ejecutarProcedimientoAlmacenado(
					"PQ_TRAMITES.PRUEBA_NO_BORRAR",
					utilidades.basedatos.RegistroBD.class,
					mapaParametros);
			
			for(Integer param : mapaParametros.keySet()){
				if(
						null!=mapaParametros.get(param) && 
						mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.OUT &&
						null!=mapaParametros.get(param).getValor()  
						
				){
					System.out.println(mapaParametros.get(param).getValor().toString());
				}
			}
			*/
			//ParametroProcedimientoAlmacenado pam = mapaParametros.get(new Integer(2));
			//System.out.println(pam.getValor());
			
			
			/*
			 * EJEMPLO con ejecutarSQL_SELECT.
			 */
			/*
			List<Object> lista = gbd.ejecutarSQL_SELECT(
					"SELECT * FROM TRAFICO_TRAMITE WHERE ROWNUM < 5", 
					utilidades.basedatos.RegistroBD.class);
			if(null!=lista && lista.size()>0){
				utilidades.basedatos.RegistroBD registro = (utilidades.basedatos.RegistroBD)lista.get(0);
				System.out.println(registro.getCIF_CONTRATO());
			}
			*/

			/*
			 * EJEMPLO con ejecutarSQL_ACTUALIZAR sin usar el argumento de conexión.
			 */
			/*
			int filasActualizadas=0;
			try{
					filasActualizadas = gbd.ejecutarSQL_ACTUALIZAR(
					"UPDATE TRAFICO_TRAMITE_CASILLA SET VALOR='GARCIA' WHERE ID_TRAFICO_TRAMITE=6 AND ID_CASILLA=10");
					System.out.println(filasActualizadas);

					
			}catch(Throwable e){
				e.printStackTrace();
			}
			*/
			
			
			/*
			 * EJEMPLO con ejecutarSQL_ACTUALIZAR usando el argumento de conexión y autocommit a false.
			 */
			/*
			Connection con=gbd.getConnection();
			filasActualizadas=0;
			try{
					filasActualizadas = gbd.ejecutarSQL_ACTUALIZAR(
					"UPDATE TRAFICO_TRAMITE_CASILLA SET VALOR='PEREZ' WHERE ID_TRAFICO_TRAMITE=6 AND ID_CASILLA=10",
					con, false);
					System.out.println(filasActualizadas);
					//SIEMPRE USAREMOS con.commit() en este punto, pero
					//para no alterar los datos de pruebas en el ejemplo, 
					//usaremos con.rollback(); 
					 
					con.rollback(); //Para dejar los datos como están.
					//con.commit(); //Para confirmar la modificación.
					
			}catch(Throwable e){
				con.rollback();
			}
			finally{
				try{
					con.close();
				}catch(Throwable e){}
			}
			*/
			


			/*
			 * EJEMPLO con ejecutarSQL_ACTUALIZAR sin usar el argumento de conexión.
			 */
			
			/*
			filasActualizadas=0;
			try{
					filasActualizadas = gbd.ejecutarSQL_ACTUALIZAR(
					"CREATE OR REPLACE TYPE STR_ARRAY_PRUEBA AS VARRAY(3) OF NUMBER");
					System.out.println(filasActualizadas);

					
			}catch(Throwable e){
				e.printStackTrace();
			}
			*/


			/*
			 * EJEMPLO con ejecutarSQL_ACTUALIZAR con paso de lista de parámetros.
			 */
			
			/*
			int filasActualizadas=0;
			try{
				
					List<CampoTabla> listaCampos = new ArrayList<CampoTabla>();
					CampoTabla ct = new CampoTabla("ID_CASILLA",new BigDecimal(10000),1);
					listaCampos.add(ct);
					ct = new CampoTabla("NOMBRE","borrar",2);
					listaCampos.add(ct);
					
					filasActualizadas = gbd.ejecutarSQL_ACTUALIZAR(
					"INSERT INTO TRAFICO_CASILLA_NOMBRE (ID_CASILLA,NOMBRE) VALUES (?,?)",listaCampos);
					
					filasActualizadas = gbd.ejecutarSQL_ACTUALIZAR(
					"DELETE FROM TRAFICO_CASILLA_NOMBRE WHERE ID_CASILLA = ? AND NOMBRE=?",listaCampos);
					
					System.out.println("Filas actualizadas:" + filasActualizadas);
					
			}catch(Throwable e){
				e.printStackTrace();
			}
			*/

			
			
			
			/*
			GestorArbol ga=new GestorArbol("78869919K",gbd);
			ga.getArbol(new BigDecimal(12), "M");
			*/
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}

/*
PROCEDURE PRUEBA_NO_BORRAR (
                      p_id IN number,
                      p_codigo_resultado out number,
                       p_sqlmsg out varchar2, 
--                      p_reg_tipo out TRAFICO_TRAMITE%ROWTYPE,           
                      p_resultado out sys_refcursor
                      ) 
  AS


  
  BEGIN

      p_sqlmsg := 'Comienza la ejecución...';
    --p_codigo_resultado := 0;
    
    --OPEN p_resultado FOR
    --SELECT * FROM TRAFICO_TRAMITE;
      p_codigo_resultado:=23;
      
      --select * into p_reg_tipo from TRAFICO_TRAMITE where ROWNUM=1;
          
      OPEN p_resultado FOR
      SELECT * FROM TRAFICO_TRAMITE WHERE ROWNUM<10;
  Exception

   When others then
      
      p_codigo_resultado := SQLCODE;
      --p_sqlmsg := SQlERRM;

END PRUEBA_NO_BORRAR;
 */