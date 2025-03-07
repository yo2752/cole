package org.gestoresmadrid.core.consultaKo.model.enumerados;

public enum TipoDocumentoKoEnum {
	PERMISO_CIRCULACION("PC", "Permiso de circulación") {
		public String toString() {
			return "PC";
		}
	},
	FICHA_TECNICA("FCT", "Ficha técnica") {
		public String toString() {
			return "FCT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoDocumentoKoEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;

	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static TipoDocumentoKoEnum convertir(String valorEnum) {
		for (TipoDocumentoKoEnum element : TipoDocumentoKoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoDocumentoKoEnum element : TipoDocumentoKoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoDocumentoKoEnum element : TipoDocumentoKoEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoTramiteKo tipo : TipoTramiteKo.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(TipoDocumentoKoEnum tipo : TipoDocumentoKoEnum.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
}