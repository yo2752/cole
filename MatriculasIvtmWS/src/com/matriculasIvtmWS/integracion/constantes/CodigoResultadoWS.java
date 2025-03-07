package com.matriculasIvtmWS.integracion.constantes;


public enum CodigoResultadoWS {

	OK("000", "Correcto"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_INTERNO("999","Error Interno del Servidor"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_FALTANPARAMETROS("001","Faltan parámetros de entrada"){
			public String toString(){
				return "Correcto";
			}
	},
	ERROR_ERRORPARAMETROS("002","Error en los parámetros de entrada"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_DEMASIADOSPARAMETROS("003","Demasiados parámetros de entrada"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_ERROR_PARAMETROS_FECHA("004","Error en las fechas"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_NO_HAY_RESULTADOS("005","No hay valores para esos datos de entrada"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_AUTENTICACION("006","Error de Autenticación [usuario/clave]"){
		public String toString(){
			return "Correcto";
		}
	},
	ERROR_FIRMA("007","Error de Firma"){
		public String toString(){
			return "Correcto";
		}
	};
	
	/*
	 * 	public static CodigoResultado RESULTADO_OK = new CodigoResultado (ConstantesWS.OK,ConstantesWS.Codigo_000,ConstantesWS.Correcto);
		public static CodigoResultado RESULTADO_ERROR_INTERNO = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_999,ConstantesWS.Error_Interno);
		public static CodigoResultado RESULTADO_FALTAN_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_001,ConstantesWS.Falta_parametros_entrada);
		public static CodigoResultado RESULTADO_ERROR_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_002,ConstantesWS.Error_parametros_entrada);
		public static CodigoResultado RESULTADO_DEMASIADOS_PARAMETROS = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_003,ConstantesWS.Demasiados_parametros_entrada);
		public static CodigoResultado RESULTADO_ERROR_PARAMETROS_FECHA = new CodigoResultado (ConstantesWS.NOK,ConstantesWS.Codigo_005,ConstantesWS.Error_parametros_entrada_fecha);
		public static CodigoResultado RESULTADO_NO_HAY_RESULTADOS= new CodigoResultado (ConstantesWS.OK,ConstantesWS.Codigo_004,ConstantesWS.No_Hay_Resultados);
		public static CodigoResultado RESULTADO_USUARIO_PASS_INCORRECTO = new CodigoResultado(ConstantesWS.NOK, ConstantesWS.Codigo_006, ConstantesWS.Usuario_o_password_incorrectos);
	 */
	
	private String valorEnum;
	private String nombreEnum;
	
	private CodigoResultadoWS(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	
	
}
