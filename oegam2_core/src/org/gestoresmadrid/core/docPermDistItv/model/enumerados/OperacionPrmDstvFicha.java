package org.gestoresmadrid.core.docPermDistItv.model.enumerados;

public enum OperacionPrmDstvFicha {
	CREACION("CR","CREACION"){
		public String toString() {
			return "CR";
		}
	},
	SOLICITUD("SOL","SOLICITUD"){
		public String toString() {
			return "SOL";
		}
	},
	SOLICITUD_MSV("SOL_MSV","SOLICITUD MASIVA"){
		public String toString() {
			return "SOL_MSV";
		}
	},
	SOL_IMPRESION("SOL_IMP","SOLICITUD IMPRESION"){
		public String toString() {
			return "SOL_IMP";
		}
	},
	SOL_IMPRESION_MSV("SOL_IMP_MSV","SOLICITUD IMPRESION MASIVA"){
		public String toString() {
			return "SOL_IMP_MSV";
		}
	},
	SOL_IMPRESION_DMD("SOL_IMP_DMD","SOLICITUD IMPRESION DEMANDA"){
		public String toString() {
			return "SOL_IMP_DMD";
		}
	},
	SOL_IMPRESION_DMD_MASIVO("SOL_IMP_DMD_MSV","SOLICITUD IMPRESION DEMANDA MASIVA"){
		public String toString() {
			return "SOL_IMP_DMD_MSV";
		}
	},
	IMPRESION("IMP","IMPRESION"){
		public String toString() {
			return "IMP";
		}
	},
	REVERTIR("RVT","REVERTIR"){
		public String toString() {
			return "RVT";
		}
	},
	CAMBIO_ESTADO("CB","CAMBIAR ESTADO"){
		public String toString() {
			return "CBE";
		}
	},
	INICIADO("INI","INICIADO"){
		public String toString() {
			return "CBE";
		}
	},
	GENERADO("GEN","GENERADO"){
		public String toString() {
			return "GEN";
		}
	},
	ERROR_GENERADO("EGEN","ERROR_GENERADO"){
		public String toString() {
			return "EGEN";
		}
	},
	DESCARGADO("DCG","DESCARGADO"){
		public String toString() {
			return "DCG";
		}
	},
	ERROR_IMPRESION("EIMP","ERROR_IMPRESION"){
		public String toString() {
			return "EIMP";
		}
	},IMPRIMIENDO("IMPD","IMPRIMIENDO"){
		public String toString() {
			return "IMPD";
		}
	},MODIFICACION("MOD","MODIFICACION"){
		public String toString() {
			return "MOD";
		}
	},DOC_RECIBIDA("DRB","DOC_RECIBIDA"){
		public String toString() {
			return "DRB";
		}
	},GENERADO_KO("GKO","GENERADO_KO"){
		public String toString() {
			return "GKO";
		}
	},IMPRIMIENDO_GESTORIA("IMPD_GST","IMPRIMIENDO_GESTORIA"){
		public String toString() {
			return "IMPD_GST";
		}
	},IMPRESO_GESTORIA("IMP_GST","IMPRESO_GESTORIA"){
		public String toString() {
			return "IMP_GST";
		}
	},CREACION_GESTORIA("CR_GST","CREACION_GESTORIA"){
		public String toString() {
			return "CR_GST";
		}
	},SOLICITUD_GESTORIA("SOL_GST","SOLICITUD_GESTORIA"){
		public String toString() {
			return "SOL_GST";
		}
	},ANULAR("ANULAR","ANULAR"){
		public String toString() {
			return "ANL";
		}
	},DESASIGNAR("DESASIGNAR","DESASIGNAR"){
		public String toString() {
			return "DSG";
		}	
	},REINICIAR_IMPR("RC_IMPR","REINICIAR IMPR"){
		public String toString() {
			return "RC_IMPR";
		}	
	},MODIFICACION_CARPETA("MCPT","MODIFICACION CARPETA"){
		public String toString() {
			return "MCPT";
		}	
	},FINALIZADO_IMPR("FINZ","FINALIZADO IMPR"){
		public String toString() {
			return "FINZ";
		}	
	},REACTIVAR("RCT","REACTIVAR"){
		public String toString() {
			return "RCT";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;

	private OperacionPrmDstvFicha(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static OperacionPrmDstvFicha convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(OperacionPrmDstvFicha tipo : OperacionPrmDstvFicha.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(OperacionPrmDstvFicha tipo : OperacionPrmDstvFicha.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(OperacionPrmDstvFicha tipo : OperacionPrmDstvFicha.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
}
