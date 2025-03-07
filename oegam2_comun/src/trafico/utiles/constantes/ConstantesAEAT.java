package trafico.utiles.constantes;

public class ConstantesAEAT {

	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONNECTION = "Connection";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String PRAGMA = "Pragma";
	public static final String CONTENT_LENGTH = "Content-length";

	// Nombres de las variables que se envía a la AEAT
	//public static final String HID 	= "HID";
	//public static final String F01	= "F01";
	//public static final String NRC	= "NRC";
	public static final String ENT = "ENT";
	//public static final String LOG	= "LOG";
	//public static final String TXT	= "TXT";
	//public static final String FIR	= "FIR";
	//public static final String FIN	= "FIN";
	public static final String NIF		= "NIF";
	public static final String APELLIDO1= "APELLIDO1";
	public static final String CODIGO	= "CODIGO";
	// Nuevas variables a enviar
	public static final String FIRNIF		= "FIRNIF";
	public static final String FIRNOMBRE	= "FIRNOMBRE";

	public static final String NIF_ANAGRAMA	= "nif";
	public static final String APE_ANAGRAMA	= "ape";
	public static final String ETIQUETAS_ANAGRAMA = "etiquetas";
	public static final String LOG_ANAGRAMA	= "log";
	public static final String ENV_ANAGRAMA	= "ENV";

	// Valores posibles de las variables que se envía a la AEAT
	public static final String VALOR_HID_576	= "IE85760A";
	public static final String VALOR_HID_05		= "IE60050A";
	public static final String VALOR_HID_06		= "IE80060A";
	public static final String VALOR_NRC_VACIO	= "";
	public static final String VALOR_ENT		= "";
	public static final String VALOR_LOG		= "";
	public static final String VALOR_TXT_VACIO	= "";
	public static final String VALOR_FIR_VACIO	= "";
	public static final String VALOR_FIN		= "F";

	public static final String VALOR_HID_ANAGRAMA		= "ETICERTI";
	public static final String VALOR_ETIQUETAS_ANAGRAMA = "2";
	public static final String VALOR_ENV_ANAGRAMA		= "Enviar";
	public static final String VALOR_LOG_ANAGRAMA		= "NO";

	// Nombre de los campos del texto que se va en la variable TXT que se envía a la AEAT
	public static final String TXT_CODIGO_TIPO			= "Codigo y Tipo";
	public static final String TXT_NRC					= "Numero de referencia completo";
	public static final String TXT_IMPORTE_INGRESADO	= "Importe ingresado";
	public static final String TXT_NIF					= "Nif del contribuyente";
	public static final String TXT_VEHICULOS			= "a. Vehiculos (Bastidor)";
	public static final String TXT_EMBARCACIONES		= "Embarcaciones (Nº de Construccion)";
	public static final String TXT_AERONAVES			= "Aeronaves (Nº de Serie)";
	public static final String TXT_BASE_IMPONIBLE		= "[1] Base imponible";
	public static final String TXT_BASE_IMPONIBLE_RED 	= "[2] Base imponible reducida";
	public static final String TXT_TIPO					= "[3] Tipo";
	public static final String TXT_CUOTA				= "[4] Cuota";
	public static final String TXT_DEDUCION_LINEAL		= "[5] Deduccion lineal";
	public static final String TXT_CUOTA_A_INGRESAR		= "[6] Cuota a ingresar";
	public static final String TXT_A_DEDUCIR			= "[7] A deducir";
	public static final String TXT_RESULT_LIQUIDACION	= "[8] Resultado de la liquidacion";

	//Valores posibles de los campos del texto que se va en la variable TXT que se envía a la AEAT 
	public static final String TXT_VALOR_CODIGO_TIPO_INGRESAR	= "576 A Ingresar";
	public static final String TXT_VALOR_CODIGO_TIPO_NEGATIVA	= "576 Negativa";
	public static final String TXT_VALOR_NRC_PREFIJO			= "576";

	//Nombres de las variables javascript que se devuelve a la AEAT
	public static final String VAR_CEM	= "CEH";
	public static final String VAR_CEP	= "CEL";
	public static final String VAR_REF	= "expediente";

	//Etiquetas donde se muestra el mensaje de error en la respuesta de la AEAT
	public static final String TAG_LISTA_ERRORES_INICIO		= "Err[";
	public static final String TAG_LISTA_ERRORES_INICIO_MSJ	= "]='";
	public static final String TAG_LISTA_ERRORES_FIN_MSJ	= "';";

	// VERSIÓN 2
	public static final String CABECERA_ERROR = "Error generando formulario modelo 576 : ";
	// Constantes para las variables del formulario del 576
	public static final String TAG_INICIO_DATOS = "<T57601>";
	public static final String TAG_FIN_DATOS = "</T57601>";
	public static final String TAG_CABECERA_DATOS_INICIO = "<T5760";
	public static final String TAG_CABECERA_DATOS_FIN = "0A0000>";
	public static final String TAG_PIE_DATOS_INICIO = "</T5760";
	public static final String TAG_PIE_DATOS_FIN = "0A0000>";
	public static final String TAG_INICIO_AUX = "<AUX>";
	public static final String TAG_FIN_AUX = "</AUX>";
	public static final String TAG_INICIO_VECTOR = "<VECTOR>";
	public static final String TAG_FIN_VECTOR = "</VECTOR>";
	public static final int TAM_BLANCOS_AUX = 300;
	public static final int TAM_BLANCOS_VECT = 294;
	public static final int TAM_F01_COMPLETA = 2180;
	public static final String ID_MODELO = "576";
	public static final String HID = "HID";
	public static final String TIA = "TIA";
	public static final String NDC = "NDC";
	public static final String NRC = "NRC";
	public static final String ING = "ING";
	public static final String NRR = "NRR";
	public static final String ICO = "ICO";
	public static final String NR1 = "NR1";
	public static final String IN1 = "IN1";
	public static final String NR2 = "NR2";
	public static final String IN2 = "IN2";
	public static final String NR3 = "NR3";
	public static final String IN3 = "IN3";
	public static final String NR4 = "NR4";
	public static final String IN4 = "IN4";
	public static final String NR5 = "NR5";
	public static final String IN5 = "IN5";
	public static final String NR6 = "NR6";
	public static final String IN6 = "IN6";
	public static final String NR7 = "NR7";
	public static final String IN7 = "IN7";
	public static final String CMN = "CMN";
	public static final String LOT = "LOT";
	public static final String IDI = "IDI";
	public static final String LEV = "LEV";
	public static final String F01 = "F01";
	public static final String PUN = "PUN";
	public static final String LOG = "LOG";
	public static final String TXT = "TXT";
	public static final String FIR = "FIR";
	public static final String FIN = "FIN";
	// Constantes para la construcción de la variable F01 formulario del modelo 576:
	public static final String INGRESO = "I";
	public static final String NEGATIVA = "N";
	public static final String PERIODO = "0A";
	public static final String TIPO_TRANSPORTE = "V";
	public static final String NUEVO = "1";
	public static final String USADO = "2";
	public static final String GASOLINA = "1";
	public static final String DIESEL = "2";
	public static final String OTROS = "3";
	// Constante para el valor de la variable HID:
	public static final String HID_VALOR = "IE25760A";
	// Constante para el valor de la variable LOT:
	public static final String LOT_VALOR = "0";
	// Constante para el valor de la variable IDI:
	public static final String IDI_VALOR = "ES";
	// Constante para el valor de la variable LEV:
	public static final String LEV_VALOR = "000000000000";
	// Constante para el valor de la variable PUN:
	public static final String PUN_VALOR = "00000000";
	// Constante para el valor de la variable FIN:
	public static final String FIN_VALOR = "F";
	// Constante para las variables reservadas aeat (vacías)
	public static final String RESERVADO_VACIO = "";
	// Constante para la codificacion, tras el encode url del valor de las variables del formulario:
	public static final String CODIFICACION_VARIABLES = "ISO-8859-15";
	// Constante para el total de variables que irán en el formulario según documentación:
	public static final int TOTAL_VARIABLES_FORMULARIO = 31;
	// Constantes para establecer el host al que se enviará el formulario:
	// PRUEBAS SIN CERTIFICADO:
	public static final String HOST_PRUEBAS_SIN_CERTIFICADO = "https://www2.agenciatributaria.gob.es/es13/l/ewewlink";
	// PRUEBAS CON CERTIFICADO:
	public static final String HOST_PRUEBAS_CON_CERTIFICADO = "https://www1.agenciatributaria.gob.es/es13/l/ewewlink";
	// PRODUCCIÓN SIN CERTIFICADO:
	public static final String HOST_PRODUCCION_SIN_CERTIFICADO = "https://www2.agenciatributaria.gob.es/es13/l/ewuaewlinkua";
	// PRODUCCIÓN CON CERTIFICADO:
	public static final String HOST_PRODUCCION_CON_CERTIFICADO = "https://www1.agenciatributaria.gob.es/es13/l/ewuaewlinkua";
	public static final String FICHERO_DECLARACION = "ficheroDeclaracion";
	// Cabeceras requeridas:
	public static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
	public static final String HEADER_CONNECTION_KEY = "Connection";
	public static final String HEADER_CACHE_CONTROL_KEY = "Cache-Control";
	public static final String HEADER_PRAGMA_KEY = "Pragma";
	public static final String HEADER_CONTENT_LENGTH_KEY = "Content-length";
	public static final String HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";
	public static final String HEADER_CONNECTION_VALUE = "keep-alive";
	public static final String HEADER_CACHE_CONTROL_VALUE = "no-cache";
	public static final String HEADER_PRAGMA_VALUE = "no-cache";
	// FIN VERSIÓN 2
	public static final String HOST_SERVLET_PRUEBAS = "http://localhost/oegam2/servletTest";
	public static final String NOMBRE_FICHERO_RESPUESTA = "Respuesta576";
	public static final String DECLARACION_VARIABLE_EJECUCION_CORRECTA = "CodigoSeguroVerificacion";
	public static final String SUBSTRING_VAR_ERROR_INICIAL = "Err[";
	public static final String SUBSTRING_VAR_ERROR_FINAL = "]=\"";
	public static final String DELIMITADOR_ERRORES = "-ERROR_576-";
	public static final String VARIABLE_CEM_EN_HTML = "var codigoTrafico =";

	public static final String MODELO = "MODELO";
	public static final String EJERCICIO = "EJERCICIO";
	public static final String PERIODO_ = "PERIODO";
	public static final int TAM_F01_COMPLETA_NUEVA = 1863;
	public static final String FIR_VALOR = "FirmaBasica";
	public static final String FIRNIF_VALOR = "Q2861007I";
	public static final String FIRNOMBRE_VALOR = "ILUSTRE COLEGIO OFICIAL DE GESTORES ADMINISTRATIVOS DE MADRID";
	public static final String CAMPO_LOG_EN_JSON = "Log";
	public static final int INICIO_CADENA_CEM = 39;
	public static final int FINAL_CADENA_CEM = 47;
}