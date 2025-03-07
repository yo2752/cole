package org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados;


public enum DocumentosJustificante {
	PermisoCirculacion("PC","Permiso de circulación."){
		public String toString(){
			return "PC"; 
		}
	}, FichaTecnica("FT", "Ficha técnica.") {
		public String toString() {
	        return "FT";
	    }
	},PermisoCirculacionYFichaTecnica("PCYFT", "Permiso de circulación y ficha técnica.")	{
		public String toString() {
	        return "PCYFT";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private DocumentosJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static DocumentosJustificante convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(DocumentosJustificante doc : DocumentosJustificante.values()){
				if(doc.getValorEnum().equals(valorEnum)){
					return doc;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(DocumentosJustificante doc : DocumentosJustificante.values()){
				if(doc.getValorEnum().equals(valor)){
					return doc.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(DocumentosJustificante doc : DocumentosJustificante.values()){
				if(doc.getNombreEnum().equals(nombre)){
					return doc.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(DocumentosJustificante documento){
		if(documento != null){
			for(DocumentosJustificante doc : DocumentosJustificante.values()){
				if(doc.getValorEnum() == documento.getValorEnum()){
					return doc.getNombreEnum();
				}
			}
		}
		return null;
	}
}
