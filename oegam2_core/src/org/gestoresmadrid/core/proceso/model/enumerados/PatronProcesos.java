package org.gestoresmadrid.core.proceso.model.enumerados;


public enum PatronProcesos {

	TODOS("todos","TODOS"){
		public String toString(){
			return "TODOS";
		}
	},TRAFICO("trafico","TRÁFICO"){
		public String toString(){
			return "TRÁFICO";
		}
	},MATRICULACION("matriculacion","MATRICULACION"){
		public String toString(){
			return "MATRICULACION";
		}
	},TRANSMISION("transmision","TRANSMISIÓN"){
		public String toString(){
			return "transmision";
		}
	},JUSTIFICANTES("justificantes","JUSTIFICANTES"){
		public String toString(){
			return "JUSTIFICANTES";
		}
	},ENVIO_CORREOS("correo","ENVÍO DE CORREOS"){
		public String toString(){
			return "ENVÍO DE CORREOS";
		}
	},INTEVE("inteve","INTEVE"){
		public String toString(){
			return "INTEVE";
		}
	},GDOC("gdoc","GESTIÓN DOCUMENTAL"){
		public String toString(){
			return "GESTIÓN DOCUMENTAL";
		}
	},DATOS_SENSIBLES("sensibles","DATOS SENSIBLES"){
		public String toString(){
			return "DATOS SENSIBLES";
		}
	},REGISTRO("registro","REGISTRO"){
		public String toString(){
			return "REGISTRO";
		}
	},AEAT_576("aeat","576"){
		public String toString(){
			return "576";
		}
	},COMPRA_TASAS("compratasas","COMPRA TASAS"){
		public String toString(){
			return "COMPRA TASAS";
		}
	},PRESENTACION_JPT("presentacion","PRESENTACION JPT"){
		public String toString(){
			return "PRESENTACION JPT";
		}
	},SANTANDER("santander","SANTANDER"){
		public String toString(){
			return "SANTANDER";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private PatronProcesos(String valorEnum, String nombreEnum) {
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
	
	public static PatronProcesos convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(PatronProcesos patron : PatronProcesos.values()){
				if(patron.getValorEnum().equals(valorEnum)){
					return patron;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(PatronProcesos patron : PatronProcesos.values()){
				if(patron.getValorEnum().equals(valor)){
					return patron.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(PatronProcesos patron : PatronProcesos.values()){
				if(patron.getNombreEnum().equals(nombre)){
					return patron.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(PatronProcesos patronPro){
		if(patronPro != null){
			for(PatronProcesos patron : PatronProcesos.values()){
				if(patron.getValorEnum().equals(patronPro.getValorEnum())){
					return patron.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	
}


