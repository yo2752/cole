package com.matriculasWS.utiles;

import com.matriculasWS.beans.CodigoResultado;

public abstract class ConstantesCodigosError {

	//Codigos y descripción de los errores
	public static CodigoResultado RESULTADO_OK = new CodigoResultado (ConstantesWS.OK,ConstantesWS.Codigo_000,ConstantesWS.Correcto);
	public static CodigoResultado RESULTADO_ERROR_INTERNO = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_999,ConstantesWS.Error_Interno);
	public static CodigoResultado RESULTADO_FALTAN_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_001,ConstantesWS.Falta_parametros_entrada);
	public static CodigoResultado RESULTADO_ERROR_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_002,ConstantesWS.Error_parametros_entrada);
	public static CodigoResultado RESULTADO_DEMASIADOS_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_003,ConstantesWS.Demasiados_parametros_entrada);
	public static CodigoResultado RESULTADO_ERROR_PARAMETROS_FECHA = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_005,ConstantesWS.Error_parametros_entrada_fecha);
	public static CodigoResultado RESULTADO_NO_HAY_RESULTADOS= new CodigoResultado (ConstantesWS.OK,ConstantesWS.Codigo_004,ConstantesWS.No_Hay_Resultados);
	public static CodigoResultado RESULTADO_USUARIO_PASS_INCORRECTO = new CodigoResultado(ConstantesWS.NOK, ConstantesWS.Codigo_006, ConstantesWS.Usuario_o_password_incorrectos);
}
