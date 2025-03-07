package oegam.constantes;

import java.math.BigDecimal;

public class ConstantesPQ {	
	public static final String P_INFORMACION = "P_INFORMACION"; 
	public static final String P_SQLERRM     = "P_SQLERRM"; 
	public static final String P_CODE        = "P_CODE";
	public static final String P_CUENTA      = "CUENTA";
	
	public static final String LOG_P_INFORMACION = "P_INFORMACION: "; 
	public static final String LOG_P_SQLERRM     = "P_SQLERRM: "; 
	public static final String LOG_P_CODE        = "P_CODE: ";
	public static final String LOG_P_CUENTA      = "P_CUENTA: ";
	public static final String LOG_P_ERROR       = "P_ERROR: ";
	
	public static final BigDecimal pCodeOk = new BigDecimal(0);
	public static final BigDecimal pCodeOkAlt = new BigDecimal(1);
	public static final BigDecimal pCodeFaltaNombre = new BigDecimal(171);
	 
	public static final  String MAPAPARAMETROS = "mapaParametros"; 
	public static final  String RESULTBEAN     = "ResultBean";
	public static final  String BEANPANTALLA   = "beanPantalla"; 
	
	public static final  String MENSAJE     = "mensaje"; 
	public static final  String LISTACURSOR = "listaCursor";
	
	public static final String RESPUESTAGENERICA = "RespuestaGenerica";
	public static final String RESULTADOVEHICULO = "ResultadoVehiculo";
	public static final String RESULTADOTRAMITE  = "ResultadoTramite";
	public static final String RESULTADOTITULAR  = "ResultadoTitular";
	public static final String RESULTADOREPRESENTANTE = "ResultadoRepresentante";
	public static final String RESULTADOADQUIRIENTE   = "ResultadoAdquiriente";
	
	public static final String BEANPQRESULTADO = "beanPQResultado";
	public static final String NUM_EXPEDIENTE  = "numExpediente";	
}
