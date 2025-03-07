/**
 * 
 */
package org.gestoresmadrid.core.trafico.eeff.model.enumerados;


/**
 * Constantes utilizadas para las entidades financieras (EEFF)
 *
 */
public class ConstantesEEFF {

	/**
	 * Guardado de Ficheros.
	 */
	public static final String EEFF = "EEFF";
	
	public static final String EEFF_SUBTIPO_LIBERACION = "LIBERACION";
	
	public static final String EEFF_SUBTIPO_CONSULTA = "CONSULTA";
	/**
	 * Definici�n del permiso para liberar EEFF. Se corresponde con codigo_funcion en la base de datos (tabla funcion).
	 */
	public static final String CODIGO_PERMISO_BBDD_LIBERAR_EEFF = "OT131";
	
	/**
	 * Definici�n del permiso para consultar EEFF. Se corresponde con codigo_funcion en la base de datos (tabla funcion).
	 */
	public static final String CODIGO_PERMISO_BBDD_CONSULTA_EEFF = "OT132";
	
	/**
	 * Resultado a devolver por el Action de Liberar EEFF para una liberaci�n, cuando ha funcionado correctamente. Se corresponde con el resultado que devuelve el action de AltasMatriculacionMatw.
	 */
	public static final String RESULTADO_CORRECTO_ACTION_LIBERAR_EEFF_MATW = "altasMatriculacionMatw";
	/*
	 * Resultado a devolver por el Action de Liberar EEFF para una liberaci�n masiva de expediente, cuando ha funcionado correctamente. Se corresponde con el resultado que devulve el action de ConsultaTramiteMatriculacion.
	 */
	public static final String RESULTADO_CORRECTO_ACTION_LIBERAR_EEFF_MASIVO = "consultaTramiteTrafico";
	
	/**
	 * Resultado a devolver por el Action de Liberar EEFF cuando hay error. Se corresponde con el resultado que devuelve el action de AltasMatriculacion.
	 */
	public static final String RESULTADO_ERROR_ACTION_LIBERAR_EEFF = "error";
	
	/**
	 * Tipo de petici�n del proceso. Se utiliza como diferenciador (xmlenviar) del tipo de proceso.  
	 */
	public static final String XML_ENVIAR_LIBERAR_EEFF = "LiberarEEFF";
	
	/**
	 * Tipo de petici�n del proceso. Se utiliza como diferenciador (xmlenviar) del tipo de proceso.  
	 */
	public static final String XML_ENVIAR_CONSULTAR_EEFF = "ConsultarEEFF";
	
	/**
	 * Para indicar que el XML que se est� guardando es la petici�n al WS
	 */
	public static final int TIPO_GUARDAR_XML_PETICION_LIBERAR = 1;
	
	/**
	 * Para indicar que el XML que se est� guardando es la petici�n al WS
	 */
	public static final int TIPO_GUARDAR_XML_PETICION_CONSULTAR = 1;
	
	/**
	 * Para indicar que el XML que se est� guardando es la petici�n al WS
	 */
	public static final int TIPO_GUARDAR_XML_RESPUESTA_CONSULTAR = 2;
	
	/**
	 * Para indicar que el XML que se est� guardando es la respuesta al WS
	 */
	public static final int TIPO_GUARDAR_XML_RESPUESTA_LIBERAR = 2;
	
	/**
	 * Indica la longitud que debe tener el NIVE
	 */
	public static final int EEFF_LONGITUD_NIVE = 32;
	
	/**
	 * Indica la longitud que debe tener el Bastidor
	 */
	public static final int EEFF_LONGITUD_BASTIDOR = 21;
	
	/* CONSTANTES DE TEXTOS */
	
	public static final String EEFF_TEXTO_NO_PERMISO = "No tiene permiso para usar la liberaci�n de EEFF";
	
	public static final String EEFF_TEXTO_ERROR_VALIDACION_LIBERACION = "Errores en los datos de la liberaci�n:";
	
	public static final String EEFF_TEXTO_ERROR_PREVIA_LIBERACION = "El tr�mite ya ha sido liberado previamente";
	
	public static final String EEFF_TEXTO_ERROR_DATOS_LIBERACION = "El tr�mite no tiene asociado datos de liberaci�n";
	
	public static final String EEFF_TEXTO_ERROR_NUM_EXPEDIENTE_LIBERACION = "Ha habido un error al obtener el n�mero de expdiente del tr�mite";
	
	public static final String EEFF_TEXTO_MENSAJE_ENCOLAR_LIBERAR = "Solicitud de liberaci�n realizada. Esperando respuesta";

	public static final String EEFF_TEXTO_ERROR_ESTADO_PARA_LIBERACION = "Error: Para liberar un veh�culo, el tr�mite debe estar en estado Validado Telem�ticamente";
	
	public static final String EEFF_TEXTO_ERROR_DATOSMATR_PARA_LIBERACION = "Ha habido un error recuperando los datos de liberaci�n";
	
	public static final String EEFF_TEXTO_SOLICITUD_LIBERACION_CORRECTA = "La solicitud de liberaci�n se ha encolado. Espere la respuesta en las notificaciones";
	
	public static final String EEFF_TEXTO_SOLICITUD_LIBERACION_ERROR = "No se ha podido realizar la solicitud de liberaci�n";
	
	public static final String EEFF_TEXTO_ESTADO_LIBERACION_ERROR = "No se ha podido realizar el cambio de estado del tr�mite";
	
	public static final String EEFF_TEXTO_NO_LIBERADO_MATRICULAR = "El veh�culo no se ha liberado, y no se puede matricular";
	
	public static final String EEFF_TEXTO_ERROR_TIPO_TRA_LIBERACION = "El tr�mite no es tipo Matriculaci�n";
	
	public static final String EEFF_TEXTO_ERROR_GENERICO_LIBERACION = "Ha habido un error en la liberaci�n";
	
	public static final String EEFF_COBRAR_CREDITOS_LIBERAR = "eeff.creditos.cobrarCreditos.liberar"; // Creditos
	
	public static final String EEFF_COBRAR_CREDITOS_CONSULTA = "eeff.creditos.cobrarCreditos.consulta"; // Creditos
	
   /* CONSTANTES DE TEXTOS CONSULTAR */
	
	public static final String EEFF_TEXTO_NO_PERMISO_CONSULTA = "No tiene permiso para usar la consulta de EEFF";
	
	public static final String EEFF_TEXTO_ERROR_CONSULTA = "Ha habido un error de EEFF con la BD";
	
	public static final String EEFF_TEXTO_ERROR_VALIDACION_CONSULTA = "Errores en los datos de la consulta:";
	
	public static final String EEFF_TEXTO_SOLICITUD_CONSULTA_CORRECTA = "La solicitud de consulta se ha encolado. Espere la respuesta en las notificaciones";
	
	public static final String EEFF_TEXTO_SOLICITUD_CONSULTA_ERROR = "No se ha podido realizar la solicitud de Consulta";
	
	public static final String EEFF_TEXTO_ERROR_VALI_LONG_NIVE = "El c�digo NIVE debe tener una longitud de 32 caracteres";
	
	public static final String EEFF_TEXTO_ERROR_VALI_NIVE = "El c�digo NIVE es obligatorio";	
	
	public static final String EEFF_TEXTO_ERROR_VALI_BASTIDOR = "El N� Bastidor es obligatorio";
	
	public static final String EEFF_TEXTO_ERROR_VALI_FIRCIF = "El CIF del FIR es obligatorio";
	
	public static final String EEFF_TEXTO_ERROR_VALI_FIRMARCA = "La marca del FIR es obligatoria";
	
	public static final String EEFF_TEXTO_ERROR_CIF_CONCESIONARIO = "El CIF del concesionario es obligatorio";

   /*Validaci�n al importar tr�mites
    * 
    */
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_NIVE = "Veh�culo no exento de liberar, El NIVE es obligatorio";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_BASTIDOR = "Veh�culo no exento de liberar, El Bastidor es obligatorio";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_TITUDNI = "Veh�culo no exento de liberar, El DNI del titular es necesario";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_DIRVIA = "Veh�culo no exento de liberar, El tipo de via del titular es necesario";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_NOM_VIA = "Veh�culo no exento de liberar, El nombre de la v�a del titular es necesario";
		
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_DIRNUM = "Veh�culo no exento de liberar, El n�mero de direcci�n del titular es necesario";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_DIRPROV = "Veh�culo no exento de liberar, La provincia del titular es necesaria";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_DIRMUN = "Veh�culo no exento de liberar,  El Municipio del titular es necesario";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_DIR_CP = "Veh�culo no exento de liberar, El c�digo postal del titular es necesario";
	
	//cambiarlo al property
	public static final String EEFF_URL="eeff.direccionWS";
	
	public static final String EEFF_TEXTO_ERROR_IMPO_VALI_NUM_FACT = "El n�mero de factura es obligatorio";
	
	public static final int TIPO_ID_EEFF = 2;

	public static final int TAMANIO_NOMBRE = 32;
	
	public static final int TAMANIO_APELLIDO1 = 32;
	
	public static final int TAMANIO_APELLIDO2 = 32;
	
	public static final String LOG_PROCESO_EEFF_GENERATOR = "PROCESO_EEFF_GENERATOR: ";
	public static final String APPENDER_EEFF_GENERATOR = "ProcesoEEFFGenerator";
	
	public static final String ESTADO_BASTIDOR_LIBERADO	= "LIBERADA";
}
