package utilidades.mensajes;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class Claves {

	//private static final ILoggerOegam Log = LoggerOegam.getLogger(Claves.class);

	public static final String CLAVE_SIN_REGISTROS="sinRegistros";
	public static final String CLAVE_CONTRATOS="Contratos";
	public static final String CLAVE_ARBOL="miArbol";
	public static final String CLAVE_GESTOR_ARBOL="GestorArbol";
	public static final String CLAVE_GESTOR_ACCESO="GestorAcceso";
	public static final String CLAVE_GESTOR_ERRORES="GestorErrores";
	public static final String CLAVE_GESTOR_MENSAJES="GestorMensajes";
	public static final String CLAVE_ERROR_OEGAM="ErrorOegam";
	public static final String CLAVE_PROPIEDADES_CONEXION_BD="PropiedadesConexionBD";
	public static final String CLAVE_PAGINA_INICIO="/bienvenidaLink.action";
	public static final String CLAVE_Cerrar_Session="/invalidaCerrarSession.action";
	public static final String CLAVE_Usuario="user";
	public static final String CLAVE_NIF_Viafirma="NIF_Viafirma";
	public static final String CLAVE_CIF_COLEGIO="CIF_VIAFIRMA";
	public static final String CLAVE_Login_ACTION="/Login.action";
	public static final String CLAVE_Index_ACTION="/index.action";
	public static final String CLAVE_Registrar_ACTION="/Registrar.action";
	public static final String CLAVE_Arbol_ACTION="/Arbol.action";
	public static final String CLAVE_SECURITY_ACTION="Security.action";
	public static final String CLAVE_Contrato_Seleccionado="contratoSeleccionado";
	public static final String CLAVE_Mensaje_Error="mensajeError";
	//Comentarios del log de escrituras
	public static final String CLAVE_LOG_ESCRITURA="ESCRITURA: ";
	public static final String CLAVE_LOG_ESCRITURA600_INICIO="ESCRITURA 600: Inicio--";
	public static final String CLAVE_LOG_ESCRITURA600_FIN="ESCRITURA 600: Fin--";
	public static final String CLAVE_LOG_ESCRITURA601_INICIO="ESCRITURA 601: Inicio--";
	public static final String CLAVE_LOG_ESCRITURA601_FIN="ESCRITURA 601: Fin--";
	//Comentarios del log de envío de escrituras al registro
	public static final String CLAVE_LOG_ENVIO_ESCRITURA_ERROR="ENVIO_ESCRITURA: Error--";
	public static final String CLAVE_LOG_ENVIO_ESCRITURA_INICIO="ENVIO_ESCRITURA: Inicio--";
	public static final String CLAVE_LOG_ENVIO_ESCRITURA_FIN="ENVIO_ESCRITURA: Fin--";
	public static final String CLAVE_LOG_ENVIO_ESCRITURA_INFO = "ENVIO_ESCRITURA: Info--";
	//Comentarios del log de trámites de tráfico
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA="Consulta Tramite Trafico: Inicio--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_MATRICULACION="Matriculacion Tramite Trafico: Inicio--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_BAJA_INICIO="Baja Tramite Trafico: Inicio--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_BAJA_FIN="Baja Tramite Trafico: Fin--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_BAJA="Baja Tramite Trafico: Fin--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_DUPLICADO_INICIO="Baja Tramite Trafico: Inicio--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_DUPLICADO_FIN="Baja Tramite Trafico: Fin--";
	public static final String CLAVE_LOG_TRAFICO_TRAMITE_DUPLICADO="Baja Tramite Trafico: Fin--";
	public static final String CLAVE_LOG_TRAFICO_CONSULTA_VEHICULO_INICIO = "Consulta Vechiculo : Inicio--";
	public static final String CLAVE_LOG_TRAFICO_CONSULTA_VEHICULO_FIN = "Consulta Vechiculo : Fin--";
	public static final String CLAVE_LOG_TRAFICO_CONSULTA_HISTORICO_SOLICITUD_INICIO = "Historico Solicitud: Inicio--";
	public static final String CLAVE_LOG_TRAFICO_CONSULTA_HISTORICO_SOLICITUD_FIN = "Historico Solicitud: Fin--";

	public static final String CLAVE_LOG_TRAFICO_TRAMITE_JUSTIFICANTE="Justificantes Profesionales Tramite Trafico: Inicio--";

	// DRC@15-06-2012 Comentarios del log de facturación
	public static final String CLAVE_LOG_FACTURACION_ALTA="Alta Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_BAJA="Baja Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_MODIFICACION="Modificar Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_CONSULTA="Consulta Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_CONSULTA_RECTIFICATIVA="Consulta Rectificativa Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_EXPORTAR="Exportar Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_GENERAR_PDF="Generar PDF Facturacion : Gestion--";
	public static final String CLAVE_LOG_FACTURACION_CONSULTA_CONCEPTO="Consulta Concepto Facturacion : Gestion--";

	//Comentarios del log de registradores

	//MODELO GENERICO LOGS
	public static final String CLAVE_LOG_MODELO_GENERICO_INICIO="MODELO GENERICO: Inicio--";
	public static final String CLAVE_LOG_MODELO_GENERICO_ERROR="MODELO GENERICO: Error-- ";

	//MODELO 1 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO1_INICIO="REGISTRADORES MODELO 1: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO1_FIN="REGISTRADORES MODELO 1: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO1_ERROR="REGISTRADORES MODELO 1: Error--";

	// MODELO 2 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO2_INICIO="REGISTRADORES MODELO 2: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO2_FIN="REGISTRADORES MODELO 2: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO2_ERROR="REGISTRADORES MODELO 2: Error--";

	//MODELO 3 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO3_INICIO="REGISTRADORES MODELO 3: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO3_FIN="REGISTRADORES MODELO 3: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO3_ERROR="REGISTRADORES MODELO 3: Error--";

	//MODELO 4 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO4_INICIO="REGISTRADORES MODELO 4: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO4_FIN="REGISTRADORES MODELO 4: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO4_ERROR="REGISTRADORES MODELO 4: Error--";

	//MODELO 5 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO5_INICIO="REGISTRADORES MODELO 5: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO5_FIN="REGISTRADORES MODELO 5: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO5_ERROR="REGISTRADORES MODELO 5: Error--";

	//CUENTA 11 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO11_INICIO="REGISTRADORES MODELO 11: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO11_FIN="REGISTRADORES MODELO 11: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO11_ERROR="REGISTRADORES MODELO 11: Error--";

	//LIBRO 11 LOGS
	public static final String CLAVE_LOG_REGISTRADORES_MODELO12_INICIO="REGISTRADORES MODELO 12: Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO12_FIN="REGISTRADORES MODELO 12: Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_MODELO12_ERROR="REGISTRADORES MODELO 12: Error--";

	// GENÉRICOS A TODOS LOS MODELOS
	public static final String CLAVE_LOG_REGISTRADORES_COMUN_INICIO = "REGISTRADORES COMUN : Inicio--";
	public static final String CLAVE_LOG_REGISTRADORES_COMUN_FIN = "REGISTRADORES COMUN : Fin--";
	public static final String CLAVE_LOG_REGISTRADORES_COMUN_ERROR = "REGISTRADORES COMUN : Error--";
	public static final String CLAVE_LOG_REGISTRADORES_COMUN_INFO = "REGISTRADORES COMUN : Info--";

	// PARA ENVIO_ESCRITURAS
	public static final String CLAVE_LOG_ENVIO_ESCRITURAS_INICIO="ENVIO_ESCRITURAS: Inicio--";
	public static final String CLAVE_LOG_ENVIO_ESCRITURAS_FIN="ENVIO_ESCRITURAS: Fin--";
	public static final String CLAVE_LOG_ENVIO_ESCRITURAS_ERROR="ENVIO_ESCRITURAS: Error--";
	public static final String CLAVE_TIPO_FIRMA_ENVIO_ESCRITURAS_DOCUMENTACION_HSM = "HSM";
	public static final String CLAVE_TIPO_FIRMA_ENVIO_ESCRITURAS_DOCUMENTACION_APPLET = "APPLET";
	public static final String CLAVE_TIPO_FIRMA_ENVIO_ESCRITURAS_MENSAJE_HSM = "HSM";
	public static final String CLAVE_TIPO_FIRMA_ENVIO_ESCRITURAS_MENSAJE_APPLET = "APPLET";

	// PARA EL MODELO DE REGISTRADORES
	public static final String CLAVE_LOG_MODELO_REGISTRADORES_INICIO = "REGISTRADORES_MODELO: Inicio-- ";
	public static final String CLAVE_LOG_MODELO_REGISTRADORES_FIN = "REGISTRADORES_MODELO: Fin-- ";
	public static final String CLAVE_LOG_MODELO_REGISTRADORES_ERROR = "REGISTRADORES_MODELO: Error-- ";
	public static final String CLAVE_LOG_MODELO_REGISTRADORES_INFO = "REGISTRADORES_MODELO: Info-- ";
	public static final String CLAVE_TIPO_FIRMA_DPR_HSM = "HSM";
	public static final String CLAVE_TIPO_FIRMA_DPR_APPLET = "APPLET";

	//PARA LAS LICENCIAS CAM
	public static final String CLAVE_LOG_LICENCIAS_COMUN_INICIO = "LICENCIAS COMUN : Inicio--";
	public static final String CLAVE_LOG_LICENCIAS_COMUN_FIN = "LICENCIAS COMUN : Fin--";
	public static final String CLAVE_LOG_LICENCIAS_COMUN_ERROR = "LICENCIAS COMUN : Error--";
	public static final String CLAVE_LOG_LICENCIAS_COMUN_INFO = "LICENCIAS COMUN : Info--";

	// PARA ENVIO_MATRICULACION
	public static final String CLAVE_LOG_ENVIO_MATRICULACION_INICIO="ENVIO_MATRICULACION: Inicio--";
	public static final String CLAVE_LOG_ENVIO_MATRICULACION_FIN="ENVIO_MATRICULACION: Fin--";
	public static final String CLAVE_LOG_ENVIO_MATRICULACION_ERROR="ENVIO_MATRICULACION: Error--";
	private static List<Object> _listaPaginasAutorizadas=null;

	public static final String CLAVE_LOG_ENVIO_BAJA_INICIO="ENVIO_CUSTODIA_BAJA: Inicio--";
	public static final String CLAVE_LOG_ENVIO_BAJA_FIN="ENVIO_CUSTODIA_BAJA: Fin--";
	public static final String CLAVE_LOG_ENVIO_BAJA_ERROR="ENVIO_CUSTODIA_BAJA: Error--";

	// PARA LOS NOMBRES DE LOS PROCESOS:
	public static final String CLAVE_MENSAJES_REGISTRO = "REG";
	public static final String CLAVE_CONEXION_REGISTRO = "WREG";
	public static final String CLAVE_CONEXION_MATE = "MATE";

	// PARA EL LOG DEL PROCESO WREG:
	public static final String CLAVE_LOG_PROCESO_WREG = "PROCESO WREG = ";

	// PARA EL LOG DE PROCESOS
	public static final String CLAVE_LOG_PROCESOS_FIN_CORRECTO = "PROCESO: Fin Correcto: ";
	public static final String CLAVE_LOG_PROCESOS_ERROR = "PROCESO: Error: ";

	// PARA EL LOG DEL SERVLET DE VIAFIRMA:
	public static final String CLAVE_LOG_SERVLET_VIAFIRMA_INICIO = "SERVLET VIAFIRMA : Inicio - ";
	public static final String CLAVE_LOG_SERVLET_VIAFIRMA_FIN = "SERVLET VIAFIRMA : Fin - ";
	public static final String CLAVE_LOG_SERVLET_VIAFIRMA_ERROR = "SERVLET VIAFIRMA : Error - ";

	// PARA DEJAR TRAZA DEL VALOR DE LOS CONTEXT PARAM:
	public final static String CLAVE_LOG_CONTEXT_PARAM = "CONTEXT-PARAM: ";

	// PARA LA EJECUCION CORRECTA DE UN PROCESO:
	public static final String CLAVE_EJECUCION_CORRECTA_PROCESO = "Correcto";

	//TRATAMIENTO DE XML.
	public static final String ENCODING_UTF8 ="UTF-8";
	public static final String ENCODING_ISO88591 = "ISO-8859-1";
	public static final String SOLICITUD_REGISTRO_ENTRADA = "solicitud_registro_entrada";
	public static final String BR = " - ";

	// ERRORES:
	public static final String RETURN_ERROR = "error";

	// ESTADO CONTRATO
	public static final String ESTADO_CONTRATO_HABILITADO = "1";
	public static final String ESTADO_CONTRATO_DESHABILITADO = "2";
	public static final String ESTADO_CONTRATO_ELIMINADO = "3";

	// ESTADO PROCESOS
	public static final String ESTADO_PROCESO_ACTIVO = "Activo";
	public static final String ESTADO_PROCESO_PARADO = "Parado";

	// MODELO COLEGIADO
	public static final String CLAVE_LOG_MODELO_COLEGIADO_ERROR_COLEGIADO_BUSQUEDA = "Se ha producido un error en la ejecución del procedimiento"
			+ " BUSQUEDA del PQ_COLEGIADOS";
	public static final String CLAVE_LOG_MODELO_COLEGIADO_ERROR = "COLEGIADO MODELO : Error-- ";

	public static List<Object> getListaPaginasAutorizadas(){
		if(null==_listaPaginasAutorizadas){
		}
		return _listaPaginasAutorizadas;
	}

	public static void setObjetoDeContextoAplicacion(String clave, Object objeto){
		if(null==clave || clave.trim().length()==0){
			return;
		}

		Map<String, Object> aplicacion=ActionContext.getContext().getApplication();
		if(null==aplicacion){
			return;
		}

		aplicacion.put(clave, objeto);
	}

	public static Object getObjetoDeContextoAplicacion(String clave){
		if(null==clave || clave.trim().length()==0){
			return null;
		}

		Map<String, Object> aplicacion=ActionContext.getContext().getApplication();
		if(null==aplicacion){
			return null;
		}
		return aplicacion.get(clave);
	}

	public static void setObjetoDeContextoSesion(String clave, Object objeto){
		if(null==clave || clave.trim().length()==0){
			return;
		}
		Map<String, Object> sesion = null;
		if(ActionContext.getContext() != null){
			sesion=ActionContext.getContext().getSession();
		}
		if(null==sesion){
			return;
		}else{
			sesion.put(clave, objeto);
		}
	}

	public static Object getObjetoDeContextoSesion(String clave){
		if(null==clave || clave.trim().length()==0){
			return null;
		}
		Map<String, Object> sesion = null;
		if(ActionContext.getContext() != null){
			sesion = ActionContext.getContext().getSession();
		}
		if(null==sesion){
			return null;
		}
		return sesion.get(clave);
	}

}