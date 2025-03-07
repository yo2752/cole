package com.matriculasIvtmWS.integracion.constantes;

import com.matriculasIvtmWS.integracion.bean.IvtmCodigoResultadoMatriculasWS;

public class Constantes {

	public static final String CONTEXTO_PROPERTIES_FRONTAL = "FRONTAL";
	public static final String OEGAM_PROPERTIES = "oegam.properties";
	public static final String PROPIEDADES = "propiedades";
	
	public static final String USUARIO = "MUNIMADRID";
	public static final String CLAVE = "MUNIMADRID";

	/* **********************************************
	 Constantes de matriculasWS
	 *****************************************/
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
	
	//Codigos Error
	public static final String Codigo_000 = "000";//Corrrecto
	public static final String Codigo_001 = "001";//faltan parámetros de entrada
	public static final String Codigo_002 = "002";//error en los parámetros de entrada
	public static final String Codigo_003 = "003";//error en los parámetros de entrada
	public static final String Codigo_004 = "004";//error en los parámetros de entrada
	public static final String Codigo_999 = "999";//Error interno del servidor
	public static final String Codigo_005 = "005";
	public static final String Codigo_006 = "006";//Usuario o password incorrectos
	public static final String Codigo_007 = "007";//La firma no es válida
	public static final String Codigo_008 = "008";//La firma no existe
	
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_OK = new IvtmCodigoResultadoMatriculasWS (Constantes.OK,Constantes.Codigo_000,Constantes.Correcto);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_INTERNO = new IvtmCodigoResultadoMatriculasWS (Constantes.NOK,Constantes.Codigo_999,Constantes.Error_Interno);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_FALTAN_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (Constantes.NOK,Constantes.Codigo_001,Constantes.Falta_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (Constantes.NOK,Constantes.Codigo_002,Constantes.Error_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_DEMASIADOS_PARAMETROS = new IvtmCodigoResultadoMatriculasWS (Constantes.NOK,Constantes.Codigo_003,Constantes.Demasiados_parametros_entrada);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_ERROR_PARAMETROS_FECHA = new IvtmCodigoResultadoMatriculasWS (Constantes.NOK,Constantes.Codigo_005,Constantes.Error_parametros_entrada_fecha);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_NO_HAY_RESULTADOS= new IvtmCodigoResultadoMatriculasWS (Constantes.OK,Constantes.Codigo_004,Constantes.No_Hay_Resultados);
	public static IvtmCodigoResultadoMatriculasWS RESULTADO_USUARIO_PASS_INCORRECTO = new IvtmCodigoResultadoMatriculasWS(Constantes.NOK, Constantes.Codigo_006, Constantes.Usuario_o_password_incorrectos);

}
