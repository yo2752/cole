package colas.modelo;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import oegam.constantes.ValoresSchemas;
import procesos.colaCursor;
import procesos.beans.ProcesoBean;
import procesos.daos.BeanPQBUSCAR_ERROR;
import procesos.daos.BeanPQBorrarSolicitud;
import procesos.daos.BeanPQCrearSolicitud;
import procesos.daos.BeanPQELIMINAR_COLA;
import procesos.daos.BeanPQERROR_SERVICIO;
import procesos.daos.BeanPQFINALIZAR_ERROR;
import procesos.daos.BeanPQFINALIZAR_ERROR_SERVICIO;
import procesos.daos.BeanPQREACTIVAR_ERROR;
import procesos.daos.BeanPQReasignarSolicitud;
import procesos.daos.BeanPQTomarSolicitud;
import trafico.beans.daos.BeanPQGeneral;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.ErrorSolicitudExcepcion;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;

@Service
public class ModeloSolicitud extends ModeloBasePQ {

	private static final String SEPARATOR_TOKEN = ", ";

	private static final String SOLICITUD_BORRADA_CORRECTAMENTE = "Solicitud borrada correctamente:";
	private static final String SOLICITUD_INSCRITA_CORRECTAMENTE = "Solicitud inscrita Correctamente";
	private static final String ERROR_AL_CREAR_UNA_SOLICITUD = "Error al crear una solicitud ";
	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final String NOMBRE_HOST_SOLICITUD_PROCESOS2 = "nombreHostSolicitudProcesos2";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloSolicitud.class);

	@Autowired
	private ServicioCorreo servicioCorreo;
	
	@Autowired
	ServicioMensajeErrorServicio mensajeErrorServicio;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;

	@SuppressWarnings("unchecked")
	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		
		ListaRegistros listaRegistros = new ListaRegistros();

		
		//Datos de la búsqueda.
		//Datos que sacamos de la sesión para la búsqueda
		BeanPQBUSCAR_ERROR beanPQBuscarERROR = new BeanPQBUSCAR_ERROR();
//		beanConsultaTramite.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiado());
		
		//Datos de paginación, que pasamos por defecto
		beanPQBuscarERROR.setPAGINA(new BigDecimal(pagina));
		beanPQBuscarERROR.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanPQBuscarERROR.setCOLUMNA_ORDEN(columnaOrden);
		beanPQBuscarERROR.setORDEN(orden.toString().toUpperCase());
		
		//Datos de búsqueda del formulario.
		Map<String,Object> parametrosBusqueda = getParametrosBusqueda();
		beanPQBuscarERROR.setESTADO((BigDecimal) parametrosBusqueda.get(ConstantesSession.ESTADO_TRAMITE_CONSULTA));
		beanPQBuscarERROR.setTIPO_TRAMITE((String) parametrosBusqueda.get(ConstantesSession.TIPO_TRAMITE_CONSULTA));
		beanPQBuscarERROR.setID_TRAMITE((BigDecimal) parametrosBusqueda.get(ConstantesSession.ID_TRAMITE));
		beanPQBuscarERROR.setNUM_COLEGIADO((String) parametrosBusqueda.get(ConstantesSession.NUM_COLEGIADO));
		// Try catch porque no se puede hacer throws sin modificar la clase abstracta ModeloBase:
		try{
			String horaEntrada = (String) parametrosBusqueda.get(ConstantesSession.HORA_ENTRADA);
			Date fechaEntradaDesde = (Date) parametrosBusqueda.get(ConstantesSession.FECHA_ENTRADA_DESDE);
			if(horaEntrada != null && !horaEntrada.equals("")){
				if(fechaEntradaDesde == null){
					fechaEntradaDesde = new Date();
				}
				utilesFecha.setHoraEnDate(fechaEntradaDesde, horaEntrada);
			}
			if(fechaEntradaDesde != null){
				Timestamp tsFechaEntradaDesde = new Timestamp(fechaEntradaDesde.getTime());
				beanPQBuscarERROR.setFECHA_ENTRADA_DESDE(tsFechaEntradaDesde);
			}
			Date fechaEntradaHasta = (Date) parametrosBusqueda.get(ConstantesSession.FECHA_ENTRADA_HASTA);
			Timestamp tsFechaEntradaHasta = null;
			if(fechaEntradaHasta != null){
				tsFechaEntradaHasta = new Timestamp(fechaEntradaHasta.getTime());
				beanPQBuscarERROR.setFECHA_ENTRADA_HASTA(tsFechaEntradaHasta);
			}
		}catch(Throwable e){
			log.error("Ha ocurrido el siguiente error mapeando los campos de fecha de la jsp en el bean del modelo:\n" + e);
		}
		
		//Realiza la llamada genérica para la consulta
	
		ResultBean resultadoBuscar = buscarSolicitudesErrorServicio(beanPQBuscarERROR);
		
		if(resultadoBuscar.getError()){
			return listaRegistros;
		}
		else{
			List<Object> listaCursor = (List<Object>) resultadoBuscar.getAttachment("CURSOR");
			List<colaCursor> listaBeanVista = new ArrayList<colaCursor>(); 
			
			for (Object object : listaCursor) {
				listaBeanVista.add((colaCursor)object);
			}
			Integer cuenta = utiles.convertirBigDecimalAInteger(beanPQBuscarERROR.getCUENTA());
			listaRegistros.setTamano(cuenta);
			listaRegistros.setLista(listaCursor);
			
		}
		
		return listaRegistros;		
		
	}
	
	public HashMap <String,Object> crearSolicitud(BeanPQCrearSolicitud beanPQCrearSolicitud) {
		ResultBean resultBean = new ResultBean(); 
		ColaBean colaBean = new ColaBean(); 
		beanPQCrearSolicitud.setP_NODO(getNodoSolicitud());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPQCrearSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "CREAR_SOLICITUD", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje(ERROR_AL_CREAR_UNA_SOLICITUD); 
			resultBean.setError(true); 
			resultBean.setMensaje(resultBean .getMensaje() + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
		} else {			
			resultBean.setMensaje(SOLICITUD_INSCRITA_CORRECTAMENTE);
			resultBean.setError(false); 
		}

		HashMap<String,Object> resultadoCrearSolicitud = new HashMap<String, Object>();
		colaBean.setIdEnvio((BigDecimal) respuestaSolicitud.getParametro("P_ID_ENVIO"));
		colaBean.setCola((String) respuestaSolicitud.getParametro("P_COLA")); 

		resultadoCrearSolicitud.put(ConstantesPQ.RESULTBEAN, resultBean); 		
		resultadoCrearSolicitud.put(ConstantesPQ.BEANPANTALLA, colaBean); 

		return resultadoCrearSolicitud;
	}
	
	public HashMap <String,Object> crearSolicitudProcesos2(BeanPQCrearSolicitud beanPQCrearSolicitud) {
		ResultBean resultBean = new ResultBean(); 
		ColaBean colaBean = new ColaBean(); 
		beanPQCrearSolicitud.setP_NODO(getNodoSolicitudProcesos2());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPQCrearSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "CREAR_SOLICITUD", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje(ERROR_AL_CREAR_UNA_SOLICITUD); 
			resultBean.setError(true); 
			resultBean.setMensaje(resultBean .getMensaje() + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
		} else {			
			resultBean.setMensaje(SOLICITUD_INSCRITA_CORRECTAMENTE);
			resultBean.setError(false); 
		}

		HashMap<String,Object> resultadoCrearSolicitud = new HashMap<String, Object>();
		colaBean.setIdEnvio((BigDecimal) respuestaSolicitud.getParametro("P_ID_ENVIO"));
		colaBean.setCola((String) respuestaSolicitud.getParametro("P_COLA")); 

		resultadoCrearSolicitud.put(ConstantesPQ.RESULTBEAN, resultBean); 		
		resultadoCrearSolicitud.put(ConstantesPQ.BEANPANTALLA, colaBean); 

		return resultadoCrearSolicitud;
	}

	public void crearSolicitudExcep(BigDecimal numExpediente,BigDecimal usuario,TipoTramiteTrafico tipoTramite, String proceso) throws CrearSolicitudExcepcion{ 

		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud(); 
		beanPQCrearSolicitud.setP_ID_TRAMITE(numExpediente);
		beanPQCrearSolicitud.setP_ID_USUARIO(usuario); 
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(tipoTramite.getValorEnum());
		beanPQCrearSolicitud.setP_XML_ENVIAR(proceso + numExpediente+ ".xml"); 
		beanPQCrearSolicitud.setP_PROCESO(proceso); 
		beanPQCrearSolicitud.setP_NODO(getNodoSolicitud());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPQCrearSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "CREAR_SOLICITUD", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));
		
		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) 
			{
			throw new CrearSolicitudExcepcion((String) respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
			}
	}
	
	//TODO MPC. Cambio IVTM. Sobrecarga del método, para poder pasarle valores nulos.Son 3 los métodos cambiados.
	/**
	 * 
	 * @param usuario
	 * @param tipoTramite
	 * @param proceso
	 * @param xmlAEnviar
	 * @throws CrearSolicitudExcepcion
	 */
	
	public void crearSolicitud(BigDecimal usuario,TipoTramiteTrafico tipoTramite, String proceso,String xmlAEnviar) throws CrearSolicitudExcepcion{ 
		crearSolicitud(null, usuario, tipoTramite, proceso, xmlAEnviar);
	}
	
	/**
	 * 
	 * @param numExpediente
	 * @param usuario
	 * @param tipoTramite
	 * @param proceso
	 * @param xmlAEnviar
	 * @throws CrearSolicitudExcepcion
	 */
	
	public void crearSolicitud(BigDecimal numExpediente,BigDecimal usuario,TipoTramiteTrafico tipoTramite, String proceso,String xmlAEnviar) throws CrearSolicitudExcepcion{ 

		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud(); 
		beanPQCrearSolicitud.setP_ID_TRAMITE(numExpediente);
		beanPQCrearSolicitud.setP_ID_USUARIO(usuario); 
		String valorTipoTramite = tipoTramite!=null ? tipoTramite.getValorEnum(): null;
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(valorTipoTramite);
		beanPQCrearSolicitud.setP_XML_ENVIAR(xmlAEnviar); 
		beanPQCrearSolicitud.setP_PROCESO(proceso); 
		beanPQCrearSolicitud.setP_NODO(getNodoSolicitud());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPQCrearSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "CREAR_SOLICITUD", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));
		
		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) {
			throw new CrearSolicitudExcepcion((String) respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
		}
	}
	
	/**
	 * Metodo que lee solicitudes y devuelve excepcion si no devuelve ninguna.
	 * @return
	 * @throws OegamExcepcion 
	 */
	
	public ColaBean tomarSolicitud (String proceso,String cola) throws SinSolicitudesExcepcion {
		BeanPQTomarSolicitud beanPQTomarSolicitud = new BeanPQTomarSolicitud(); 
		beanPQTomarSolicitud.setP_NODO(getNodoSolicitud());
		beanPQTomarSolicitud.setP_PROCESO(proceso); 
		beanPQTomarSolicitud.setP_COLA(cola); 

		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPQTomarSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "TOMAR_SOLICITUD", BeanPQGeneral.class); 

			
		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) {
			throw new SinSolicitudesExcepcion((String) respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
		} else {
			log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
			log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
			log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));
			ColaBean solicitud = new ColaBean();
			solicitud.setIdEnvio((BigDecimal) respuestaSolicitud.getParametro("P_ID_ENVIO"));
			solicitud.setCola((String) respuestaSolicitud.getParametro("P_COLA")); 
			solicitud.setEstado((BigDecimal) respuestaSolicitud.getParametro("P_ESTADO"));
			solicitud.setTipoTramite((String) respuestaSolicitud.getParametro("P_TIPO_TRAMITE"));

			if(respuestaSolicitud.getParametro("P_FECHA_HORA") != null) {
				solicitud.setFecha_hora(respuestaSolicitud.getParametro("P_FECHA_HORA").toString());
			}
			solicitud.setIdTramite((BigDecimal)respuestaSolicitud.getParametro("P_ID_TRAMITE"));
			solicitud.setIdUsuario((BigDecimal)respuestaSolicitud.getParametro("P_ID_USUARIO"));
			solicitud.setNumeroIntento((BigDecimal)respuestaSolicitud.getParametro("P_N_INTENTO"));
			
			if(respuestaSolicitud.getParametro("P_ID_CONTRATO") != null){
				solicitud.setIdContrato((BigDecimal) respuestaSolicitud.getParametro("P_ID_CONTRATO"));
			}
			
			if(respuestaSolicitud.getParametro("P_PROCESO") != null) {
				solicitud.setProceso(respuestaSolicitud.getParametro("P_PROCESO").toString());
			}
			
			if(respuestaSolicitud.getParametro("P_XML_ENVIAR") != null) {
				solicitud.setXmlEnviar(respuestaSolicitud.getParametro("P_XML_ENVIAR").toString());
			}
			
			return solicitud; 
		//tomar fechahora
		}
	}
	
	/**
	 * PROCEDURE SOLICITUD_CORRECTA ( P_ID_ENVIO IN NUMBER,
                                  P_CODE OUT NUMBER,
                                  P_SQLERRM OUT VARCHAR2);
	 */
	public HashMap <String, Object> borrarSolicitud(BigDecimal idEnvio,String respuesta) 
	{
		ResultBean resultBean = new ResultBean(); 
		BeanPQBorrarSolicitud beanPqBorrarSolicitud = new BeanPQBorrarSolicitud();

		beanPqBorrarSolicitud.setP_NODO(getNodoSolicitud());
		beanPqBorrarSolicitud.setP_RESPUESTA(respuesta);
		beanPqBorrarSolicitud.setP_ID_ENVIO(idEnvio);
		RespuestaGenerica respuestaSolicitud = ejecutarProc(beanPqBorrarSolicitud, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "SOLICITUD_CORRECTA", BeanPQGeneral.class); 

		

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) 
			{
			log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
			log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
			log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));
			resultBean.setMensaje((String)respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM) + idEnvio); 
			resultBean.setError(true); 
			resultBean.setMensaje(resultBean .getMensaje() + " "); 
			}
		else{			
			resultBean.setMensaje(SOLICITUD_BORRADA_CORRECTAMENTE + idEnvio);
			resultBean.setError(false); 
			} 
		HashMap<String,Object> resultadoBorrarSolicitud = new HashMap<String,Object>();
		resultadoBorrarSolicitud.put(ConstantesPQ.RESULTBEAN, resultBean); 		
		return resultadoBorrarSolicitud; 
	}
	
	public HashMap<String, Object> borrarSolicitudExcep(BigDecimal idEnvio,String respuesta) throws BorrarSolicitudExcepcion{
		HashMap<String,Object> resultadoBorrarSolicitud = borrarSolicitud(idEnvio, respuesta);
		ResultBean resultBean = (ResultBean) resultadoBorrarSolicitud.get(ConstantesPQ.RESULTBEAN); 
		if (resultBean.getError())
			throw new BorrarSolicitudExcepcion(resultBean.getMensaje()); 
		else return resultadoBorrarSolicitud; 
	}
	
	public HashMap <String, Object> errorServicio(BigDecimal idEnvio,String respuesta) 
	{
		ResultBean resultBean = new ResultBean(); 
		BeanPQERROR_SERVICIO beanPqErrorServicio = new BeanPQERROR_SERVICIO();

		beanPqErrorServicio.setP_ID_ENVIO(idEnvio); 
		beanPqErrorServicio.setP_RESPUESTA(respuesta); 
		beanPqErrorServicio.execute();  

		log.info(ConstantesPQ.LOG_P_CODE + beanPqErrorServicio.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPqErrorServicio.getP_INFORMACION());
		log.info(ConstantesPQ.LOG_P_SQLERRM + beanPqErrorServicio.getP_SQLERRM());

		if ((!ConstantesPQ.pCodeOk.equals(beanPqErrorServicio.getP_CODE()))) 
			{
			resultBean.setMensaje("Error al marcar la solicitud como error de servicio :" + idEnvio+ " " + beanPqErrorServicio.getP_SQLERRM()); 
			resultBean.setError(true); 
			}
		else
			{			
			resultBean.setMensaje("Solicitud asignada como error servicio:" + idEnvio + " " + beanPqErrorServicio.getP_INFORMACION());
			resultBean.setError(false);
			}
		
		HashMap<String,Object> resultadoMarcarErrorServicio = new HashMap<String,Object>();
		resultadoMarcarErrorServicio.put(ConstantesPQ.RESULTBEAN, resultBean); 		
		
		return resultadoMarcarErrorServicio; 
	}
	
	public ResultBean buscarSolicitudesErrorServicio(BeanPQBUSCAR_ERROR beanPQBuscarERROR) 
	{
		ResultBean resultBean = new ResultBean(); 
		 
		List<Object> listaSolicitudes =  beanPQBuscarERROR.execute(colaCursor.class);  

		log.info(ConstantesPQ.LOG_P_CODE + beanPQBuscarERROR.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQBuscarERROR.getP_INFORMACION());
		log.info(ConstantesPQ.LOG_P_CUENTA + beanPQBuscarERROR.getCUENTA()); 

		if ((!ConstantesPQ.pCodeOk.equals(beanPQBuscarERROR.getP_CODE()))) 
			{
			resultBean.setMensaje("Error al recuperar la lista de solicitudes marcadas como error servicio:"); 
			resultBean.setError(true); 
			resultBean.setMensaje(resultBean.getMensaje() + " "); 
			}
		else
			{			
			resultBean.setMensaje("recuperada correctamente la lista de solicitudes en error servicio:");
			resultBean.setError(false); 
			}
		
		resultBean.addAttachment("CURSOR", listaSolicitudes); 
		 	
		return resultBean; 
	}
	
	public ResultBean reactivarError(BigDecimal solicitud) 
	{
		ResultBean resultBean = new ResultBean(); 
		 
		BeanPQREACTIVAR_ERROR beanPQREACTIVARERROR = new BeanPQREACTIVAR_ERROR();
		beanPQREACTIVARERROR.setP_ID_ENVIO(solicitud); 
		beanPQREACTIVARERROR.execute();  

		log.info(ConstantesPQ.LOG_P_CODE + beanPQREACTIVARERROR.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQREACTIVARERROR.getP_INFORMACION());

		if ((!ConstantesPQ.pCodeOk.equals(beanPQREACTIVARERROR.getP_CODE()))) 
			{
			log.error(ConstantesPQ.LOG_P_ERROR + beanPQREACTIVARERROR.getP_SQLERRM());
			resultBean.setMensaje(beanPQREACTIVARERROR.getP_SQLERRM()); 
			resultBean.setError(true); 
			}
		else
			{			
			resultBean.setMensaje("Reactivada correctamente");
			resultBean.setError(false); 
			}
		 	
		return resultBean; 
	}
	
	public ResultBean finalizarError(BigDecimal solicitud) 
	{
		ResultBean resultBean = new ResultBean(); 
		 
		BeanPQFINALIZAR_ERROR beanPQFINALIZARERROR = new BeanPQFINALIZAR_ERROR();
		beanPQFINALIZARERROR.setP_ID_ENVIO(solicitud); 
		beanPQFINALIZARERROR.execute();  

		log.info(ConstantesPQ.LOG_P_CODE + beanPQFINALIZARERROR.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQFINALIZARERROR.getP_INFORMACION());

		if ((!ConstantesPQ.pCodeOk.equals(beanPQFINALIZARERROR.getP_CODE()))) 
			{
			log.error(ConstantesPQ.LOG_P_ERROR + beanPQFINALIZARERROR.getP_SQLERRM());
			resultBean.setMensaje(beanPQFINALIZARERROR.getP_SQLERRM()); 
			resultBean.setError(true); 
			}
		else
			{			
			resultBean.setMensaje("Finalizada con Error correctamente");
			resultBean.setError(false); 
			}
		 	
		return resultBean; 
	}
	
	public ResultBean finalizarErrorServicio(BigDecimal solicitud) 
	{
		ResultBean resultBean = new ResultBean(); 
		 
		BeanPQFINALIZAR_ERROR_SERVICIO beanPQFINALIZARERRORSERVICIO = new BeanPQFINALIZAR_ERROR_SERVICIO();
		beanPQFINALIZARERRORSERVICIO.setP_ID_ENVIO(solicitud); 
		beanPQFINALIZARERRORSERVICIO.execute();  

		log.info(ConstantesPQ.LOG_P_CODE + beanPQFINALIZARERRORSERVICIO.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQFINALIZARERRORSERVICIO.getP_INFORMACION());

		if ((!ConstantesPQ.pCodeOk.equals(beanPQFINALIZARERRORSERVICIO.getP_CODE()))) 
			{
			log.error(ConstantesPQ.LOG_P_ERROR + beanPQFINALIZARERRORSERVICIO.getP_SQLERRM());
			resultBean.setMensaje(beanPQFINALIZARERRORSERVICIO.getP_SQLERRM()); 
			resultBean.setError(true); 
			}
		else
			{			
			resultBean.setMensaje("Finalizada con Error Servicio correctamente");
			resultBean.setError(false); 
			}
		 	
		return resultBean; 
	}
	
	// Método que saca solicitudes de la cola
	public ResultBean eliminarSolicitud(BigDecimal solicitud) 
	{
		ResultBean resultBean = new ResultBean(); 
		 
		BeanPQELIMINAR_COLA beanPQELIMINAR_COLA  = new BeanPQELIMINAR_COLA();
		beanPQELIMINAR_COLA.setP_ID_ENVIO(solicitud); 
		beanPQELIMINAR_COLA.execute();  

		//log.info(ConstantesPQ.LOG_P_CODE + beanPQELIMINAR_COLA.getP_CODE());
		//log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQELIMINAR_COLA.getP_INFORMACION());

		if ((!ConstantesPQ.pCodeOk.equals(beanPQELIMINAR_COLA.getP_CODE()))) 
			{
			//log.error(ConstantesPQ.LOG_P_ERROR + beanPQELIMINAR_COLA.getP_SQLERRM());
			resultBean.setMensaje(beanPQELIMINAR_COLA.getP_SQLERRM()); 
			resultBean.setError(true); 
			}
		else
			{			
			resultBean.setMensaje("La solicitud ha sido eliminada correctamente de la cola");
			resultBean.setError(false); 
			}
		 	
		return resultBean; 
	}
	
	public String getNodoSolicitud() {
		return gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD);
	}
	
	public String getNodoSolicitudProcesos2() {
		return gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_PROCESOS2);
	}
	
	/*
	 * PROCEDURE SOLICITUD_ERRONEA(P_ID_ENVIO IN NUMBER,
                      P_RESPUESTA IN VARCHAR2,
                      P_CODE OUT NUMBER,
                      P_SQLERRM OUT VARCHAR2);
	 */
	public HashMap <String, Object> errorSolicitud(BigDecimal idEnvio, String respuesta)
	{

		ResultBean resultBean = new ResultBean(); 
		BeanPQReasignarSolicitud bean = new BeanPQReasignarSolicitud();
		bean.setP_ID_ENVIO(idEnvio);
		bean.setP_RESPUESTA(respuesta);
		bean.setP_NODO(getNodoSolicitud());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(bean, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "SOLICITUD_ERRONEA", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) 
			{
			resultBean.setMensaje("Error al reasignar la solicitud"); 
			resultBean.setError(true); 
			resultBean.setMensaje(resultBean .getMensaje() + " "); 
			}
		else{			
			resultBean.setMensaje("Solicitud reasignada de forma correcta");
			resultBean.setError(false); 
			} 
		HashMap<String,Object> resultadoCrearSolicitud = new HashMap<String,Object>();
		resultadoCrearSolicitud.put(ConstantesPQ.RESULTBEAN, resultBean); 		
		return resultadoCrearSolicitud; 
	}
	
	public void errorSolicitudExcep(BigDecimal idEnvio, String respuesta) throws ErrorSolicitudExcepcion

		{
		BeanPQReasignarSolicitud bean = new BeanPQReasignarSolicitud();
		bean.setP_ID_ENVIO(idEnvio);
		bean.setP_RESPUESTA(respuesta);
		bean.setP_NODO(getNodoSolicitud());

		RespuestaGenerica respuestaSolicitud = ejecutarProc(bean, valoresSchemas.getSchema(), ValoresCatalog.PQ_PROCESO, "SOLICITUD_ERRONEA", BeanPQGeneral.class); 

		log.info(ConstantesPQ.LOG_P_CODE + respuestaSolicitud .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaSolicitud .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud .getParametro(ConstantesPQ.P_CODE)))) 
			{
			throw new ErrorSolicitudExcepcion((String) respuestaSolicitud .getParametro(ConstantesPQ.P_SQLERRM)); 
			}
		}
	/**
	 * Encapsula la lógica de envío de un correo de notificación de que cierta
	 * petición ha superado su número máximo de envios con ERROR_RECUPERABLE
	 * pasando a estado 9 : ERROR_SERVICIO
	 * @param idEnvio
	 * @param error
	 */
	public void notificarErrorServicio(ColaBean cola, String error) {

		log.info("Inicio del metodo notificarErrorServicio.");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			String destinatariosNotificacion = gestorPropiedades.valorPropertie("numero.destinatarios.notificacion.error.servicio");

			StringBuffer direcciones = new StringBuffer();

			if (destinatariosNotificacion != null) {
				try {
					// Convierte la propiedad en número para establecer la
					// longitud del array de direcciones:
					int numDesNot = Integer.parseInt(destinatariosNotificacion);
					boolean flagFirst = true;
					for (int i = 0; i < numDesNot; i++) {
						// Recorre el fichero de propiedades recuperando las
						// direcciones:
						String direccion = gestorPropiedades.valorPropertie("direccion.notificacion.error.servicio." + (i + 1));
						if (direccion != null) {
							if (flagFirst) {
								flagFirst = false;
							} else {
								direcciones.append(SEPARATOR_TOKEN);
							}
							direcciones.append(direccion);
						}
					}
				} catch (NumberFormatException e) {
					log.error("La propiedad 'numero.destinatarios.notificacion.error.servicio' ha de ser un numero", e);
				}
			}

			StringBuffer subject = new StringBuffer("ERROR DEL SERVICIO EN: ").append(gestorPropiedades.valorPropertie("Entorno"));
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificación desde la Oficina Electrónica de Gestión Administrativa (OEGAM): <br>La petición de envío con identificador: <b>")
					.append(cola.getIdEnvio())
					.append("</b> y número de expediente: <b> ")
					.append(cola.getIdTramite())
					.append("</b> ha pasado a estado 9 (ERROR DEL SERVICIO).<br>- Información de la petición:<br>   * Tipo de trámite : <b>")
					.append(cola.getTipoTramite())
					.append("</b><br>   * Proceso         : <b>")
					.append(cola.getProceso())
					.append("</b><br>   * Respuesta       : <b>")
					.append(error)
					.append("</b><br></span><br><br>");

			ResultBean resultEnvio;
			MensajeErrorServicioDto mensaje = new MensajeErrorServicioDto();
			mensaje.setCola(cola.getCola());
			mensaje.setDescripcion(error);
			mensaje.setFecha(new Date());
			mensaje.setIdEnvio(cola.getIdEnvio().longValue());
			mensaje.setProceso(cola.getProceso());
			ResultadoMensajeErrorServicio respuesta = mensajeErrorServicio.guardar(mensaje);
			if(respuesta.isError()){				
				resultEnvio = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject.toString(), direcciones.toString(), null, null, null);
				if(resultEnvio==null || resultEnvio.getError())
					log.error("Error en la notificacion de error servicio");				
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("Error en la notificacion de error servicio", e);
		}

		log.info("Fin del metodo notificarErrorServicio");
	}


	/**
	 * Encapsula la lógica de envío de un correo de notificación de que cierta
	 * petición ha superado su número máximo de envios con ERROR_RECUPERABLE
	 * pasando a estado 9 : ERROR_SERVICIO
	 * @param idEnvio
	 * @param error
	 */
	public void notificarErrorServicio(String proceso, String error) {

		log.info("Inicio del metodo notificarErrorServicio.");

		try {
			String destinatariosNotificacion = gestorPropiedades.valorPropertie("numero.destinatarios.notificacion.error.servicio");
			
			// Error servicio en el caso del proceso YERBABUENAN
			String direccionErrorYerbabuena = gestorPropiedades.valorPropertie("direccion.error.servicio.yerbanuena.noche");
			String direccionOcultaErrorYerbabuena = gestorPropiedades.valorPropertie("direccion.error.servicio.yerbanuena.noche.cco");

			StringBuffer direcciones = new StringBuffer();

			if (destinatariosNotificacion != null) {
				try {
					// Convierte la propiedad en número para establecer la
					// longitud del array de direcciones:
					int numDesNot = Integer.parseInt(destinatariosNotificacion);
					boolean flagFirst = true;
					for (int i = 0; i < numDesNot; i++) {
						// Recorre el fichero de propiedades recuperando las
						// direcciones:
						String direccion = gestorPropiedades.valorPropertie("direccion.notificacion.error.servicio." + (i + 1));
						if (direccion != null) {
							if (flagFirst) {
								flagFirst = false;
							} else {
								direcciones.append(SEPARATOR_TOKEN);
							}
							direcciones.append(direccion);
						}
					}
				} catch (NumberFormatException e) {
					log.error("La propiedad 'numero.destinatarios.notificacion.error.servicio' ha de ser un numero", e);
				}
			}
			
			StringBuffer subject = new StringBuffer("ERROR DEL SERVICIO EN: ").append(gestorPropiedades.valorPropertie("Entorno"));
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificación desde la Oficina Electrónica de Gestión Administrativa (OEGAM): <br>ERROR DEL SERVICIO: <br>")
					.append("* Proceso         : <b>")
					.append(proceso)
					.append("</b><br>   * Respuesta       : <b>")
					.append(error)
					.append("</b><br></span><br><br>");

			servicioCorreo.enviarCorreo(texto.toString(), null, null, subject.toString(), direcciones.toString(), null,null,null);

		} catch (OegamExcepcion | IOException e) {
			log.error("Error en la notificacion de error servicio", e);
		}

		log.info("Fin del metodo notificarErrorServicio");
	}

	/**
	 * Notifica que se ha caido un proceso y debería estar arriba.
	 * 
	 * @param proceso que está caido
	 * @throws OegamExcepcion 
	 * @throws Throwable
	 */
	public void notificacionCaidaProceso(ProcesoBean proceso) throws OegamExcepcion  {
		String titulo = gestorPropiedades.valorPropertie("notificacion.caida.proceso.titulo");
		String direcciones = gestorPropiedades.valorPropertie("notificacion.caida.proceso.direcciones");

		StringBuffer sb = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificación desde la Oficina Electrónica de Gestión Administrativa (OEGAM): <br>El proceso <b>")
				.append(proceso.getNombre()).append(" - ")
				.append(proceso.getDescripcion())
				.append("<br><br></span></b> está parado y no se ha podido arrancar<br>");

		// Invocación al privado que envia el mensaje:
		try {
			servicioCorreo.enviarCorreo(sb.toString(), null, null, titulo, direcciones, null,null,null);
		} catch (IOException e) {
			log.error("Error en la notificacion de caida de proceso", e);
		}
	}

	public ServicioMensajeErrorServicio getMensajeErrorServicio() {
		return mensajeErrorServicio;
	}

	public void setMensajeErrorServicio(ServicioMensajeErrorServicio mensajeErrorServicio) {
		this.mensajeErrorServicio = mensajeErrorServicio;
	}

}
