package trafico.utiles.constantes;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmCodigoResultadoMatriculasWS;

import colas.constantes.ConstantesProcesos;

public class ConstantesIVTM {

	/*
	 * Valor devuelto por el Action de IVTM, cuando se intenta autoliquidar el IVTM para una matriculación, y va correctamente.
	 */
	public static final String RESPUESTA_ALTA_MATRICULACION_CORRECTA = "altasMatriculacion";
	public static final String RESPUESTA_ALTA_MATRICULACION_MATW_CORRECTA = "altasMatriculacionMatw";

	/*
	 * Valor devuelto por el Action de IVTM, cuando se intenta autoliquidar el IVTM para una matriculación masivamente, y va correctamente.
	 */
	public static final String RESPUESTA_AUTOLIQ_MASIVA = "consultaTramiteTrafico";

	// Tipos de peticiones al WS
	public static final String TIPO_ALTA_IVTM_WS = "ALTAPROYECTOIVTM";
	public static final String TIPO_MODIFICACION_IVTM_WS = "MODPROYECTOIVTM";
	public static final String TIPO_CONSULTA_IVTM_WS = "CDEUDAIVTM";

	// Constantes incluidas en el properties
	public static final String KEY_URL_PETICION_IVTM = "trafico.ivtm.url.peticion";
	public static final String KEY_AYUNTAMIENTO_NIF = "trafico.ivtm.ayuntamiento.nif";
	public static final String KEY_AYUNTAMIENTO_NOMBRE = "trafico.ivtm.ayuntamiento.nombre";
	public static final String KEY_FUNCIONARIO_NIF = "trafico.ivtm.funcionario.nif";
	public static final String KEY_FUNCIONARIO_NOMBRE = "trafico.ivtm.funcionario.nombre";
	public static final String KEY_CODE_INICIAL = "trafico.ivtm.code.initial";
	public static final String KEY_SOLICITANTE_CONSENTIMIENTO = "trafico.ivtm.solicitante.consentimiento";
	public static final String KEY_SOLICITANTE_FINALIDAD = "trafico.ivtm.solicitante.finalidad";
	public static final String KEY_SOLICITANTE_IDENTIFICADOR = "trafico.ivtm.solicitante.identificacion";
	public static final String KEY_SOLICITANTE_NOMBRE = "trafico.ivtm.solicitante.nombre";

	// Otras constantes de los XML del WS
	public static final int NUMERO_ELEMENTOS_PETICION = 1;
	public static final String DATE_FORMAT_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final Integer TIEMPO_ESTIMADO_RESPUESTA_XML = 0;
	public static final String BONIFICACION_SI = "S";
	public static final String BONIFICACION_NO = "N";
	public static final String RESULTADO_CONSULTAWS_VACIO = "No hay respuesta";

	public static final String TEXTO_NO_TRAMITE = "No hay trámite";
	public static final String TEXTO_VEHICULO_OBLIGATORIO = "El vehículo es obligatorio";
	public static final String TEXTO_SERVICIO_OBLIGATORIO = "El tipo de servicio es obligatorio";
	public static final String TEXTO_CARBURANTE_OBLIGATORIO = "El tipo de carburante es obligatorio";
	public static final String TEXTO_TIPO_VEHICULO_OBLIGATORIO = "El tipo de vehículo es obligatorio";
	public static final String TEXTO_PESO_OBLIGATORIO = "La MMA y la Tara es obligatoria en este tipo de vehículos. Además, la MMA debe ser mayor que la tara";
	public static final String TEXTO_POTENCIA_OBLIGATORIA = "La potencia fiscal es obligatoria en este tipo de vehículos";
	public static final String TEXTO_PLAZAS_OBLIGATORIA = "El número de plazas es obligatorio en este tipo de vehículos";
	public static final String TEXTO_CILINDRADA_OBLIGATORIA = "La cilindrada es obligatoria para este tipo de vehículo";
	public static final String TEXTO_VEHICULO_NO_AUTOLIQUIDABLE = "El vehículo, por su tipo, no puede autoliquidar el IVTM";
	public static final String TEXTO_MODELO_OBLIGATORIO = "El modelo del vehículo es obligatorio";
	public static final String TEXTO_MARCA_OBLIGATORIA = "La marca del vehículo es obligatoria";
	public static final String TEXTO_BASTIDOR_OBLIGATORIO = "El bastidor es obligatorio";
	public static final String TEXTO_FECHA_OBLIGATORIA = "Es obligatoria la fecha de ";
	public static final String TEXTO_FECHA_POSTERIOR = "No puede ser posterior a la fecha actual la fecha de ";
	public static final String TEXTO_ERROR_FECHA = "Error en fecha:";
	public static final String TEXTO_FALTA_TITULAR = "Falta el titular";
	public static final String TEXTO_FALTA_REPRESENTANTE = "Falta el representante";
	public static final String TEXTO_OBLIGATORIO_TIPO_PERSONA = "Es obligatorio el";
	public static final String TEXTO_OBLIGATORIO_NOMBRE = "Se debe rellenar el nombre del ";
	public static final String TEXTO_OBLIGATORIO_APELLIDO = "Se debe llenar el campo Apellido 1 / Razón social del ";
	public static final String TEXTO_OBLIGATORIO_DOCUMENTO_IDENTIFICACION = "No es válido el documento de identificación del ";
	public static final String TEXTO_OBLIGATORIO_TIPO_VIA = "Es obligatorio el tipo de la vía del";
	public static final String TEXTO_OBLIGATORIA_DIRECCION = "Es obligatoria La dirección del ";
	public static final String TEXTO_OBLIGATORIO_NUMERO_VIA = "Es obligatorio el número de la vía del ";
	public static final String TEXTO_OBLIGATORIO_NOMBRE_VIA = "Es obligatorio el nombre de la vía del ";
	public static final String TEXTO_OBLIGATORIO_MUNICIPIO = "Es obligatorio el municipio del";
	public static final String TEXTO_OBLIGATORIO_CODIGO_POSTAL = "Es obligatorio, y debe tener 5 dígitos el código postal del";
	public static final String TEXTO_MUNICIPIO_MADRID_OBLIGATORIO = "El municipio debe ser Madrid ";
	public static final String TEXTO_IBAN_OBLIGATORIO = "El CCC (formato IBAN) no es correcto";
	public static final String TEXTO_BONIFICACION_MEDIO_AMBIENTE_OBLIGATORIO ="La bonificación medio ambiente es obligatoria";
	public static final String TEXTO_AUTOLIQUIDACION_OBLIGATORIA="El numero de autoliquidación es un dato obligatorio";
	public static final String TEXTO_IVTM_NO_PERMISO_CONSULTA="No tiene permiso para usar la consulta de Ivtm";
	public static final String TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION = "No tiene permiso para usar el alta de autoliquidación IVTM";
	public static final String TEXTO_IVTM_NO_PERMISO_USO_AUTOLIQUIDACION ="No puede utilizar la autoliquidación IVTM";
	public static final String TEXTO_IVTM_TRAMITE_GUARDADO = "Trámite guardado.";
	public static final String TEXTO_IVTM_TRAMITE_NO_GUARDADO = "Trámite no guardado.";
	public static final String TEXTO_IVTM_ERROR_NO_LUQIDACION="Existen errores. No se puede Autoliquidar el IVTM.";
	public static final String TEXTO_IVTM_ERROR_NO_PAGO="Existen errores. No se puede realizar el pago del IVTM.";
	public static final String TEXTO_IVTM_IVTM_NO_GUARDADO = "IVTM no guardado";
	public static final String TEXTO_IVTM_ERROR_GUARDAR_IVTM = "Error al guardar IVTM";
	public static final String TEXTO_IVTM_VALIDACION_CORRECTA = "Validacion Autoliquidación IVTM correcta.";
	public static final String TEXTO_IVTM_ENCOLADO_ALTA_CORRECTO= "Solicitud de Autoliquidación IVTM en Cola.";
	public static final String TEXTO_IVTM_ENCOLADO_ALTA_ERROR= "Ha ocurrido un error interno tramitando la solicitud. No se ha encolado.";
	public static final String TEXTO_IVTM_ERROR_GUARDADO_NO_ALTA_IVTM = "Al no haberse guardado todos los datos del trámite, no es posible realizar la autoliquidación del IVTM.";
	public static final String TEXTO_IVTM_ERROR_VALIDACION_CONSULTA="Errores en los datos de la consulta";
	public static final String TEXTO_IVTM_SOLICITUD_CONSULTA="La solicitud de consulta se ha encolado. Espere la respuesta en las notificaciones";
	public static final String TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR="No se ha podido ejecutar la consulta. Por favor, inténtelo más tarde";
	public static final String TEXTO_IVTM_DATOS_TRAMITE_ERROR="Faltan datos en el trámite.";
	public static final String TEXTO_IVTM_ERROR_RECUPERANDO = "Error recuperando IVTM";
	public static final String TEXTO_FECHA_OBLIGATORIA_CONS_BUSQ = "Es obligatoria la fecha de búsqueda";
	public static final String TEXTO_FECHA_OBLIGATORIA_CONS_BUSQ_INI = "Es obligatoria la Fecha Inicio para la búsqueda de peticiones previas ";
	public static final String TEXTO_FECHA_OBLIGATORIA_CONS_BUSQ_FIN = "Es obligatoria la Fecha Hasta para la búsqueda de peticiones previas ";
	public static final String TEXTO_FECHA_OBLIGATORIA_CONS_POST_INI = "No puede ser posterior a la fecha actual la Fecha  Inicio";
	public static final String TEXTO_FECHA_OBLIGATORIA_CONS_POST_FIN = "No puede ser posterior a la fecha actual la Fecha  Hasta";
	public static final String TEXTO_IVTM_OBLIGATORIO_IMPORTE = "Es obligatorio el importe";
	public static final String TEXTO_IVTM_DIGITO_CONTROL = "Es obligatorio el digito de control";
	public static final String TEXTO_IMPORTE_OBLIGATORIO = "El importe del IVTM es obligatorio";
	public static final String TEXTO_GESTOR_OBLIGATORIO = "El importe del IVTM es obligatorio";
	public static final String TEXTO_EMISOR_OBLIGATORIO = "El emisor del IVTM es obligatorio";
	public static final String TEXTO_FECHA_PAGO_OBLIGATORIO = "La fecha de pago del IVTM es obligatorio";

	public static final String RESULTADO_CORRECTO_ACTION_CONSULTA_IVTM = "consultaIVTM";
	public static final String RESULTADO_ERROR_ACTION_IVTM = "error";

	public static final String TIPOTRAMITECONSULTAIVTM = "T14";

	public static final String FECHA_IVTM = "pago";

	public static final Object MUNICIPIO_MADRID = "079";
	public static final String MUNICIPIO_MADRID_IMPO = "MADRID";
	public static final Object PROVINCIA_MADRID = "28";
	public static final String FORMATO_FECHA_GUARDAR_IVTM = "dd/MM/yyyy HH:mm:ss";

	public static final String CONSULTA_IVTM_LLAMA_PROCESO = "trafico.ivtm.consultaIVTMLlamaProceso";

	public static final String TIPO_ID_PETICION_IVTM = "1";
	public static final String TEXTO_LOG_PROCESO = "Proceso "+ConstantesProcesos.PROCESO_IVTM;

	/* *********************************
	 Constantes de matriculasWS
	 ***********************************/
	public static final String OK="OK";
	public static final String NOK="NOK";
	public static final String Correcto = "Correcto";
	public static final String Error_Interno = "Error Interno";
	public static final String Falta_parametros_entrada = "Faltan Parámetros de Entrada";
	public static final String Error_parametros_entrada = "Error en los Parámetros de Entrada: La búsqueda se debe realizar por fechas o por números de autoliquidación";
	public static final String Demasiados_parametros_entrada = "Demasiados Parámetros de Entrada: Se debe buscar por fechas o por números de autoliquidación";
	public static final String No_Existe_Matricula_asociada = "No existe Matrícula o Bastidor asociado a este número de Autoliquidación";
	public static final String No_Hay_Resultados = "No hay resultados asociados a ese criterio de búsqueda.";
	public static final String Error_parametros_entrada_fecha = "Error en los Parámetros de Entrada: Error en fechas";
	public static final String Usuario_o_password_incorrectos= "Usuario o password incorrectos";
	public static final String La_firma_no_es_valida = "La firma no es válida";
	public static final String No_existe_la_firma = "No existe la firma";

	// Códigos Error
	public static final String Codigo_000 = "000"; // Correcto
	public static final String Codigo_001 = "001"; // Faltan parámetros de entrada
	public static final String Codigo_002 = "002"; // Error en los parámetros de entrada
	public static final String Codigo_003 = "003"; // Error en los parámetros de entrada
	public static final String Codigo_004 = "004"; // Error en los parámetros de entrada
	public static final String Codigo_999 = "999"; // Error interno del servidor
	public static final String Codigo_005 = "005";
	public static final String Codigo_006 = "006"; // Usuario o password incorrectos
	public static final String Codigo_007 = "007"; // La firma no es válida
	public static final String Codigo_008 = "008"; // La firma no existe

	public static IvtmCodigoResultadoMatriculasWS RESULTADO_OK = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.OK,ConstantesIVTM.Codigo_000,ConstantesIVTM.Correcto);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_INTERNO = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.NOK,ConstantesIVTM.Codigo_999,ConstantesIVTM.Error_Interno);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_FALTAN_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.NOK,ConstantesIVTM.Codigo_001,ConstantesIVTM.Falta_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.NOK,ConstantesIVTM.Codigo_002,ConstantesIVTM.Error_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_DEMASIADOS_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.NOK,ConstantesIVTM.Codigo_003,ConstantesIVTM.Demasiados_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_PARAMETROS_FECHA = new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.NOK,ConstantesIVTM.Codigo_005,ConstantesIVTM.Error_parametros_entrada_fecha);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_NO_HAY_RESULTADOS= new IvtmCodigoResultadoMatriculasWS (ConstantesIVTM.OK,ConstantesIVTM.Codigo_004,ConstantesIVTM.No_Hay_Resultados);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_USUARIO_PASS_INCORRECTO = new IvtmCodigoResultadoMatriculasWS(ConstantesIVTM.NOK, ConstantesIVTM.Codigo_006, ConstantesIVTM.Usuario_o_password_incorrectos);

	public static final String MODIFICACION_IVTM_BEAN = "modificacionIvtmBean";
	public static final String CONSULTA_IVTM_BEAN = "consultaIvtmBean";
}