package org.gestoresmadrid.logs.enumerados;

public enum TipoLogProcesoEnum {

	LOGING_LOG("loging", "loging"){
		public String toString() {
	        return "loging";
	    }
	},
	TRAFICO_LOG("trafico", "trafico"){
		public String toString() {
	        return "trafico";
	    }
	},
	YERBABUENA("proc_yerbabuena", "yerbabuena"){
		public String toString() {
	        return "proc_yerbabuena";
	    }
	},
	MATRICULACION("proc_matriculacion", "matriculacion"){
		public String toString() {
	        return "proc_matriculacion";
	    }
	},
	TRANSMISION("proc_transmision", "transmision"){
		public String toString() {
	        return "proc_transmision";
	    }
	},
	SOLICITUD_INFORMACION("proc_sol_informacion", "solicitud de informacion"){
		public String toString() {
	        return "proc_sol_informacion";
	    }
	},
	JUSTIFICANTES_PROFESIONALES("proc_justificantes", "justificantes"){
		public String toString() {
	        return "proc_justificantes";
	    }
	},
	BAJAS("proc_bajas", "bajas"){
		public String toString() {
	        return "proc_bajas";
	    }
	},
	DUPLICADOS("proc_duplicados", "duplicados"){
		public String toString() {
	        return "proc_duplicados";
	    }
	},
	CARGA_DATOS("proc_carga_datos", "carga de datos"){
		public String toString() {
	        return "proc_carga_datos";
	    }
	};

	private TipoLogProcesoEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	private String valorEnum;
	private String nombreEnum;
	
	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public static TipoLogProcesoEnum recuperar(String valorEnum){
		if(valorEnum.equals("loging")){
			return TipoLogProcesoEnum.LOGING_LOG;}
		else if(valorEnum.equals("trafico")){
			return TipoLogProcesoEnum.TRAFICO_LOG;}
		else if(valorEnum.equals("yerbabuena")){
			return TipoLogProcesoEnum.YERBABUENA;}
		else if(valorEnum.equals("matriculacion")){
			return TipoLogProcesoEnum.MATRICULACION;}
		else if(valorEnum.equals("transmision")){
			return TipoLogProcesoEnum.TRANSMISION;}
		else if(valorEnum.equals("justificantes")){
			return TipoLogProcesoEnum.JUSTIFICANTES_PROFESIONALES;}
		else if(valorEnum.equals("bajas")){
			return TipoLogProcesoEnum.BAJAS;}
		else if(valorEnum.equals("duplicados")){
			return TipoLogProcesoEnum.DUPLICADOS;}
		else if(valorEnum.equals("carga de datos")){
			return TipoLogProcesoEnum.CARGA_DATOS;
		}else{
			return null;
		}
	}
}
