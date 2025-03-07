package trafico.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.utiles.enumerados.DecisionTrafico;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQTasasBuscar;
import trafico.beans.daos.BeanPQTasasDesasignar;
import trafico.beans.daos.BeanPQTasasDetalle;
import trafico.beans.daos.BeanPQTasasEliminar;
import trafico.beans.daos.pq_tasas.BeanPQBUSCAR;
import trafico.beans.daos.pq_tasas.BeanPQDETALLE;
import trafico.beans.daos.pq_tasas.BeanPQGUARDAR;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.Usuario;

public class ModeloTasas extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloTasas.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloTasas() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/*
	 *   PROCEDURE BUSCAR  ( P_NUM_COLEGIADO in tramite_trafico.num_colegiado%type,
                      P_ID_CONTRATO in tasa.ID_CONTRATO%type,
                      P_ID_USUARIO in tasa.ID_USUARIO%type,
                      P_ASIGNADA in varchar2,
                      P_CODIGO_TASA in tasa.CODIGO_TASA%type,
                      P_TIPO_TASA in tasa.TIPO_TASA%type,
                      P_FECHA_ALTA_DESDE in tasa.FECHA_ALTA%type,
                      P_FECHA_ALTA_HASTA in tasa.FECHA_ALTA%type,
                      P_FECHA_ASIGNACION_DESDE in tasa.FECHA_ASIGNACION%type,
                      P_FECHA_ASIGNACION_HASTA in tasa.FECHA_ASIGNACION%type,
                      P_FECHA_FIN_VIGENCIA in tasa.FECHA_FIN_VIGENCIA%type,
                      -- Criterios del tramite
                      P_num_expediente in tramite_trafico.num_expediente%type,
                      P_tipo_tramite in tramite_trafico.tipo_tramite%type,
                      P_ref_propia in tramite_trafico.ref_propia%type,
                      ----
                      PAGINA IN NUMBER,
                      NUM_REG IN NUMBER,
                      COLUMNA_ORDEN IN VARCHAR2,
                      ORDEN IN VARCHAR2,
                      CUENTA out number,
                      P_CODE OUT NUMBER,
                      P_SQLERRM OUT VARCHAR2,
                      C_Tasas OUT SYS_REFCURSOR);
	 */

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina,
			SortOrderEnum orden, String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();

		RespuestaGenerica respuesta = null;

		try{
			String numColegiado= "";
			if(utilesColegiado.tienePermisoColegio() || utilesColegiado.tienePermisoAdmin()){
				numColegiado = (String)getParametrosBusqueda().get("numColegiado");
			}else{
				numColegiado=utilesColegiado.getNumColegiadoSession();
			}

			BigDecimal idContratoSession=utilesColegiado.getIdContratoSessionBigDecimal();

			BigDecimal idContrato= utilesColegiado.getIdContratoSessionBigDecimal();

			BigDecimal idUsuario= utilesColegiado.getIdUsuarioSessionBigDecimal();

			DecisionTrafico asign = null;
			if (getParametrosBusqueda().get("asignada")!=null) {
				asign = (DecisionTrafico)getParametrosBusqueda().get("asignada");
			}
			String asignada = "";
			if(asign != null){
				asignada = asign.getValorEnum();
			}else {
				asignada = null;
			}

			String codigoTasa = (String)getParametrosBusqueda().get("codigoTasa");
			String tipoTasa = (String)getParametrosBusqueda().get("tipoTasa");

			FechaFraccionada fechaAlta = (FechaFraccionada)getParametrosBusqueda().get("fechaAlta");

			Timestamp fechaAltaDesde = null;
			Timestamp fechaAltaHasta = null;

			if(fechaAlta!=null){
				Date fechaInicio = null;
				if((fechaInicio = fechaAlta.getFechaInicio()) != null){
					fechaAltaDesde = utilesFecha.getTimestamp(fechaInicio);
				}

				Date fechaFin = null;
				if((fechaFin = fechaAlta.getFechaFin()) != null){
					fechaAltaHasta = utilesFecha.getTimestamp(fechaFin);
				}
			}

			FechaFraccionada fechaAsignacion = (FechaFraccionada)getParametrosBusqueda().get("fechaAsignacion");

			Timestamp fechaAsignacionDesde = null;
			Timestamp fechaAsignacionHasta = null;

			if(fechaAsignacion!=null){

				Date fechaInicio = null;
				if((fechaInicio = fechaAsignacion.getFechaInicio()) != null){
					fechaAsignacionDesde = utilesFecha.getTimestamp(fechaInicio);
				}

				Date fechaFin = null;
				if((fechaFin = fechaAsignacion.getFechaFin()) != null){
					fechaAsignacionHasta = utilesFecha.getTimestamp(fechaFin);
				}
			}

			Fecha fechaFinVigencia = (Fecha)getParametrosBusqueda().get("fechaFinVigencia");
			Timestamp fechaVigencia = null;

			if(fechaFinVigencia!=null && fechaFinVigencia.getAnio()!=null && !fechaFinVigencia.getAnio().equals("")){
				//El dia y el mes de la fecha de fin de vigencia son fijos...
				fechaFinVigencia.setDia("31");
				fechaFinVigencia.setMes("12");
				Date fechaFin = null;
				if((fechaFin = fechaFinVigencia.getFecha()) != null){
					fechaVigencia = utilesFecha.getTimestamp(fechaFin);
				}
			}

			String numExpediente = (String)getParametrosBusqueda().get("numExpediente");
			Long numExp = null;
			if (numExpediente != null && !numExpediente.equals("") && !numExpediente.equals("-1")){
				numExp = new Long(numExpediente);
			}
			String tipoTramite = (String)getParametrosBusqueda().get(ConstantesSession.TIPO_TRAMITE);
			if (tipoTramite != null && tipoTramite.equals("-1")){
				tipoTramite = null;
			}
			String referenciaPropia = (String)getParametrosBusqueda().get(ConstantesSession.REFERENCIA_PROPIA_RECUPERAR);
			BeanPQBUSCAR beanBuscarTasa = new BeanPQBUSCAR();

			beanBuscarTasa.setP_ID_CONTRATO_SESSION(idContratoSession);
			beanBuscarTasa.setP_ID_CONTRATO(idContrato);
			beanBuscarTasa.setP_NUM_COLEGIADO(!"".equals(numColegiado)?numColegiado:null);
			beanBuscarTasa.setP_ID_USUARIO(idUsuario);
			beanBuscarTasa.setP_ASIGNADA(!"".equals(asignada)?asignada:null);
			beanBuscarTasa.setP_CODIGO_TASA(!"".equals(codigoTasa) && !"-1".equals(codigoTasa)?codigoTasa:null);
			beanBuscarTasa.setP_TIPO_TASA(!"".equals(tipoTasa) && !"-1".equals(tipoTasa)?tipoTasa:null);
			beanBuscarTasa.setP_FECHA_ALTA_DESDE(fechaAltaDesde);
			beanBuscarTasa.setP_FECHA_ALTA_HASTA(fechaAltaHasta);
			beanBuscarTasa.setP_FECHA_ASIGNACION_DESDE(fechaAsignacionDesde);
			beanBuscarTasa.setP_FECHA_ASIGNACION_HASTA(fechaAsignacionHasta);
			beanBuscarTasa.setP_FECHA_FIN_VIGENCIA(fechaVigencia);
			beanBuscarTasa.setP_NUM_EXPEDIENTE(numExp!=null?utiles.convertirLongABigDecimal(numExp):null);
			beanBuscarTasa.setP_TIPO_TRAMITE(!"".equals(tipoTramite)?tipoTramite:null);
			beanBuscarTasa.setP_REF_PROPIA(!"".equals(referenciaPropia)?referenciaPropia:null);
			beanBuscarTasa.setPAGINA(utiles.convertirIntegerABigDecimal(pagina));
			beanBuscarTasa.setNUM_REG(utiles.convertirIntegerABigDecimal(numeroElementosPagina));
			beanBuscarTasa.setCOLUMNA_ORDEN(!"".equals(columnaOrden)?columnaOrden:null);
			beanBuscarTasa.setORDEN(orden.getName().toUpperCase());

			respuesta = ejecutarProc(beanBuscarTasa, valoresSchemas.getSchema(), ValoresCatalog.PQ_TASAS, "BUSCAR", BeanPQTasasBuscar.class);

			//Recuperamos información de respuesta
			log.debug(ConstantesPQ.LOG_P_CODE + (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE));
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_CUENTA + respuesta.getParametro("CUENTA"));

			List<Object> listaCursor = respuesta.getListaCursor();
			List<Object> listaBeanVista = new ArrayList<Object>();

			for (Object tasa : listaCursor) {
				BeanPQTasasBuscar tasaAux = (BeanPQTasasBuscar) tasa;
				listaBeanVista.add(tasaAux);
			}

			BigDecimal tamRegs = (BigDecimal) respuesta.getParametro("CUENTA");//listaCursor.size();
			listaRegistros.setTamano(utiles.convertirBigDecimalAInteger(tamRegs));

			listaRegistros.setLista(listaBeanVista);

			return listaRegistros;

		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR+e.getMessage());
			return null;
		}
	}

	public BeanPQTasasDetalle detalleTasa(BeanPQTasasDetalle beanDetalleTasa) {
		BeanPQTasasDetalle resultado =  new BeanPQTasasDetalle();

		RespuestaGenerica respuesta = ejecutarProc(beanDetalleTasa,valoresSchemas.getSchema(),ValoresCatalog.PQ_TASAS,"DETALLE",BeanPQTasasDetalle.class);

		if(respuesta.getParametro("P_CODE")!=null && !respuesta.getParametro("P_CODE").equals("")){
			if(utiles.convertirBigDecimalAInteger((BigDecimal)respuesta.getParametro("P_CODE"))!=0){
				log.error((String)respuesta.getParametro("P_SQLERRM"));
			}
		}
		if(respuesta.getParametro("P_ID_USUARIO") != null){
			log.debug(respuesta.getParametro("P_ID_USUARIO"));
		}else{
			log.error("No se ha recuperado el id usuario de la tasa de codigo:" + respuesta.getParametro("P_CODIGO_TASA"));
		}
		///// FIN DE BORRAR CUANDO SOLUCIONE EL ERROR

		//Recuperamos el mapa de parámetros, y la lista del cursor
		//HashMap<String,Object> mapaParametros = (HashMap<String,Object>)respuesta.getParametro("mapaParametros");
		resultado.setP_CODIGO_TASA(""+respuesta.getParametro("P_CODIGO_TASA"));
		resultado.setP_TIPO_TASA(""+respuesta.getParametro("P_TIPO_TASA"));
		resultado.setP_ID_CONTRATO(respuesta.getParametro("P_ID_CONTRATO")!=null?(BigDecimal)respuesta.getParametro("P_ID_CONTRATO"):null);
		resultado.setP_ID_USUARIO(respuesta.getParametro("P_ID_USUARIO")!=null?(BigDecimal)respuesta.getParametro("P_ID_USUARIO"):null);
		resultado.setP_FECHA_ALTA(respuesta.getParametro("P_FECHA_ALTA")!=null?utilesFecha.getTimestamp((Date)respuesta.getParametro("P_FECHA_ALTA")):null);
		resultado.setP_FECHA_ASIGNACION(respuesta.getParametro("P_FECHA_ASIGNACION")!=null?utilesFecha.getTimestamp((Date)respuesta.getParametro("P_FECHA_ASIGNACION")):null);
		resultado.setP_FECHA_FIN_VIGENCIA(respuesta.getParametro("P_FECHA_FIN_VIGENCIA")!=null?utilesFecha.getTimestamp((Date)respuesta.getParametro("P_FECHA_FIN_VIGENCIA")):null);
		resultado.setP_NUM_EXPEDIENTE(respuesta.getParametro("P_NUM_EXPEDIENTE")!=null?(BigDecimal)respuesta.getParametro("P_NUM_EXPEDIENTE"):null);
		resultado.setP_PRECIO(respuesta.getParametro("P_PRECIO")!=null?(BigDecimal)respuesta.getParametro("P_PRECIO"):null);
		resultado.setP_REF_PROPIA(respuesta.getParametro("P_REF_PROPIA")!=null?""+respuesta.getParametro("P_REF_PROPIA"):"");
		resultado.setP_NUM_COLEGIADO(respuesta.getParametro("P_NUM_COLEGIADO")!=null?""+respuesta.getParametro("P_NUM_COLEGIADO"):"");

		return resultado;
	}

	public String desasignarTasa(BeanPQTasasDesasignar beanDesasignarTasa) {
		//Completamos algunos parámetros para la llamada al procedimiento personalizada
		RespuestaGenerica respuesta = ejecutarProc(beanDesasignarTasa,valoresSchemas.getSchema(),ValoresCatalog.PQ_TASAS,"DESASIGNAR",BeanPQTasasDesasignar.class);

		if(respuesta.getParametro("P_CODE")!=null && !respuesta.getParametro("P_CODE").equals("") &&
			utiles.convertirBigDecimalAInteger((BigDecimal)respuesta.getParametro("P_CODE"))!=0){
			return((String)respuesta.getParametro("P_SQLERRM"));
		}

		return "OK";
	}

	public String eliminarTasa(BeanPQTasasEliminar beanEliminarTasa) {
		//Completamos algunos parámetros para la llamada al procedimiento personalizada
		RespuestaGenerica respuesta = ejecutarProc(beanEliminarTasa,valoresSchemas.getSchema(),ValoresCatalog.PQ_TASAS,"ELIMINAR",BeanPQTasasEliminar.class);

		if(respuesta.getParametro("P_CODE")!=null && !respuesta.getParametro("P_CODE").equals("") &&
			utiles.convertirBigDecimalAInteger((BigDecimal)respuesta.getParametro("P_CODE"))!=0){
			return((String)respuesta.getParametro("P_SQLERRM"));
		}

		return "OK";
	}

	/**
	 * Recupera los detalles de una tasa
	 */
	public BeanPQDETALLE detalleTasaNew(BeanPQDETALLE beanPQ) throws Exception{
		beanPQ.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQ.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQ.getP_SQLERRM());

		return beanPQ;
	}

	/*
	 * 
	 */
	public RespuestaGenerica guardarTasa(Tasa tasa) throws OegamExcepcion{

		RespuestaGenerica respuesta = null;

		try{
			BeanPQGUARDAR beanGuardarTasa = new BeanPQGUARDAR();

			beanGuardarTasa.setP_ID_CONTRATO_SESSION(tasa.getIdContrato()==null?null:utiles.convertirIntegerABigDecimal(tasa.getIdContrato()));
			beanGuardarTasa.setP_CODIGO_TASA(tasa.getCodigoTasa());
			beanGuardarTasa.setP_TIPO_TASA(tasa.getTipoTasa());
			beanGuardarTasa.setP_ID_CONTRATO(tasa.getIdContrato()==null?null:utiles.convertirIntegerABigDecimal(tasa.getIdContrato()));
			beanGuardarTasa.setP_PRECIO(tasa.getPrecio()==null?null:tasa.getPrecio());
			beanGuardarTasa.setP_FECHA_ALTA(utilesFecha.getTimestamp(tasa.getFechaAlta()));
			beanGuardarTasa.setP_FECHA_ASIGNACION(null);
			beanGuardarTasa.setP_FECHA_FIN_VIGENCIA(null);
			beanGuardarTasa.setP_ID_USUARIO(tasa.getIdUsuario()==null?null:utiles.convertirIntegerABigDecimal(tasa.getIdUsuario()));
			beanGuardarTasa.setP_NUM_COLEGIADO(null);

			respuesta = ejecutarProc(beanGuardarTasa, valoresSchemas.getSchema(), ValoresCatalog.PQ_TASAS, "GUARDAR", BeanPQGeneral.class);

			log.debug(ConstantesPQ.LOG_P_CODE+ respuesta.getParametro("P_CODE"));
			log.debug(ConstantesPQ.LOG_P_SQLERRM + respuesta.getParametro("P_SQLERRM"));

			return respuesta;

		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_ESCRITURA+e);
			return respuesta;
		}
	}

	//Guarda en BD el fichero de Tasas.
	//Devuelve un array de objetos: en la primera posición devuelve un fichero con las lineas
	//que fallaron y en la segunda posición una lista con las lineas y los motivos del fallo.
	public HashMap<String,Object> importarTasas (File fichero, Usuario usuario, BigDecimal contrato, String idSession){
		List<ResumenTasas> resumen = new ArrayList<>();
		resumen.add(0,new ResumenTasas("1.1"));
		resumen.add(1,new ResumenTasas("1.2"));
		resumen.add(2,new ResumenTasas("1.4"));
		resumen.add(3,new ResumenTasas("1.5"));
		resumen.add(4,new ResumenTasas("1.6"));
		resumen.add(5,new ResumenTasas("2.3"));
		resumen.add(6,new ResumenTasas("4.1"));
		resumen.add(7,new ResumenTasas("4.2"));
		resumen.add(8,new ResumenTasas("4.3"));
		resumen.add(9,new ResumenTasas("4.31"));
		resumen.add(10,new ResumenTasas("4.32"));
		resumen.add(11,new ResumenTasas("4.33"));
		resumen.add(12,new ResumenTasas("4.34"));
		resumen.add(13,new ResumenTasas("4.4"));
		resumen.add(14,new ResumenTasas("4.5"));
		resumen.add(15,new ResumenTasas("TOTAL"));

		File ficheroErrores=null;

		FicheroBean ficheroBean= new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.TASAS);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(utilesFecha.getFechaActual());
		ficheroBean.setSobreescribir(false);
		ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_ERRORES_TASAS+idSession);
		ficheroBean.setFichero(new File(""));

		try {
			ficheroErrores = gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e1) {
			log.error("Error al guardar el fichero");
		}

		//creamos el fichero
//		File ficheroErrores = new File(ruta+"ficheroTasasErrores"+idSession+".txt");
		List<String> fallos = new ArrayList<String>();

		try{
			// A partir del objeto File creamos el fichero físicamente
			ficheroErrores.createNewFile();
			ficheroErrores.setReadable(true);
			ficheroErrores.setWritable(true);

			Tasa t = null;

			BufferedReader input = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fichero)));
			BufferedWriter escribirErrores = new BufferedWriter(
					new FileWriter(ficheroErrores));

			String line = null;
			int indice = 0;
			int numLineasCabecera = 0;
			String fechaVenta = "";
			log.trace("Leyendo líneas del fichero");
			while (( line = input.readLine()) != null){
				t = new Tasa();
				if(++indice  == 1){
					//Estamos en la linea de cabecera
					if(line.length() == 42){
						fechaVenta = line.substring(12,20);
						numLineasCabecera = Integer.parseInt(line.substring(39, 42).trim());
						escribirErrores.write(line+"\n"); //Escribimos la linea de cabecera en el fichero de errores, por si hay algún error.
					} else {
						escribirErrores.write(line+"\n");
						fallos.add("- Línea "+indice+": La línea no tiene el formato correcto");
					}
				} else {
					//Resto de lineas
					if(line.length() == 29){
						t.setCodigoTasa(line.substring(0,12));
						int codigo1 = line.substring(12,14)!=null && !line.substring(12,14).trim().equals("")?Integer.parseInt(line.substring(12,14).trim()):-1;
						int codigo2 = line.substring(14,16)!=null && !line.substring(14,16).trim().equals("")?Integer.parseInt(line.substring(14,16).trim()):-1;
						t.setTipoTasa(codigo1+"."+codigo2);
						DecimalFormat formateador = new DecimalFormat("######.##");
						Number precio = null;
						try {
							precio = formateador.parse(line.substring(20,26).trim()+","+line.substring(27,29).trim());
						} catch(ParseException pe){
							escribirErrores.write(line+"\n");
							fallos.add("- Línea "+indice+": Error al recuperar el precio de la tasa");
						}
						t.setPrecio(BigDecimal.valueOf(precio.doubleValue()));
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						Date fechaParseada = null;
						try {
							fechaParseada = format.parse(fechaVenta);
						} catch(ParseException pe) {
							escribirErrores.write(line+"\n");
							fallos.add("- Línea "+indice+": Error al recuperar la fecha de venta");
						}

						t.setFechaAlta(fechaParseada);
						t.setFechaAsignacion(null);
						t.setFechaFinVigencia(null);
						t.setIdContrato(contrato.intValue());
						t.setIdUsuario(usuario.getId_usuario().intValue());

						RespuestaGenerica respuesta = guardarTasa(t);

						if((respuesta.getParametro("P_CODE")==null) || (!respuesta.getParametro("P_CODE").equals(BigDecimal.ZERO))){
							escribirErrores.write(line+"\n");
							fallos.add("- Línea "+indice+": "+respuesta.getParametro("P_SQLERRM"));
							actualizaResumen(resumen,t,false);
						} else actualizaResumen(resumen,t,true);
					} else {
						escribirErrores.write(line+"\n");
						fallos.add("- Línea "+indice+": La línea no tiene el formato correcto");
					}
				}
			}

			if(numLineasCabecera != indice-1){
				fallos.add("- No coinciden el número de líneas especificadas en la cabecera del fichero ("+numLineasCabecera+")"
						  +" con el número de líneas de tasas del fichero ("+(indice-1)+")");
			}

			//Cerramos reader y writer
			input.close();
			escribirErrores.close();
		}catch (IOException ioe){
			log.error("Error al crear el fichero de errores");
			fallos.add("- Error desconocido: No se pudo crear el fichero de errores. No se importó ninguna tasa.");
			log.error(ioe);
			HashMap<String,Object> respuesta = new HashMap<String,Object>();
			respuesta.put("fallos", fallos);
			respuesta.put("resumen", resumen);
			return respuesta;
		}catch (Throwable e){
			log.error("Error en la lectura de las lineas del fichero");
			ficheroErrores = fichero;
			fallos.add("- No se pudo importar el fichero. Error en la lectura de las lineas del fichero.");
			log.error(e);
		}

		HashMap<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("fallos", fallos);
		respuesta.put("resumen", resumen);
		return respuesta;
	}

	private List<ResumenTasas> actualizaResumen(List<ResumenTasas> resumen, Tasa t, Boolean correcto){
		if ("1.1".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(0).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(0).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("1.2".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(1).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(1).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("1.4".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(2).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(2).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("1.5".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(3).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(3).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("1.6".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(4).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(4).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("2.3".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(5).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(5).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.1".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(6).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(6).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.2".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(7).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(7).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.3".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(8).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(8).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.31".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(9).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(9).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.32".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(10).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(10).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.33".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(11).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(11).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.34".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(12).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(12).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.4".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(13).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(13).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		} else if ("4.5".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(14).addCorrecta();
				resumen.get(15).addCorrecta();
			} else {
				resumen.get(14).addIncorrecta();
				resumen.get(15).addIncorrecta();
			}
		}
		return resumen;
	}

	/**
	 * Asigna una tasa desde el método de asignacionMasivaTasas() de ConsultaTramiteTraficoAction.java
	 * @param numExpediente
	 * @param tipoTasa
	 * @param params
	 * @return Action.ERROR
	 * @throws ParseException 
	 */
	public HashMap <String, ArrayList<Object>> asignarTasa(String numExpediente, BigDecimal idUsuario, BigDecimal idContrato, String tipoTramite, String tipoTasa, String tipoAsignacion) throws ParseException{

		HashMap <String, ArrayList<Object>> listaResultado = new HashMap <String, ArrayList<Object>>();
		ArrayList<Object> listaErrores = new ArrayList<>();
		ArrayList<Object> listaCorrectos = new ArrayList<>();

		try{
			// Busco en base de datos una tasa libre para el tipo de tasa que quiero asignar
			Fecha fechaActual = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			String codigoTasaLibre = buscarTasaLibre(tipoTasa, fechaActual, idUsuario, idContrato);

			// Si he podido recuperar tasas la asigno
			if(codigoTasaLibre != null && !codigoTasaLibre.equals("")){
				// Creo un bean con los datos de la tasa a asignar
				BeanPQTasasDetalle beanTasa = new BeanPQTasasDetalle();
				beanTasa.setP_CODIGO_TASA(codigoTasaLibre);
				beanTasa.setP_NUM_EXPEDIENTE(new BigDecimal(numExpediente));
				beanTasa.setP_ID_USUARIO(idUsuario);
				beanTasa.setP_ID_CONTRATO_SESSION(idContrato);
				beanTasa.setP_FECHA_ASIGNACION(fechaActual.getTimestamp());
				beanTasa.setP_TIPO(tipoAsignacion);

				RespuestaGenerica respuesta = ejecutarProc(beanTasa, valoresSchemas.getSchema(), ValoresCatalog.PQ_TASAS, "ASIGNAR", BeanPQGeneral.class);

				if(respuesta.getParametro("P_CODE").equals(BigDecimal.ZERO)){
					listaCorrectos.add("Expediente " + respuesta.getParametro("P_NUM_EXPEDIENTE") + " (" + TipoTramiteTrafico.convertirTexto(tipoTramite) + "): " + respuesta.getParametro("P_SQLERRM") + " (" + tipoTasa + " - " + codigoTasaLibre + ")");
				} else {
					listaErrores.add("Expediente " + respuesta.getParametro("P_NUM_EXPEDIENTE") + " (" + TipoTramiteTrafico.convertirTexto(tipoTramite) + "): " + respuesta.getParametro("P_SQLERRM"));
				}
				//listaErrores.add("Al expediente " + numExpediente +" le corresponde tipo tasa: " + tipoTasa + ", y le asignaré la tasa: " + codigoTasaLibre);
			// Si no había tasas disponibles muestro error
			} else {
				listaErrores.add("No hay tasas disponibles para el expediente " + numExpediente);
			}
		} catch(Exception ex) {
			log.error(ex);
			listaErrores.add(ex.toString());
		}

		listaResultado.put(ConstantesTrafico.LISTA_ERRORES, listaErrores);
		listaResultado.put(ConstantesTrafico.LISTA_CORRECTOS, listaCorrectos);

		return listaResultado;
	}

	/**
	 * Busca una tasa libre (del contrato y usuario en sessión) para el tipo de tasa enviada por parámetro
	 * @return String codigoTasa
	 * @throws ParseException 
	 */
	public String buscarTasaLibre(String tipoTasa, Fecha fechaActual) throws ParseException{
		return buscarTasaLibre(tipoTasa, fechaActual, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
	}

	/**
	 * Busca una tasa libre para el tipo de tasa enviada por parámetro
	 * @return String codigoTasa
	 * @throws ParseException 
	 */
	public String buscarTasaLibre(String tipoTasa, Fecha fechaActual, BigDecimal idUsuario, BigDecimal idContrato) throws ParseException{
		BeanPQTasasDetalle beanTasa = new BeanPQTasasDetalle();
		beanTasa.setP_ID_USUARIO(idUsuario);
		beanTasa.setP_ID_CONTRATO(idContrato);
		beanTasa.setP_TIPO_TASA(tipoTasa);
		beanTasa.setP_FECHA_ASIGNACION(fechaActual.getTimestamp());
		RespuestaGenerica respuesta = ejecutarProc(beanTasa, valoresSchemas.getSchema(), ValoresCatalog.PQ_TASAS, "BUSCAR_TASA_LIBRE", BeanPQTasasDetalle.class);

		return (String) respuesta.getParametro("W_TASA");
	}
}